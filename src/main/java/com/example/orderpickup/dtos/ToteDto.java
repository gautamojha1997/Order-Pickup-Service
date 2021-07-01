package com.example.orderpickup.dtos;

import lombok.Data;

import javax.persistence.Id;
import java.util.List;
import java.util.UUID;

@Data
public class ToteDto {

    @Id
    private String toteId;

    private List<ItemsDto> items;

    public ToteDto(){
        this.toteId = UUID.randomUUID().toString();
    }
}
