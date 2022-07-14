package com.musinsa.assignment.repository;

import com.musinsa.assignment.domain.Brand;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query("select b from Brand b "
        + "join b.products p on p.id in :productIds "
        + "where b.id = :id")
    Brand findByIdAndProductIds(@Param("id") Long id, @Param("productIds") List<Long> productIds);

}
