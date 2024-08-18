package com.example.sale.entity;


public class Category {
    private Long categoryId;
    private String name;

    // Constructors
    public Category() {}

    public Category(Long categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    // Getters and Setters
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name; // or any other meaningful representation
    }
}