package com.invop.iobackend.controller.advice;

import com.invop.iobackend.exceptions.ErrorBody;
import com.invop.iobackend.exceptions.ProductExistsException;
import com.invop.iobackend.exceptions.ProductNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductControllerAdvice {

    @ExceptionHandler(value = {ProductNotExistsException.class})
    protected ResponseEntity<ErrorBody> productNoExists () {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("El producto no existe.")
                .build(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = {ProductExistsException.class})
    protected ResponseEntity<ErrorBody> productExists () {
        return new ResponseEntity(ErrorBody.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("El producto ya existe.")
                .build(),
                HttpStatus.NOT_FOUND
        );
    }
}
