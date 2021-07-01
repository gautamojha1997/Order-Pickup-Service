package com.example.orderpickup.dtos;

import lombok.Data;

import javax.persistence.Id;
import java.util.UUID;

@Data
public class StoreDto {

    @Id
    private String storeId;

    private String location;

    public StoreDto(){
        this.storeId = UUID.randomUUID().toString();
    }
}
