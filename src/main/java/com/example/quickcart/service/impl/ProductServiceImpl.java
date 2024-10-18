package com.example.quickcart.service.impl;

import com.example.quickcart.exception.InvalidIdException;
import com.example.quickcart.exception.ProductNotFoundException;
import com.example.quickcart.mapper.ProductMapper;
import com.example.quickcart.model.Category;
import com.example.quickcart.model.Product;
import com.example.quickcart.repository.ProductRepository;
import com.example.quickcart.service.ProductService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(
            ProductMapper productMapper,
            ProductRepository productRepository
    ) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    @Override
    public Page<Product> getAllProducts(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> searchProducts(String query, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findByNameContainingIgnoreCaseOrBrandContainingIgnoreCase(query, query, pageable);
    }

    @Override
    public Page<Product> getProductsByBrand(String brand, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findByBrand(brand, pageable);
    }

    @Override
    public Page<Product> getProductsByCategory(String category, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Category categoryEnum = Category.valueOf(category);
        return productRepository.findByCategory(categoryEnum, pageable);
    }

    @Override
    public Product getProductById(String id) {
        Matcher matcher = Pattern.compile("^\\d{13,}$").matcher(id);
        if (!matcher.matches())
            throw new InvalidIdException("Invalid Product Id Provided.");
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Product addProduct(Product product) {
        product.setId(String.valueOf(System.currentTimeMillis()));
        product.setRating(0.0d);
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());

        return productRepository.save(product);
    }

    @Override
    public String addProducts(List<Product> products) {
        String baseTimestamp = String.valueOf(System.currentTimeMillis() / 100);
        for (int i=0; i<products.size(); i++) {
            products.get(i).setId(baseTimestamp + String.format("%02d", i));
        }
        List<Product> savedProducts = productRepository.saveAll(products);
        if (products.size() == savedProducts.size())
            return "All Products Saved Successfully";
        else
            return String.format("Saved %d of %d products. Kindly verify once", savedProducts.size(), products.size());
    }

    @Override
    public Product updateProduct(Product product) {
        return null;
    }

    @Override
    public String deleteProductById(String id) {
        try {
            productRepository.deleteById(id);
            return String.format("Product with Id: %s deleted successfully", id);
        } catch (Exception ex) {
            return String.format("Failed to delete product with Id: %s. Please try again", id);
        }
    }

    @Override
    public String uploadCsv(MultipartFile file) throws IOException, CsvException {
        CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()));
        List<String[]> rows = csvReader.readAll();
        rows.remove(0);
        for (String[] row : rows) {
            Product product = productMapper.mapToProduct(row);
//            System.out.println(product);
            System.out.println(product.getId() == null);
        }
        return "Uploading CSV";
    }
}
