package com.example.orderpickup.controller;

import com.example.orderpickup.dtos.PickupDto;
import com.example.orderpickup.exceptions.EmployeeBeingAssignedException;
import com.example.orderpickup.service.kafka.ProducerService;
import com.example.orderpickup.service.pickup.PickupService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PickupController {

    @Autowired
    private PickupService pickupService;

    @Autowired
    private ProducerService producerService;

    @GetMapping(value = "/single")
    @ApiOperation(value  = "Returns list of all single pickings!!")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK" ),
            @ApiResponse(code = 400, message = "BAD REQUEST ERROR")
    })
    public ResponseEntity<List<PickupDto>> getAllSinglePickings(){
        return ResponseEntity.ok(pickupService.getAllSinglePicking());
    }

    @ApiOperation(value  = "Create single picking")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK" ),
            @ApiResponse(code = 400, message = "BAD REQUEST ERROR")
    })
    @PostMapping(value = "/single/create")
    public ResponseEntity<PickupDto> createSinglePicking(@RequestBody PickupDto pickupDto){
        try{
            return ResponseEntity.ok(pickupService.createSinglePicking(pickupDto));
        }catch(OptimisticLockingFailureException e){
            throw new EmployeeBeingAssignedException("The employee you are trying to assign is already assigned or been assigned");
        }
    }

    @ApiOperation(value  = "Cancel single picking")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK" ),
            @ApiResponse(code = 400, message = "BAD REQUEST ERROR")
    })
    @PostMapping(value = "/single/cancel/{id}")
    public ResponseEntity<PickupDto> cancelSinglePicking(@PathVariable("id") String id){
        return ResponseEntity.ok(pickupService.cancelSinglePicking(id));
    }

    @ApiOperation(value  = "Returns list of all batch pickings!!")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK" ),
            @ApiResponse(code = 400, message = "BAD REQUEST ERROR")
    })
    @GetMapping(value = "/batch")
    public ResponseEntity<List<PickupDto>> getAllBatchPickings(){
        return ResponseEntity.ok(pickupService.getAllBatchPicking());
    }

    @ApiOperation(value  = "Create batch picking")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK" ),
            @ApiResponse(code = 400, message = "BAD REQUEST ERROR")
    })
    @PostMapping(value = "/batch/create")
    public ResponseEntity<PickupDto> createBatchPicking(@RequestBody PickupDto pickupDto){
        try{
            return ResponseEntity.ok(pickupService.createBatchPicking(pickupDto));
        }catch(Exception e){
            throw new EmployeeBeingAssignedException("The employee you are trying to assign is already assigned or been assigned");
        }

    }

    @ApiOperation(value  = "Cancel batch picking")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK" ),
            @ApiResponse(code = 400, message = "BAD REQUEST ERROR")
    })
    @PostMapping(value = "/batch/cancel/{id}")
    public ResponseEntity<PickupDto> cancelBatchPicking(@PathVariable("id") String id){
        return ResponseEntity.ok(pickupService.cancelBatchPicking(id));
    }

    @ApiOperation(value  = "Switch from Single to Batch picking based on WareHouse Location")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK" ),
            @ApiResponse(code = 400, message = "BAD REQUEST ERROR")
    })
    @GetMapping(value = "/single/batch/{location}/{batchSize}")
    public ResponseEntity<List<PickupDto>> singleToBatch(@PathVariable("location") String location,
                                                         @PathVariable("batchSize") int batchSize){

        log.info("Entered single to batch controller with location = " + location + "and batchSize = " + batchSize);
        return ResponseEntity.ok(pickupService.singleToBatch(location, batchSize));
    }

    @ApiOperation(value  = "Switch from Batch to Single picking")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK" ),
            @ApiResponse(code = 400, message = "BAD REQUEST ERROR")
    })
    @GetMapping(value = "/batch/single/")
    public ResponseEntity<List<PickupDto>> batchToSingle(){
        return ResponseEntity.ok(pickupService.batchToSingle());
    }

    //Kafka send message when pickup is done
    @PostMapping(value = "/publish/pickup")
    public String publishPickupDone(@RequestBody PickupDto pickupDto){
        log.info("Order Message received in KafkaController: {}", pickupDto);
        producerService.sendPickupDoneMessage(pickupDto);
        return "Picking Done";
    }

}
