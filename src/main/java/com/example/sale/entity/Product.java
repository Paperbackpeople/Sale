package com.example.sale.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long productId;
    private String name;
    private String shortDescription;
    private BigDecimal price;
    private int saleVolume;
    private float rating;
    private String fullDescription;
    private List<String> images;
    private List<Category> categories;
}