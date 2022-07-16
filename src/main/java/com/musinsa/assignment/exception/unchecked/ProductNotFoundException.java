package com.musinsa.assignment.exception.unchecked;

public class ProductNotFoundException extends BusinessException {
    private static String ERROR_CODE = "PRODUCT001";
    private static String DEFAULT_MESSAGE = "상품을 찾을 수 없습니다.";

    public ProductNotFoundException() {
        super(DEFAULT_MESSAGE, ERROR_CODE);
    }
}
