package com.example.quickcart.controller;

import com.example.quickcart.exception.InvalidIdException;
import com.example.quickcart.exception.ProductNotFoundException;
import com.example.quickcart.model.CustomErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleProductNotFoundException(ProductNotFoundException ex) {
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<>(new CustomErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<CustomErrorResponse> handleInvalidIdException(InvalidIdException ex) {
        LOGGER.error(ex.getMessage());
        return new ResponseEntity<>(new CustomErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
