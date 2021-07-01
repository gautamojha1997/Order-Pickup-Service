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
public class Store {

    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    private String storeId;

    private String location;

    public Store(){
        this.storeId = UUID.randomUUID().toString();
    }
}
