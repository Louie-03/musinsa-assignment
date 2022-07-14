package com.musinsa.assignment.repository;

import com.musinsa.assignment.domain.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c.id from Category c")
    List<Long> findAllId();
}
