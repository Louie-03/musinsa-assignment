package com.musinsa.assignment.exception.response;

import com.musinsa.assignment.exception.unchecked.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ExceptionResponse {
    private String errorCode;
    private String message;

    public ExceptionResponse(BusinessException exception) {
        this.errorCode = exception.getErrorCode();
        this.message = exception.getMessage();
    }
}
