package com.musinsa.assignment.repository;

import static com.musinsa.assignment.domain.QBrand.brand;
import static com.musinsa.assignment.domain.QCategory.category;
import static com.musinsa.assignment.domain.QProduct.product;

import com.musinsa.assignment.domain.Product;
import com.musinsa.assignment.domain.QCategory;
import com.musinsa.assignment.domain.QProduct;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Product findLowestPriceByCategoryIdAndBrandId(Long categoryId, Long brandId) {
        QProduct productSub = new QProduct("productSub");
        return queryFactory
            .selectFrom(product)
            .join(product.category, category).fetchJoin()
            .join(product.brand, brand).fetchJoin()
            .where(
                category.id.eq(categoryId)
                    .and(brand.id.eq(brandId))
                    .and(product.price.eq(
                        JPAExpressions
                            .select(productSub.price.min())
                            .from(productSub)
                            .where(
                                productSub.category.id.eq(categoryId)
                                    .and(productSub.brand.id.eq(brandId))
                            )
                    ))
            ).fetchFirst();
    }

    @Override
    public Product findLowestPriceByCategoryName(String categoryName) {
        QProduct productSub = new QProduct("productSub");
        QCategory categorySub = new QCategory("categorySub");
        return queryFactory
            .selectFrom(product)
            .join(product.brand).fetchJoin()
            .join(product.category, category).on(category.name.eq(categoryName))
            .where(
                product.price.eq(
                    JPAExpressions
                        .select(productSub.price.min())
                        .from(productSub)
                        .join(productSub.category, categorySub)
                        .on(categorySub.name.eq(categoryName)))
            ).fetchFirst();
    }

    @Override
    public Product findHighestPriceByCategoryName(String categoryName) {
        QProduct productSub = new QProduct("productSub");
        QCategory categorySub = new QCategory("categorySub");
        return queryFactory
            .selectFrom(product)
            .join(product.brand, brand).fetchJoin()
            .join(product.category, category).on(category.name.eq(categoryName))
            .where(
                product.price.eq(
                    JPAExpressions
                        .select(productSub.price.max())
                        .from(productSub)
                        .join(productSub.category, categorySub)
                        .on(categorySub.name.eq(categoryName)))
            ).fetchFirst();
    }
}
