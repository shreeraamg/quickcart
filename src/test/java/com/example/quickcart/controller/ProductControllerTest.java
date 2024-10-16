package com.example.quickcart.controller;

import com.example.quickcart.exception.InvalidIdException;
import com.example.quickcart.exception.ProductNotFoundException;
import com.example.quickcart.model.Product;
import com.example.quickcart.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static com.example.quickcart.utils.ProductUtil.getMockProduct;
import static com.example.quickcart.utils.ProductUtil.getMockProductWithId;
import static com.example.quickcart.utils.ProductUtil.getMockProductWithName;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.endsWith;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    void testAddProduct() throws Exception {
        Product mockProduct = getMockProduct();

        Mockito.when(productService.addProduct(Mockito.any(Product.class))).thenReturn(mockProduct);

        ResultActions response =mvc.perform(MockMvcRequestBuilders.post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(mockProduct)));

        response
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(containsStringIgnoringCase("Mock Product")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(999.99))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("ABC & Co"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tags").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isFeatured").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$.images").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isAvailable").doesNotExist());
    }

    @Test
    void testGetAllProducts() throws Exception {
        Page<Product> productPage = new PageImpl<>(List.of(getMockProductWithId(1L), getMockProductWithId(2L), getMockProductWithId(3L)));

        Mockito.when(productService.getAllProducts(0, 10)).thenReturn(productPage);

        ResultActions response = mvc.perform(MockMvcRequestBuilders.get("/api/products"));

        response
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(endsWith("001")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].id").value(endsWith("002")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[2].id").value(endsWith("003")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()").value(productPage.getSize()));
    }

    @Test
    void testSearchProducts() throws Exception {
        Page<Product> productPage = new PageImpl<>(List.of(getMockProductWithName("SmartPhone"), getMockProductWithName("Wireless Headphones")));

        Mockito.when(productService.searchProducts("phone", 0, 10)).thenReturn(productPage);

        ResultActions response = mvc.perform(MockMvcRequestBuilders.get("/api/products/search?query=phone"));

        response
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value(containsStringIgnoringCase("phone")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].name").value(containsStringIgnoringCase("phone")));
    }

    @Test
    void testGetProductById() throws Exception {
        String productId = "0000000000010";
        Mockito.when(productService.getProductById(productId)).thenReturn(getMockProductWithId(Long.parseLong(productId)));

        ResultActions response = mvc.perform(MockMvcRequestBuilders.get("/api/products/" + productId));

        response
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(productId));
    }

    @Test
    void testGetProductById_NotFound() throws Exception {
        String productId = "0000000000111";
        Mockito.when(productService.getProductById(productId)).thenThrow(new ProductNotFoundException(productId));

        ResultActions response = mvc.perform(MockMvcRequestBuilders.get("/api/products/" + productId));

        response
                .andExpect(MockMvcResultMatchers.status().is(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(containsStringIgnoringCase(productId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timeStamp").exists());
    }

    @Test
    void testGetProductById_InvalidId() throws Exception {
        String productId = "INVALID";
        String errorMessage = "Invalid Product Id Provided.";
        Mockito.when(productService.getProductById(productId)).thenThrow(new InvalidIdException(errorMessage));

        ResultActions response = mvc.perform(MockMvcRequestBuilders.get("/api/products/" + productId));

        response
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value(errorMessage))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timeStamp").exists());
    }

}
