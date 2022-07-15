package com.musinsa.assignment.repository;

import com.musinsa.assignment.domain.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p "
        + "join p.brand "
        + "join p.category where "
        + "p.price = (select min(p1.price) from Product p1 where "
        + "    p1.category.id = :categoryId and "
        + "    p1.brand.id = :brandId) and "
        + "p.category.id = :categoryId and "
        + "p.brand.id = :brandId ")
    List<Product> findLowestPriceByCategoryIdAndBrandId(@Param("categoryId") Long categoryId, @Param("brandId") Long brandId);

    @Query("select p from Product p "
        + "join fetch p.brand "
        + "join p.category c where "
        + "p.price = (select min(p1.price) from Product p1 where "
        + "    p1.category.name = :categoryName) and "
        + "c.name = :categoryName ")
    List<Product> findLowestPriceByCategoryName(@Param("categoryName") String categoryName);

    @Query("select p from Product p "
        + "join fetch p.brand "
        + "join p.category c where "
        + "p.price = (select max(p1.price) from Product p1 where "
        + "    p1.category.name = :categoryName) and "
        + "c.name = :categoryName ")
    List<Product> findHighestPriceByCategoryName(@Param("categoryName") String categoryName);
}
