package com.musinsa.assignment.repository;

import com.musinsa.assignment.domain.Product;

public interface CustomProductRepository {

    Product findLowestPriceByCategoryIdAndBrandId(Long categoryId, Long brandId);

    Product findLowestPriceByCategoryName(String categoryName);

    Product findHighestPriceByCategoryName(String categoryName);
}
