package com.musinsa.assignment.exception.unchecked;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private String errorCode;

    public BusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
