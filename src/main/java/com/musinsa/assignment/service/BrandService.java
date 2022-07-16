package com.musinsa.assignment.service;

import com.musinsa.assignment.domain.Brand;
import com.musinsa.assignment.domain.Product;
import com.musinsa.assignment.repository.BrandRepository;
import com.musinsa.assignment.repository.CategoryRepository;
import com.musinsa.assignment.repository.ProductRepository;
import com.musinsa.assignment.web.dto.brand.BrandDetailProductMinPriceResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

//    TODO : Brand 엔티티에서 객체 그래프 탐색을 하도록 수정해보기
    public BrandDetailProductMinPriceResponse getBrandDetailProductMinPrice(Long id) {
        List<Long> categoryIds = categoryRepository.findAllId();

        List<Long> productIds = new ArrayList<>();
        for (Long categoryId : categoryIds) {
            Product product = productRepository.findLowestPriceByCategoryIdAndBrandId(
                    categoryId, id).orElseThrow(IllegalStateException::new);
            productIds.add(product.getId());
        }
        Brand brand = brandRepository.findByIdAndProductIds(id, productIds);
        return new BrandDetailProductMinPriceResponse(brand);
    }
}
