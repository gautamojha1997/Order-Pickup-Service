package com.example.orderpickup.dtos;

import lombok.Data;

import javax.persistence.Id;
import java.util.UUID;

@Data
public class EmpDto {

    @Id
    private String employeeId;

    private String email;
    private String firstName;
    private String lastName;
    private String contactNumber;

    public EmpDto(){
        this.employeeId = UUID.randomUUID().toString();
    }
}
