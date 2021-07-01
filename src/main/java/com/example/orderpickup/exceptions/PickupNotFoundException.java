package com.example.orderpickup.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PickupNotFoundException extends RuntimeException{
    public PickupNotFoundException(String message) {
        super(message);
    }
}
