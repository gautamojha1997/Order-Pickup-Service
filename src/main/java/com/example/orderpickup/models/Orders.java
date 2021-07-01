package com.example.orderpickup.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Orders {

    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    private String orderId;

    //Assumed orders created 1 sec back,
    private ZonedDateTime pickedAt = ZonedDateTime.now().plusHours(1);

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Items> items;

    public Orders(){
        this.orderId = UUID.randomUUID().toString();
    }

}
