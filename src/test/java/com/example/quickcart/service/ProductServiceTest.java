package com.example.quickcart.service;

import com.example.quickcart.model.Product;
import com.example.quickcart.repository.ProductRepository;
import com.example.quickcart.service.impl.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.example.quickcart.utils.ProductUtil.getMockProduct;
import static com.example.quickcart.utils.ProductUtil.getMockProductWithId;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void testGetAllProducts() {
        int pageNumber = 0;
        int pageSize = 3;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> products = new PageImpl<>(List.of(
                getMockProductWithId(1L), getMockProductWithId(2L), getMockProductWithId(3L)
        ));

        when(productRepository.findAll(pageable)).thenReturn(products);

        Page<Product> productPage = productService.getAllProducts(pageNumber, pageSize);

        Assertions.assertThat(productPage.getSize()).isEqualTo(pageSize);
        Assertions.assertThat(productPage.getContent().get(0).getId()).contains("001");
        Assertions.assertThat(productPage.getContent().get(1).getId()).contains("002");
        Assertions.assertThat(productPage.getContent().get(2).getId()).contains("003");
    }

    @Test
    void testGetProductById() {
        String productId = "0000000000111";

        when(productRepository.findById(productId)).thenReturn(Optional.of(getMockProductWithId(Long.parseLong(productId))));

        Product product = productService.getProductById(productId);

        Assertions.assertThat(product).isNotNull();
        Assertions.assertThat(product.getId()).isEqualTo(productId);
        Assertions.assertThat(product.getName()).contains("Mock Product");
    }

    @Test
    void testAddProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(getMockProductWithId(1L));

        Product savedProduct = productService.addProduct(getMockProduct());

        Assertions.assertThat(savedProduct).isNotNull();
        Assertions.assertThat(savedProduct.getId()).contains("001");
    }

}
