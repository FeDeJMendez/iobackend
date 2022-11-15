package com.invop.iobackend.controller.advice;

import com.invop.iobackend.exceptions.ErrorBody;
import com.invop.iobackend.exceptions.InsufficientStockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SaleControllerAdvice {

    @ExceptionHandler(value = {InsufficientStockException.class})
    protected ResponseEntity<ErrorBody> insufficientStock () {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("Stock Insuficiente.")
                .build(),
                HttpStatus.NOT_FOUND
        );
    }
}
