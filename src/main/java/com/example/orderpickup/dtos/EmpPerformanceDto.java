package com.example.orderpickup.dtos;

import lombok.Data;

import javax.persistence.Id;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class EmpPerformanceDto {

    @Id
    private String empPerId;

    private ZonedDateTime startTime = ZonedDateTime.now();

    private ZonedDateTime endTime = ZonedDateTime.now().plusHours((long) (Math.random()*(10-4+1) + 4));
    //private EmpDto employee;

    public EmpPerformanceDto(){
        this.empPerId = UUID.randomUUID().toString();
    }
}
