package com.example.orderpickup.mappers;

import com.example.orderpickup.dtos.PickupDto;
import com.example.orderpickup.models.Pickup;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PickupMapper {

    @Autowired
    ModelMapper modelMapper;

    //Converts dto to entity
    public Pickup mapToEntity(PickupDto pickupDto){
        return modelMapper.map(pickupDto, Pickup.class);
    }

    //Converts entity to dto
    public PickupDto mapToDto(Pickup pickup){
        return modelMapper.map(pickup, PickupDto.class);
    }
}
