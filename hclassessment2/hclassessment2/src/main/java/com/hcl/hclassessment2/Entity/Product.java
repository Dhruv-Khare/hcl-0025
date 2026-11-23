package com.hcl.hclassessment2.Entity;

public class Product {
    long productId;
    String productName;
    String description;
    Double price;
    Integer quantity;
    String category;

    public Product(String category, Integer quantity, Double price, String productName, long productId, String description) {
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.productName = productName;
        this.productId = productId;
        this.description = description;
    }
}
