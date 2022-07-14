package com.musinsa.assignment.web.dto.category;

import lombok.Getter;

@Getter
public class CategoryListRequest {
    private Long categoryId;
    private Long brandId;

    public CategoryListRequest(Long categoryId, Long brandId) {
        this.categoryId = categoryId;
        this.brandId = brandId;
    }
}
