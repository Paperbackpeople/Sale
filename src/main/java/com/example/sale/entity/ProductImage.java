package com.example.sale.entity;

public class ProductImage {
    private Long id;
    private String imageUrl;
    private Long productId;

    // Constructors
    public ProductImage() {}

    public ProductImage(Long id, String imageUrl, Long productId) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.productId = productId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}