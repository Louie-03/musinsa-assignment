package com.musinsa.assignment.web.dto.category;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CategoryListRequest {
    private Long categoryId;
    private Long brandId;

    public CategoryListRequest() {
    }

    public CategoryListRequest(Long categoryId, Long brandId) {
        this.categoryId = categoryId;
        this.brandId = brandId;
    }
}
