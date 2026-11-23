package com.hcl.hclassessment2.Entity;

public class OrderItem {
    long orderItem;
    CustomerOrder customerOrder;
    Product product;
    Integer quantity;
    Double price;

    public OrderItem(long orderItem, Double price, Integer quantity, Product product, CustomerOrder customerOrder) {
        this.orderItem = orderItem;
        this.price = price;
        this.quantity = quantity;
        this.product = product;
        this.customerOrder = customerOrder;
    }
}
