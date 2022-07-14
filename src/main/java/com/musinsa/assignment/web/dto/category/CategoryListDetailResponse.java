package com.musinsa.assignment.web.dto.category;

import com.musinsa.assignment.domain.Brand;
import com.musinsa.assignment.domain.Category;
import com.musinsa.assignment.domain.Product;
import lombok.Getter;

@Getter
public class CategoryListDetailResponse {
      private long categoryId;
      private String categoryName;
      private long brandId;
      private String brandName;
      private int price;

      public CategoryListDetailResponse(Product product) {
            Category category = product.getCategory();
            Brand brand = product.getBrand();
            this.categoryId = category.getId();
            this.categoryName = category.getName();
            this.brandId = brand.getId();
            this.brandName = brand.getName();
            this.price = product.getPrice();
      }
}
