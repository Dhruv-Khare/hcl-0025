package com.hcl.hclassessment2.Entity;

import java.time.LocalDateTime;

public class CustomerOrder {
    long orderId;
    Customer customer;
    LocalDateTime orderDate;
    Double totalAmount;
    String orderStatus;

    public CustomerOrder(long orderId, String orderStatus, Double totalAmount, Customer customer, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.customer = customer;
        this.orderDate = orderDate;
    }
}
