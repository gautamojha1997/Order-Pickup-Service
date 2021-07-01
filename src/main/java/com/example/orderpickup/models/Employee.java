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
public class Employee {
    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    private String employeeId;

    private String email;
    private String firstName;
    private String lastName;
    private String contactNumber;

    public Employee(){
        this.employeeId = UUID.randomUUID().toString();
    }

}
