package com.musinsa.assignment.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.musinsa.assignment.exception.unchecked.ProductNotFoundException;
import com.musinsa.assignment.repository.ProductRepository;
import com.musinsa.assignment.web.dto.category.CategoryListRequest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("CategoryService 클래스의")
class CategoryServiceTest {

    CategoryService categoryService;
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        categoryService = new CategoryService(productRepository);
    }

    @Nested
    @DisplayName("getLowestProductInCategoryAndBrandList 메서드는")
    class get_lowest_product_in_category_and_brand_list {

        @Nested
        @DisplayName("categoryId가 유효하지 않다면")
        class category_id_is_not_valid {

            @Test
            @DisplayName("ProductNotFoundException이 발생한다.")
            void throw_product_not_found_exception() {
                Long categoryId = -1L;
                Long brandId = 3L;
                List<CategoryListRequest> requests = List.of(new CategoryListRequest(categoryId, brandId));

                given(productRepository.findLowestPriceByCategoryIdAndBrandId(categoryId, brandId))
                    .willReturn(Optional.empty());

                assertThatThrownBy(() -> categoryService.getLowestProductInCategoryAndBrandList(requests))
                    .isInstanceOf(ProductNotFoundException.class);
            }
        }

        @Nested
        @DisplayName("brandId가 유효하지 않다면")
        class brand_id_is_not_valid {

            @Test
            @DisplayName("ProductNotFoundException이 발생한다.")
            void throw_product_not_found_exception() {
                Long categoryId = 1L;
                Long brandId = -3L;
                List<CategoryListRequest> requests = List.of(new CategoryListRequest(categoryId, brandId));

                given(productRepository.findLowestPriceByCategoryIdAndBrandId(categoryId, brandId))
                    .willReturn(Optional.empty());

                assertThatThrownBy(() -> categoryService.getLowestProductInCategoryAndBrandList(requests))
                    .isInstanceOf(ProductNotFoundException.class);
            }

        }
    }
}
