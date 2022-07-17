package com.musinsa.assignment.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(indexes = @Index(name = "index_category_name", columnList = "name"))
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    @Column(nullable = false, unique = true)
    private String name;
}
