package com.example.orderpickup.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class BatchOrderRequiredException extends RuntimeException{
    public BatchOrderRequiredException(String message) {
        super(message);
    }
}
