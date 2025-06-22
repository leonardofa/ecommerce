package com.ecommerce.infrastructure.rest.controller;

import com.ecommerce.domain.model.Product;
import com.ecommerce.domain.port.ProductService;
import com.ecommerce.infrastructure.rest.dto.ProductRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    private Product testProduct;
    private ProductRequest productRequest;

    @BeforeEach
    void setUp() {
        testProduct = Product.builder()
                .sku("TST-12345678")
                .name("Test Product")
                .price(10.0)
                .stock(5)
                .enabled(true)
                .build();

        productRequest = ProductRequest.builder()
                .name("New Product")
                .price(15.0)
                .initialStock(10)
                .build();
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAllProducts_ShouldReturnProducts() throws Exception {
        // Arrange
        Product product1 = Product.builder().sku("SKU1").name("Product 1").price(10.0).stock(5).enabled(true).build();
        Product product2 = Product.builder().sku("SKU2").name("Product 2").price(20.0).stock(10).enabled(true).build();
        List<Product> products = Arrays.asList(product1, product2);
        
        when(productService.getAllProducts()).thenReturn(products);

        // Act & Assert
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].sku", is("SKU1")))
                .andExpect(jsonPath("$[0].name", is("Product 1")))
                .andExpect(jsonPath("$[1].sku", is("SKU2")))
                .andExpect(jsonPath("$[1].name", is("Product 2")));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getProduct_ShouldReturnProduct() throws Exception {
        // Arrange
        when(productService.getProductBySku("TST-12345678")).thenReturn(testProduct);

        // Act & Assert
        mockMvc.perform(get("/api/products/TST-12345678"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.sku", is("TST-12345678")))
                .andExpect(jsonPath("$.name", is("Test Product")))
                .andExpect(jsonPath("$.price", is(10.0)))
                .andExpect(jsonPath("$.stock", is(5)))
                .andExpect(jsonPath("$.enabled", is(true)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createProduct_ShouldCreateAndReturnProduct() throws Exception {
        // Arrange
        when(productService.createProduct(anyString(), anyDouble(), anyInt())).thenReturn(testProduct);

        // Act & Assert
        mockMvc.perform(post("/api/products")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.sku", is("TST-12345678")))
                .andExpect(jsonPath("$.name", is("Test Product")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateProduct_ShouldUpdateAndReturnProduct() throws Exception {
        // Arrange
        Product updatedProduct = Product.builder()
                .sku("TST-12345678")
                .name("Updated Product")
                .price(20.0)
                .stock(5)
                .enabled(true)
                .build();
        
        when(productService.updateProduct(eq("TST-12345678"), anyString(), anyDouble())).thenReturn(updatedProduct);

        // Act & Assert
        mockMvc.perform(put("/api/products/TST-12345678")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.sku", is("TST-12345678")))
                .andExpect(jsonPath("$.name", is("Updated Product")))
                .andExpect(jsonPath("$.price", is(20.0)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void enableProduct_ShouldEnableAndReturnProduct() throws Exception {
        // Arrange
        Product enabledProduct = Product.builder()
                .sku("TST-12345678")
                .name("Test Product")
                .price(10.0)
                .stock(5)
                .enabled(true)
                .build();
        
        when(productService.enableProduct("TST-12345678")).thenReturn(enabledProduct);

        // Act & Assert
        mockMvc.perform(patch("/api/products/TST-12345678/enable")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.enabled", is(true)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void disableProduct_ShouldDisableAndReturnProduct() throws Exception {
        // Arrange
        Product disabledProduct = Product.builder()
                .sku("TST-12345678")
                .name("Test Product")
                .price(10.0)
                .stock(5)
                .enabled(false)
                .build();
        
        when(productService.disableProduct("TST-12345678")).thenReturn(disabledProduct);

        // Act & Assert
        mockMvc.perform(patch("/api/products/TST-12345678/disable")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.enabled", is(false)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteProduct_ShouldDeleteProduct() throws Exception {
        // Arrange
        doNothing().when(productService).deleteProduct("TST-12345678");

        // Act & Assert
        mockMvc.perform(delete("/api/products/TST-12345678")
                .with(csrf()))
                .andExpect(status().isNoContent());
        
        verify(productService).deleteProduct("TST-12345678");
    }
}