package com.example.orderpickup.dtos;

import com.example.orderpickup.enums.Status;
import lombok.Data;

import javax.persistence.Id;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class PickupDto {

    @Id
    private String pickId;

    private ZonedDateTime pickUpCreated = ZonedDateTime.now();

    private Status status;

    private String zone;

    private WareHouseDto wareHouse;

    private StoreDto store;

    private EmpDto employee;

    private List<OrdersDto> ordersList;

    private List<ToteDto> toteList;

    private List<ToteCartDto> toteCarts;

    private EmpPerformanceDto employeePerformance;

    public PickupDto(){
        this.pickId = UUID.randomUUID().toString();
    }
}
