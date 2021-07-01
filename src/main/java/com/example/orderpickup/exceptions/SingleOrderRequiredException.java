package com.example.orderpickup.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class SingleOrderRequiredException extends RuntimeException{

    public SingleOrderRequiredException(String message) {
        super(message);
    }
}
