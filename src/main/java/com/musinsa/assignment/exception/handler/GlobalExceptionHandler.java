package com.musinsa.assignment.exception.handler;

import com.musinsa.assignment.exception.response.ExceptionResponse;
import com.musinsa.assignment.exception.unchecked.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse exception(ProductNotFoundException exception) {
        return new ExceptionResponse(exception);
    }
}
