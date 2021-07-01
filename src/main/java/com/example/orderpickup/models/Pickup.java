package com.example.orderpickup.models;

import com.example.orderpickup.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pickup {

    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    private String pickId;

    private ZonedDateTime pickUpCreated = ZonedDateTime.now();

    @Enumerated(EnumType.STRING)
    private Status status;

    private String zone;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "wareHouse_id")
    private WareHouse wareHouse;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "emp_id")
    private Employee employee;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "orders_pickup",
            joinColumns = @JoinColumn(name = "pick_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    private List<Orders> ordersList;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Tote> toteList;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<ToteCart> toteCarts;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "emp_perf_id")
    private EmployeePerformance employeePerformance;

    public Pickup(){
        this.pickId = UUID.randomUUID().toString();
    }
}
