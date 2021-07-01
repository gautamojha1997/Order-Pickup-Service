package com.example.orderpickup.exceptions.handler;

import com.example.orderpickup.exceptions.BatchOrderRequiredException;
import com.example.orderpickup.exceptions.LocationNotFoundException;
import com.example.orderpickup.exceptions.PickupNotFoundException;
import com.example.orderpickup.exceptions.SingleOrderRequiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //handling BatchOrderRequiredException
    @ExceptionHandler(BatchOrderRequiredException.class)
    public ResponseEntity<Object> handleBatchOrderRequiredException(BatchOrderRequiredException exception, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    //handling SingleOrderRequiredException
    @ExceptionHandler(SingleOrderRequiredException.class)
    public ResponseEntity<Object> handleSingleOrderRequiredException(SingleOrderRequiredException exception, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    //handling LocationNotFoundException
    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity<Object> handleLocationNotFoundException(LocationNotFoundException exception, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    //handling PickupNotFoundException
    @ExceptionHandler(PickupNotFoundException.class)
    public ResponseEntity<Object> handlePickupNotFoundException(PickupNotFoundException exception, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    //handle global exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> globalExceptionHandling(Exception exception, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(new Date(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
