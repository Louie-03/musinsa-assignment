package com.musinsa.assignment.web.dto.brand;

import com.musinsa.assignment.domain.Brand;
import com.musinsa.assignment.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BrandDetailProductMinPriceResponse {
    private String name;
    private int totalPrice;

    public BrandDetailProductMinPriceResponse(Brand brand) {
        this.name = brand.getName();
        this.totalPrice = brand.getProducts().stream()
            .mapToInt(Product::getPrice)
            .sum();
    }
}
