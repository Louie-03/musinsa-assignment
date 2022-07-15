package com.musinsa.assignment.web.dto.category;

import com.musinsa.assignment.domain.Product;
import lombok.Getter;

@Getter
public class CategoryLowestAndHighestPriceDetailResponse {
    private String brandName;
    private int price;

    public CategoryLowestAndHighestPriceDetailResponse(Product product) {
        this.brandName = product.getBrand().getName();
        this.price = product.getPrice();
    }
}
