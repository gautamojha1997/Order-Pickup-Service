package com.example.orderpickup.service.pickup;

import com.example.orderpickup.dtos.PickupDto;
import com.example.orderpickup.enums.Status;
import com.example.orderpickup.mappers.PickupMapper;
import com.example.orderpickup.models.*;
import com.example.orderpickup.repository.PickupRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
class PickupServiceImplTest {

    @MockBean
    private PickupRepository pickupRepository;

    @Autowired
    PickupService pickupService;

    @Autowired
    PickupMapper pickupMapper;

    @Bean
    public PickupService getPickupService(){
        return new PickupServiceImpl();
    }

    Pickup singlePickup = new Pickup();
    Pickup batchPickup = new Pickup();
    PickupDto singlePickupDto = new PickupDto();
    PickupDto batchPickupDto = new PickupDto();

    //Set it to false to check batch pickings
    boolean singlePicking = true;
    @BeforeEach
    public void setUp(){

        //boolean singlePicking = false;

        //create single pickup object
        singlePickup.setPickId("P1");
        singlePickup.setStatus(Status.PICKED);
        singlePickup.setZone("North");

        WareHouse wareHouse = new WareHouse();
        wareHouse.setWareHouseId("w1");
        wareHouse.setLocation("Chicago");
        singlePickup.setWareHouse(wareHouse);

        Store store = new Store();
        store.setStoreId("s1");
        store.setLocation("Illinois");
        singlePickup.setStore(store);

        Employee employee = new Employee();
        employee.setEmployeeId("emp1");
        employee.setEmail("gojha2@uic.edu");
        employee.setFirstName("gopal");
        employee.setLastName("Ojha");
        employee.setContactNumber("69874556");
        singlePickup.setEmployee(employee);

        List<Orders> l1 = new ArrayList<>();
        Orders orders = new Orders();
        orders.setOrderId("o1");
        orders.setItems(null);
        l1.add(orders);
        singlePickup.setOrdersList(l1);

        singlePickup.setToteList(null);
        singlePickup.setToteCarts(null);

        EmployeePerformance employeePerformance = new EmployeePerformance();
        employeePerformance.setEmpPerId("emper1");
        singlePickup.setEmployeePerformance(employeePerformance);

        singlePickupDto = pickupMapper.mapToDto(singlePickup);



        //create batch pickup object
        batchPickup.setPickId("P2");
        batchPickup.setStatus(Status.PICKED);
        batchPickup.setZone("North");

        batchPickup.setWareHouse(wareHouse);


        batchPickup.setStore(store);


        batchPickup.setEmployee(employee);

        List<Orders> l2 = new ArrayList<>();
        Orders orders1 = new Orders();
        orders1.setOrderId("o2");
        orders1.setItems(null);
        l2.add(orders1);

        Orders orders2 = new Orders();
        orders2.setOrderId("o3");
        orders2.setItems(null);
        l2.add(orders2);
        batchPickup.setOrdersList(l2);

        batchPickup.setToteList(null);
        batchPickup.setToteCarts(null);

        EmployeePerformance employeePerformance1 = new EmployeePerformance();
        employeePerformance1.setEmpPerId("emper2");
        batchPickup.setEmployeePerformance(employeePerformance1);

        batchPickupDto = pickupMapper.mapToDto(batchPickup);


        //Moctiko when and then
        //For Single Pickings

        if(singlePicking) {
            Mockito.when(pickupRepository.findAll())
                    .thenReturn(Collections.singletonList(singlePickup));
            Mockito.when(pickupRepository.save(singlePickup))
                    .thenReturn(singlePickup);
            Mockito.when(pickupRepository.findById(singlePickup.getPickId()))
                    .thenReturn(Optional.of(singlePickup));
        }
        //For Batch Pickings
        else {
            Mockito.when(pickupRepository.findAll())
                    .thenReturn(Collections.singletonList(batchPickup));
            Mockito.when(pickupRepository.save(batchPickup))
                    .thenReturn(batchPickup);
            Mockito.when(pickupRepository.findById(batchPickup.getPickId()))
                    .thenReturn(Optional.of(batchPickup));
        }




    }

    @AfterEach
    public void cleanUp(){

    }

    @Test
    void getAllSinglePicking(){
        List<PickupDto> results = pickupService.getAllSinglePicking();
        Assertions.assertEquals(results, Collections.singletonList(singlePickupDto), "Pickup List should match");
    }

    @Test
    void createSinglePicking() {
        PickupDto pickupDto = pickupService.createSinglePicking(singlePickupDto);
        Assertions.assertEquals(pickupDto, singlePickupDto);
    }

    @Test
    void cancelSinglePicking() {
        PickupDto pickupDto = pickupService.cancelSinglePicking(singlePickup.getPickId());

        //Here check whether pickupDto status changes from picked to canceled since singlePickupDto status was picked.
        Assertions.assertNotEquals(pickupDto.getStatus(), singlePickupDto.getStatus());
        //Assertions.assertEquals(singlePickupDto.getStatus(), Status.CANCELED, "Should Match");
        //Assertions.assertEquals(pickupDto.getStatus(), Status.CANCELED);
    }

    //For Batch Pickings set singlePicking flag above to false!!!!!!
    @Test
    void getAllBatchPicking() {
        List<PickupDto> result = pickupService.getAllBatchPicking();
        Assertions.assertEquals(result, Collections.singletonList(batchPickupDto));
    }

    @Test
    void createBatchPicking() {
        PickupDto pickupDto = pickupService.createBatchPicking(batchPickupDto);
        Assertions.assertEquals(pickupDto, batchPickupDto);
    }

    @Test
    void cancelBatchPicking() {
        PickupDto pickupDto = pickupService.cancelBatchPicking(batchPickup.getPickId());
        Assertions.assertNotEquals(pickupDto.getStatus(), batchPickupDto.getStatus());
    }
}