package com.example.quickcart.utils;

import com.example.quickcart.model.Category;
import com.example.quickcart.model.Product;

import java.util.List;

public class ProductUtil {
    public static Product getMockProduct() {
        Product product = new Product();

        product.setId(String.valueOf(System.currentTimeMillis()));
        product.setName("Mock Product " + Long.parseLong(product.getId()) % 100);
        product.setDescription("Description for Mock Product");
        product.setPrice(999.99d);
        product.setCategory(Category.AUTOMOTIVE);
        product.setBrand("ABC & Co");
        product.setTags(List.of("Mock 1", "Tag 1", "Mock Tag"));
        product.setThumbnail("https://example.com/thumbnail.jpg");
        product.setImages(List.of("https://example.com/image1.jpg", "https://example.com/image2.jpg"));
        product.setIsAvailable(true);
        product.setRating(0.0d);
        product.setIsFeatured(false);

        return product;
    }

    public static Product getMockProductWithId(Long id) {
        Product product = getMockProduct();
        product.setId(String.format("%013d", id));

        return product;
    }

    public static Product getMockProductWithName(String name) {
        Product product = getMockProduct();
        product.setName(name);

        return product;
    }
}
