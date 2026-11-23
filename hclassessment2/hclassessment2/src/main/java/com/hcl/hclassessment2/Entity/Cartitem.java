package com.hcl.hclassessment2.Entity;

public class Cartitem {
    long cartItemId;
    Cart cart;
    Product product;
    Integer quantity;
    Double price;


    public Cartitem(long cartItemId, Double price, Integer quantity, Product product, Cart cart) {
        this.cartItemId = cartItemId;
        this.price = price;
        this.quantity = quantity;
        this.product = product;
        this.cart = cart;
    }
}
