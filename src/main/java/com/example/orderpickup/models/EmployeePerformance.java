package com.example.orderpickup.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeePerformance {

    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    private String empPerId;

    private ZonedDateTime startTime = ZonedDateTime.now();


    //Setting the end time for pick up by an emp as randomly between 2 to 15 hours for performance metrics.
    //Formula used is Math.random() * (max - min + 1) + min
    //long delTimeRange = (long) (Math.random()*(15-2+1) +2);
    private ZonedDateTime endTime = ZonedDateTime.now().plusHours((long) (Math.random()*(10-4+1) + 4));

//    @ManyToOne(cascade = {CascadeType.ALL})
//    @JoinColumn(name = "emp_id")
//    private Employee employee;

    public EmployeePerformance(){
        empPerId = UUID.randomUUID().toString();
    }

}
