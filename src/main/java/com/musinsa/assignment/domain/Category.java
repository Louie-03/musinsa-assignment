package com.musinsa.assignment.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;

@Getter
@Entity
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    private String name;

    public Product getFirstProduct() {
        return products.get(0);
    }
}
