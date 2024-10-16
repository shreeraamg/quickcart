package com.example.quickcart.service;

import com.example.quickcart.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    // Read Operations
    Page<Product> getAllProducts(int pageNumber, int pageSize);
    Page<Product> searchProducts(String query, int pageNumber, int pageSize);
    Page<Product> getProductsByBrand(String brand, int pageNumber, int pageSize);
    Page<Product> getProductsByCategory(String category, int pageNumber, int pageSize);
    Product getProductById(String id);

    // Write and Update Operations
    Product addProduct(Product product);
    String addProducts(List<Product> products);
    Product updateProduct(Product product);
    String deleteProductById(String id);

}
