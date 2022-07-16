package com.musinsa.assignment.repository;

import com.musinsa.assignment.domain.Product;
import java.util.Optional;

public interface CustomProductRepository {

    Optional<Product> findLowestPriceByCategoryIdAndBrandId(Long categoryId, Long brandId);

    Optional<Product> findLowestPriceByCategoryName(String categoryName);

    Optional<Product> findHighestPriceByCategoryName(String categoryName);
}
