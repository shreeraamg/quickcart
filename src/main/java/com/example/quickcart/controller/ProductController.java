package com.example.quickcart.controller;

import com.example.quickcart.model.Product;
import com.example.quickcart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<Product> products = productService.getAllProducts(pageNumber, pageSize);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Product>> searchProducts(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<Product> products = productService.searchProducts(query, pageNumber, pageSize);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/brand")
    public ResponseEntity<Page<Product>> getProductsByBrand(
            @RequestParam String brand,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<Product> products = productService.getProductsByBrand(brand, pageNumber, pageSize);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<Page<Product>> getProductsByCategory(
            @RequestParam String category,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<Product> products = productService.getProductsByCategory(category, pageNumber, pageSize);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product savedProduct = productService.addProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<String> addProducts(@RequestBody List<Product> products) {
        String result = productService.addProducts(products);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
        product.setId(id);
        Product updatedProduct = productService.updateProduct(product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        String result = productService.deleteProductById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
