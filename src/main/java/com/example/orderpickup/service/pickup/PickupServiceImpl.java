package com.example.orderpickup.service.pickup;

import com.example.orderpickup.dtos.PickupDto;
import com.example.orderpickup.enums.Status;
import com.example.orderpickup.exceptions.BatchOrderRequiredException;
import com.example.orderpickup.exceptions.LocationNotFoundException;
import com.example.orderpickup.exceptions.PickupNotFoundException;
import com.example.orderpickup.exceptions.SingleOrderRequiredException;
import com.example.orderpickup.mappers.PickupMapper;
import com.example.orderpickup.models.Orders;
import com.example.orderpickup.models.Pickup;
import com.example.orderpickup.models.Tote;
import com.example.orderpickup.models.ToteCart;
import com.example.orderpickup.repository.PickupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;

@Service
@Transactional
@Slf4j
public class PickupServiceImpl implements PickupService {

    @Autowired
    PickupRepository pickupRepository;

    @Autowired
    PickupMapper pickupMapper;

    /**
     * This method gives list of all single picking using :
     * 1. First retrieves all the pickup list from repository
     * 2. Filter All pickups with single order and add it to single pickup list.
     * 3. Map entity to dto.
     * @return the list with all single pickups.
     **/
    @Override
    public List<PickupDto> getAllSinglePicking() {
        log.info("Getting All Single Pickup");
        List<Pickup> pickupAllList = pickupRepository.findAll();
        log.info("Found all pickup List for filtering single pickups : " + pickupAllList);
        List<Pickup> singlePickupList = new ArrayList<>();
        for(Pickup pickup: pickupAllList){
            if(pickup.getOrdersList().size() == 1)
                singlePickupList.add(pickup);
        }
        List<PickupDto> singlePickupDtoList = new ArrayList<>();
        singlePickupList.forEach(pickup -> singlePickupDtoList.add(pickupMapper.mapToDto(pickup)));

        log.info("Returned All single pickups: " + singlePickupDtoList);
        return singlePickupDtoList;
    }

    /**
     * This method creates single picking with specified order to pickup.
     * @param pickupDto
     * @return single picking.
     **/
    @Override
    @Transactional(isolation = REPEATABLE_READ) //handling concurrent request to create single pickings by multiple client
    public PickupDto createSinglePicking(PickupDto pickupDto) {
        log.info("Creating Single Pickups");
        if(pickupDto.getOrdersList().size()>1)
            throw new SingleOrderRequiredException("Single Picking requires only single order!");
        Pickup pickup = pickupMapper.mapToEntity(pickupDto);

        Pickup newPickup = pickupRepository.save(pickup);
        log.info("Single Pickup Saved to Repository");

        PickupDto pickupDtoResponse = pickupMapper.mapToDto(newPickup);
        log.info("Single Pickup Returned : " + pickupDtoResponse);
        return pickupDtoResponse;
    }

    /***
     * This method cancels the single picking for the single order with specified pickup id
     * @param id
     * @return single pickup with status as Canceled.
     */
    @Override
    public PickupDto cancelSinglePicking(String id) {
        log.info("Canceling Single Pickup");
        Optional<Pickup> pickup = pickupRepository.findById(id);
        if(!pickup.isPresent())
            throw new PickupNotFoundException("This Single Picking with id : " + id +" Not found or Doesn't exist");
        pickup.get().setStatus(Status.CANCELED);
        log.info("Canceled Single Pickup  : " + pickup);
        return pickupMapper.mapToDto(pickupRepository.save(pickup.get()));
    }

    /**
     * This method gives list of all batch picking using :
     * 1. First retrieves all the pickup list from repository
     * 2. Filter All pickups with multiple orders and add it to batch pickup list.
     * 3. Map entity to dto.
     * @return the list with all batch pickups.
     **/
    @Override
    public List<PickupDto> getAllBatchPicking() {
        log.info("Getting All Batch Pickup");
        List<Pickup> pickupAllList = pickupRepository.findAll();
        log.info("Found all pickup List for filtering batch pickups : " + pickupAllList);
        if(pickupAllList.isEmpty())
            throw new PickupNotFoundException("Pickings Are Empty");
        List<Pickup> batchPickupList = new ArrayList<>();
        for(Pickup pickup: pickupAllList){
            if(pickup.getOrdersList().size() > 1)
                batchPickupList.add(pickup);
        }

        List<PickupDto> batchPickupDtoList = new ArrayList<>();
        batchPickupList.forEach(pickup -> {
            batchPickupDtoList.add(pickupMapper.mapToDto(pickup));
        });
        log.info("Returned All single pickups: " + batchPickupDtoList);
        return batchPickupDtoList;
    }

    /**
     * This method creates batch picking with multiple orders to pickup.
     * @param pickupDto
     * @return batch picking.
     **/
    @Override
    @Transactional(isolation = REPEATABLE_READ) //handling concurrent request to create batch pickings by multiple client
    public PickupDto createBatchPicking(PickupDto pickupDto) {
        log.info("Creating Batch Pickups");
        if(pickupDto.getOrdersList().size()==1)
            throw new BatchOrderRequiredException("For Batch Picking you need multiple orders!!");
        Pickup pickup = pickupMapper.mapToEntity(pickupDto);

        Pickup newPickup = pickupRepository.save(pickup);
        log.info("Batch Pickup Saved to Repository");

        PickupDto pickupDtoResponse = pickupMapper.mapToDto(newPickup);
        log.info("Batch Pickup Returned : " + pickupDtoResponse);
        return pickupDtoResponse;
    }

    /***
     * This method cancels the batch picking for the multiple orders with specified pickup id
     * @param id
     * @return batch pickup with status as Canceled.
     */
    @Override
    public PickupDto cancelBatchPicking(String id) {
        log.info("Canceling Batch Pickup");
        Optional<Pickup> pickup = pickupRepository.findById(id);
        if(!pickup.isPresent())
            throw new PickupNotFoundException("This Batch Picking with id : " + id +" Not found or Doesn't exist");
        pickup.get().setStatus(Status.CANCELED);
        log.info("Canceled Batch Pickup  : " + pickup);
        return pickupMapper.mapToDto(pickupRepository.save(pickup.get()));
    }


    /***
     * This method provides the ability to switch from single to batch picking by doing following thing:
     * 1. Retrieves all single pickup list.
     * 2. Computes a map which stores list of single pickups based on location as key and if the pickups are not completed or canceled.
     * 3. Finally it returns batch pickups for all single pickups based on location and batch size provided.
     * @param location
     * @param batchSize
     * returns list of batch pickups.
     * */
    @Override
    public List<PickupDto> singleToBatch(String location, int batchSize) {
        //Get All Single PickUps List
        log.info("Convert all single pickups to batch pickups with location = " + location + "and batchSize = " + batchSize);
        List<Pickup> pickupAllList = pickupRepository.findAll();
        log.info("Found all pickup List for filtering single pickups : " + pickupAllList);
        if(pickupAllList.isEmpty())
            throw new PickupNotFoundException("Pickings Are Empty");
        List<Pickup> singlePickupList = new ArrayList<>();
        for(Pickup p: pickupAllList){
            if(p.getOrdersList().size() == 1)
                singlePickupList.add(p);
        }

        log.info("single pickup list = " + singlePickupList);
        //Map to Store Key as WareHouse Location and Value as List<Pickup> for a given location
        Map<String, List<Pickup>> map = new HashMap<>();

        for(Pickup p : singlePickupList){
            if(!p.getWareHouse().getLocation().equals(location))
                throw new LocationNotFoundException("Invalid location");
            List<Pickup> l;
            if(p.getStatus()!=Status.COMPLETE||p.getStatus()!=Status.CANCELED){
                if(!map.containsKey(p.getWareHouse().getLocation())){
                    l = new ArrayList<>();
                    l.add(p);
                    map.put(p.getWareHouse().getLocation(), l);
                } else {
                    l = map.get(p.getWareHouse().getLocation());
                    l.add(p);
                    map.put(p.getWareHouse().getLocation(), l);
                }

            }
        }

        log.info("WareHouse location map = " + map);
        List<List<Orders>> combinedOrdersList;
        List<List<Tote>> combinedToteList;
        List<List<ToteCart>> combinedCartList;

        List<Pickup> batchedPickups = new ArrayList<>();
        int i = 0;
        while (i < map.get(location).size()){
            Pickup pickup;
            combinedOrdersList = new ArrayList<>();
            combinedToteList = new ArrayList<>();
            combinedCartList = new ArrayList<>();
            int batchEnd = i + batchSize;
            for ( ; i < batchEnd && i < map.get(location).size(); i++) {
                // do stuff
                combinedOrdersList.add(map.get(location).get(i).getOrdersList());
                combinedToteList.add(map.get(location).get(i).getToteList());
                combinedCartList.add(map.get(location).get(i).getToteCarts());
                map.get(location).get(i).setStatus(Status.CANCELED);
            }

            List<Orders> l1 = new ArrayList<>();
            combinedOrdersList.forEach(l1::addAll);

            List<Tote> l2 = new ArrayList<>();
            combinedToteList.forEach(l2::addAll);

            List<ToteCart> l3 = new ArrayList<>();
            combinedCartList.forEach(l3::addAll);
            pickup = map.get(location).get(i-1);
            //pickup.setStatus(map.get(location).get(i-1).getStatus());
            //pickup.setEmployee(map.get(location).get(i-1).getEmployee());
            //pickup.setZone(map.get(location).get(i-1).getZone());
            pickup.setStatus(Status.PENDING);
            pickup.setOrdersList(l1);
            pickup.setToteList(l2);
            pickup.setToteCarts(l3);
            log.info("Saved Pickup = " + pickup);
            pickupRepository.save(pickup);
            batchedPickups.add(pickup);
        }
        log.info(String.valueOf(batchedPickups));

        List<PickupDto> batchPickupDtoList = new ArrayList<>();
        batchedPickups.forEach(p -> batchPickupDtoList.add(pickupMapper.mapToDto(p)));
        log.info("Converted Single To batch pickup list : " + batchPickupDtoList);
        return batchPickupDtoList;
    }


    @Override
    public List<PickupDto> batchToSingle(){
        List<Pickup> pickupAllList = pickupRepository.findAll();
        log.info("Convert all batch pickups to single pickups");
        if(pickupAllList.isEmpty())
            throw new PickupNotFoundException("Pickings Are Empty");
        List<Pickup> batchPickupList = new ArrayList<>();
        for(Pickup pickup: pickupAllList){
            if(pickup.getOrdersList().size() > 1)
                batchPickupList.add(pickup);
        }

        List<Pickup> singlePickups = new ArrayList<>();
        log.info("List of All Batch Pickups: " + batchPickupList);

        for(Pickup p : batchPickupList){
            Pickup pickup;
            if(p.getStatus()!=Status.COMPLETE||p.getStatus()!=Status.CANCELED){
                //pickup = p;
                List<Orders> l = p.getOrdersList();
                for(int i = 0; i < l.size(); i++){
                    pickup = new Pickup();
                    pickup.setOrdersList(Collections.singletonList(l.get(i)));
                    pickup.setStatus(Status.PENDING);
                    log.info("Converted single pickup: " + pickup + " for batch pickup :" + p.getPickId());
                    pickupRepository.save(pickup);
                    singlePickups.add(pickup);
                }
                log.info("Batch Pickup :" + p.getPickId() + " canceled");
                p.setStatus(Status.CANCELED);
            }
        }

        List<PickupDto> singlePickupDtoList = new ArrayList<>();
        singlePickups.forEach(pickup -> singlePickupDtoList.add(pickupMapper.mapToDto(pickup)));
        log.info("Converted Batch To Single pickup list : " + singlePickupDtoList);
        return singlePickupDtoList;
    }

}
