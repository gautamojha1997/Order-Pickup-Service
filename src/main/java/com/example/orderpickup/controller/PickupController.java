package com.example.orderpickup.controller;

import com.example.orderpickup.dtos.PickupDto;
import com.example.orderpickup.service.PickupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PickupController {

    @Autowired
    private PickupService pickupService;

    @GetMapping(value = "/single")
    public ResponseEntity<List<PickupDto>> getAllSinglePickings(){
        return ResponseEntity.ok(pickupService.getAllSinglePicking());
    }

    @PostMapping(value = "/single/create")
    public ResponseEntity<PickupDto> createSinglePicking(@RequestBody PickupDto pickupDto){
        return ResponseEntity.ok(pickupService.createSinglePicking(pickupDto));
    }

    @PostMapping(value = "/single/cancel/{id}")
    public ResponseEntity<PickupDto> cancelSinglePicking(@PathVariable("id") String id){
        return ResponseEntity.ok(pickupService.cancelSinglePicking(id));
    }

    @GetMapping(value = "/batch")
    public ResponseEntity<List<PickupDto>> getAllBatchPickings(){
        return ResponseEntity.ok(pickupService.getAllBatchPicking());
    }

    @PostMapping(value = "/batch/create")
    public ResponseEntity<PickupDto> createBatchPicking(@RequestBody PickupDto pickupDto){
        return ResponseEntity.ok(pickupService.createBatchPicking(pickupDto));
    }

    @PostMapping(value = "/batch/cancel/{id}")
    public ResponseEntity<PickupDto> cancelBatchPicking(@PathVariable("id") String id){
        return ResponseEntity.ok(pickupService.cancelBatchPicking(id));
    }

    @GetMapping(value = "/single/batch/{location}/{batchSize}")
    public ResponseEntity<List<PickupDto>> singleToBatch(@PathVariable("location") String location,
                                                         @PathVariable("batchSize") int batchSize){

        log.info("Entered single to batch controller with location = " + location + "and batchSize = " + batchSize);
        return ResponseEntity.ok(pickupService.singleToBatch(location, batchSize));
    }

    @GetMapping(value = "/batch/single/")
    public ResponseEntity<List<PickupDto>> batchToSingle(){
        return ResponseEntity.ok(pickupService.batchToSingle());
    }

}
