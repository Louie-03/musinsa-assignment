package com.musinsa.assignment.repository;

import com.musinsa.assignment.domain.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p "
        + "join fetch p.brand "
        + "join fetch p.category where "
        + "p.price = (select min(p1.price) from Product p1 where "
        + "        p1.category.id = :categoryId and "
        + "        p1.brand.id = :brandId) and "
        + "p.category.id = :categoryId and "
        + "p.brand.id = :brandId ")
    List<Product> find(@Param("categoryId") Long categoryId, @Param("brandId") Long brandId);
}
