package com.example.orderpickup.dtos;

import com.example.orderpickup.enums.ItemSubstitution;
import lombok.Data;

import javax.persistence.Id;
import java.util.UUID;

@Data
public class ItemsDto {

    @Id
    private String itemId;

    private String name;

    private int itemReqdQty;

    private int itemPickedQty;

    private ItemSubstitution isSubstAllowed;

    private String substItemName;

    public ItemsDto(){
        this.itemId = UUID.randomUUID().toString();
    }
}
