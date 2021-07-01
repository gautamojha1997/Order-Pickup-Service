package com.example.orderpickup.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ToteCart {

    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    private String toteCartId;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Tote> tote;

    public ToteCart(){
        this.toteCartId = UUID.randomUUID().toString();
    }
}
