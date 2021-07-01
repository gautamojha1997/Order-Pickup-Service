package com.example.orderpickup.dtos;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Data
public class ToteCartDto {

    @Id
    private String toteCartId;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<ToteDto> tote;

    public ToteCartDto(){
        this.toteCartId = UUID.randomUUID().toString();
    }
}
