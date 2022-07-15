package com.musinsa.assignment.web.controller;

import com.musinsa.assignment.service.BrandService;
import com.musinsa.assignment.web.dto.brand.BrandDetailProductMinPriceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/brands")
@RestController
public class BrandController {

    private final BrandService brandService;

    @GetMapping("/{id}/min-prices")
    public BrandDetailProductMinPriceResponse getBrandDetailProductMinPrice(@PathVariable Long id) {
        return brandService.getBrandDetailProductMinPrice(id);
    }
}
