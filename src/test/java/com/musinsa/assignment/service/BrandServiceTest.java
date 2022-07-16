package com.musinsa.assignment.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.musinsa.assignment.exception.unchecked.ProductNotFoundException;
import com.musinsa.assignment.repository.BrandRepository;
import com.musinsa.assignment.repository.CategoryRepository;
import com.musinsa.assignment.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayName("BrandService 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
class BrandServiceTest {

    private BrandService brandService;
    private BrandRepository brandRepository;
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        brandRepository = mock(BrandRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        productRepository = mock(ProductRepository.class);
        brandService = new BrandService(brandRepository, categoryRepository, productRepository);
    }

    @Test
    void brandId가_유효하지_않은_경우_ProductNotFoundException이_발생해야_한다() {
        given(categoryRepository.findAllId())
            .willReturn(List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L));

        given(productRepository.findLowestPriceByCategoryIdAndBrandId(anyLong(), anyLong()))
            .willReturn(Optional.empty());

        assertThatThrownBy(() -> brandService.getBrandDetailProductMinPrice(-1000000L))
            .isInstanceOf(ProductNotFoundException.class);
    }

}
