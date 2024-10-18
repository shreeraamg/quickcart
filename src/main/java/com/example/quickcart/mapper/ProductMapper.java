package com.example.quickcart.mapper;

import com.example.quickcart.model.Category;
import com.example.quickcart.model.Product;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ProductMapper {

    public Product mapToProduct(String[] csvRow) {
        Product product = new Product();

        product.setId(csvRow[0].isEmpty() || csvRow[0].equals("null") ? null : csvRow[0]);
        product.setName(csvRow[1]);
        product.setDescription(csvRow[2]);
        product.setPrice(Double.parseDouble(csvRow[3]));
        product.setQuantity(Integer.parseInt(csvRow[4]));
        product.setCategory(Category.valueOf(csvRow[5]));
        product.setBrand(csvRow[6]);
        product.setTags(Arrays.stream(csvRow[7].split(";")).toList());
        product.setThumbnail(csvRow[8]);
        product.setImages(Arrays.stream(csvRow[9].split(";")).toList());
        product.setIsAvailable(Boolean.getBoolean(csvRow[10]));
        product.setIsFeatured(Boolean.getBoolean(csvRow[11]));

        return product;
    }
}
