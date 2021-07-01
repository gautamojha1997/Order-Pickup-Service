package com.example.orderpickup.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class EmployeeBeingAssignedException extends RuntimeException{
    public EmployeeBeingAssignedException(String message) {
        super(message);
    }
}
