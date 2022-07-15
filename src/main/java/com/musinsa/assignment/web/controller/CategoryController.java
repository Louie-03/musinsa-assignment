package com.musinsa.assignment.web.controller;

import com.musinsa.assignment.service.CategoryService;
import com.musinsa.assignment.web.dto.category.CategoryListRequest;
import com.musinsa.assignment.web.dto.category.CategoryListResponse;
import com.musinsa.assignment.web.dto.category.CategoryLowestAndHighestPriceResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/categories")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public CategoryListResponse getLowestProductInCategoryAndBrandList(
        @RequestBody List<CategoryListRequest> requests) {
        return categoryService.getLowestProductInCategoryAndBrandList(requests);
    }

    @GetMapping("/min-and-max-price")
    public CategoryLowestAndHighestPriceResponse getCategoryLowestAndHighestPrice(
        @RequestParam("category-name") String categoryName) {
        return categoryService.getCategoryLowestAndHighestPrice(categoryName);
    }
}
