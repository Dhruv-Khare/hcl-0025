package com.hcl.hclassessment2.Entity;

import java.time.LocalDate;

public class Cart {
   long cartId;
   Customer customer;
   LocalDate createdDate;

    public Cart(long cartId, LocalDate createdDate, Customer customer) {
        this.cartId = cartId;
        this.createdDate = createdDate;
        this.customer = customer;
    }
}
