package com.example.orderpickup.service;

import com.example.orderpickup.dtos.PickupDto;

import java.util.List;

public interface PickupService {

    List<PickupDto> getAllSinglePicking();
    PickupDto createSinglePicking(PickupDto pickupDto);
    PickupDto cancelSinglePicking(String id);

    List<PickupDto> getAllBatchPicking();
    PickupDto createBatchPicking(PickupDto pickupDto);
    PickupDto cancelBatchPicking(String id);

    List<PickupDto> singleToBatch(String location, int batchSize);
    List<PickupDto> batchToSingle();
}
