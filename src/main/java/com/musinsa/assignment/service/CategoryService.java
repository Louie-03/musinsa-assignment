package com.musinsa.assignment.service;

import com.musinsa.assignment.domain.Product;
import com.musinsa.assignment.exception.unchecked.ProductNotFoundException;
import com.musinsa.assignment.repository.ProductRepository;
import com.musinsa.assignment.web.dto.category.CategoryListRequest;
import com.musinsa.assignment.web.dto.category.CategoryListResponse;
import com.musinsa.assignment.web.dto.category.CategoryLowestAndHighestPriceResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryService {

    private final ProductRepository productRepository;

    public CategoryListResponse getLowestProductInCategoryAndBrandList(
        List<CategoryListRequest> requests) {

        List<Product> products = new ArrayList<>();
        for (CategoryListRequest request : requests) {
            Product product = productRepository.findLowestPriceByCategoryIdAndBrandId(
                    request.getCategoryId(), request.getBrandId())
                .orElseThrow(ProductNotFoundException::new);
            products.add(product);
        }
        return new CategoryListResponse(products);
    }

    @Cacheable("getCategoryLowestAndHighestPrice")
    public CategoryLowestAndHighestPriceResponse getCategoryLowestAndHighestPrice(String name) {
        Product lowestProduct = productRepository.findLowestPriceByCategoryName(name)
            .orElseThrow(ProductNotFoundException::new);
        Product highestProduct = productRepository.findHighestPriceByCategoryName(name)
            .orElseThrow(ProductNotFoundException::new);
        return new CategoryLowestAndHighestPriceResponse(lowestProduct, highestProduct);
    }
}
