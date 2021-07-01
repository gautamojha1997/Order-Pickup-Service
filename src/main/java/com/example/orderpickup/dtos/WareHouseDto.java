package com.example.orderpickup.dtos;

import lombok.Data;

import javax.persistence.Id;
import java.util.UUID;


@Data
public class WareHouseDto {

    @Id
    private String wareHouseId;

    private String location;

    public WareHouseDto(){
        this.wareHouseId = UUID.randomUUID().toString();
    }
}
