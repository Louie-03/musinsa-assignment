package com.musinsa.assignment.web.dto.category;

import com.musinsa.assignment.domain.Category;
import com.musinsa.assignment.domain.Product;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class CategoryListResponse {
    private int totalPrice;
    private List<CategoryListDetailResponse> details;

    public CategoryListResponse(List<Product> products) {
        this.totalPrice = products.stream()
            .mapToInt(Product::getPrice)
            .sum();
        this.details = products.stream()
            .map(CategoryListDetailResponse::new)
            .collect(Collectors.toList());
    }
}
