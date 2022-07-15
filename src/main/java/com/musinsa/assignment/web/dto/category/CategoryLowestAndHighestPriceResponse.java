package com.musinsa.assignment.web.dto.category;

import com.musinsa.assignment.domain.Product;
import lombok.Getter;

@Getter
public class CategoryLowestAndHighestPriceResponse {
    private CategoryLowestAndHighestPriceDetailResponse min;
    private CategoryLowestAndHighestPriceDetailResponse max;

    public CategoryLowestAndHighestPriceResponse(Product lowestProduct, Product highestProduct) {
        this.min = new CategoryLowestAndHighestPriceDetailResponse(lowestProduct);
        this.max = new CategoryLowestAndHighestPriceDetailResponse(highestProduct);
    }
}
