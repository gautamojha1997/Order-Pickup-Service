package com.example.orderpickup.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WareHouse {

    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    private String wareHouseId;

    private String location;

    public WareHouse(){
        this.wareHouseId = UUID.randomUUID().toString();
    }
}
