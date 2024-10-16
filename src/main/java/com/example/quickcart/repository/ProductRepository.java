package com.example.quickcart.repository;

import com.example.quickcart.model.Category;
import com.example.quickcart.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    Page<Product> findByNameContainingIgnoreCaseOrBrandContainingIgnoreCase(String name, String brand, Pageable pageable);

    Page<Product> findByBrand(String brand, Pageable pageable);

    Page<Product> findByCategory(Category category, Pageable pageable);

}
