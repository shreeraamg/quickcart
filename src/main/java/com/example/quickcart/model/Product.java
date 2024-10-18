package com.example.quickcart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Document(collection = "products")
public class Product {

    @Id
    private String id;

    @Indexed(unique = false)
    private String name;

    private String description;

    private Double price;

    @JsonIgnore
    private Integer quantity;

    private Category category;

    @Indexed
    private String brand;

    private List<String> tags;

    private String thumbnail;

    private List<String> images;

    @JsonIgnore
    private Boolean isAvailable;

    private Double rating;

    private Boolean isFeatured;

    @CreatedDate
    @JsonIgnore
    private Instant createdAt;

    @JsonIgnore
    private Instant updatedAt;

}
