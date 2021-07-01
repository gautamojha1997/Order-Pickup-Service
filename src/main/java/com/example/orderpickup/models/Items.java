package com.example.orderpickup.models;

import com.example.orderpickup.enums.ItemSubstitution;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Items {

    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    private String itemId;

    private String name;

    private int itemReqdQty;

    private int itemPickedQty;

    private ItemSubstitution isSubstAllowed;

    private String substItemName;

    public Items() {
        this.itemId = UUID.randomUUID().toString();
    }
}

