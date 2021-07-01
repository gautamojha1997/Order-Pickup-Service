package com.example.orderpickup.dtos;

import lombok.Data;

import javax.persistence.Id;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class OrdersDto {
    @Id
    private String orderId;

    //Assumed orders created 1 sec back,
    private ZonedDateTime pickedAt = ZonedDateTime.now().plusHours(1);

    private List<ItemsDto> items;

    public OrdersDto(){
        this.orderId = UUID.randomUUID().toString();
    }
}
