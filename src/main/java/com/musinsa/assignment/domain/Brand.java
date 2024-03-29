package com.musinsa.assignment.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;

@Getter
@Entity
public class Brand {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "brand")
    private List<Product> products = new ArrayList<>();

    @Column(nullable = false, unique = true)
    private String name;

}
