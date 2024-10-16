package com.example.quickcart.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String productId) {
        super("No Product found with given ID: " + productId);
    }
}
