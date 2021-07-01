package com.example.orderpickup.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tote {

    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    private String toteId;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Items> items;

//    @ManyToOne(cascade = {CascadeType.ALL})
//    @JoinColumn(name = "tote_cart_id")
//    private ToteCart toteCart;

    public Tote(){
        this.toteId = UUID.randomUUID().toString();
    }


}
