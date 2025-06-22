package com.ecommerce.application.service;

import com.ecommerce.domain.model.Product;
import com.ecommerce.domain.port.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = Product.builder()
                .sku("TST-12345678")
                .name("Test Product")
                .price(10.0)
                .stock(5)
                .enabled(true)
                .build();
    }

    @Test
    void createProduct_ShouldCreateAndReturnProduct() {
        // Arrange
        when(productRepository.existsByName(anyString())).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Product result = productService.createProduct("New Product", 15.0, 10);

        // Assert
        assertNotNull(result);
        assertEquals("New Product", result.getName());
        assertEquals(15.0, result.getPrice());
        assertEquals(10, result.getStock());
        assertTrue(result.isEnabled());
        assertTrue(result.getSku().startsWith("NEW-"));
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void createProduct_WithExistingName_ShouldThrowException() {
        // Arrange
        when(productRepository.existsByName("Existing Product")).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> productService.createProduct("Existing Product", 15.0, 10));
        assertEquals("A product with name 'Existing Product' already exists", exception.getMessage());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void updateProduct_ShouldUpdateAndReturnProduct() {
        // Arrange
        when(productRepository.findBySku("TST-12345678")).thenReturn(Optional.of(testProduct));
        when(productRepository.existsByName("Updated Product")).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Product result = productService.updateProduct("TST-12345678", "Updated Product", 20.0);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Product", result.getName());
        assertEquals(20.0, result.getPrice());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProduct_WithNonExistingSku_ShouldThrowException() {
        // Arrange
        when(productRepository.findBySku("NONEXISTENT")).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> productService.updateProduct("NONEXISTENT", "Updated Product", 20.0));
        assertEquals("Product with SKU 'NONEXISTENT' not found", exception.getMessage());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void enableProduct_ShouldEnableAndReturnProduct() {
        // Arrange
        Product disabledProduct = Product.builder()
                .sku("TST-12345678")
                .name("Test Product")
                .price(10.0)
                .stock(5)
                .enabled(false)
                .build();
        
        when(productRepository.findBySku("TST-12345678")).thenReturn(Optional.of(disabledProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product savedProduct = invocation.getArgument(0);
            assertTrue(savedProduct.isEnabled());
            return savedProduct;
        });

        // Act
        Product result = productService.enableProduct("TST-12345678");

        // Assert
        assertNotNull(result);
        assertTrue(result.isEnabled());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void disableProduct_ShouldDisableAndReturnProduct() {
        // Arrange
        when(productRepository.findBySku("TST-12345678")).thenReturn(Optional.of(testProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product savedProduct = invocation.getArgument(0);
            assertFalse(savedProduct.isEnabled());
            return savedProduct;
        });

        // Act
        Product result = productService.disableProduct("TST-12345678");

        // Assert
        assertNotNull(result);
        assertFalse(result.isEnabled());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void deleteProduct_ShouldDeleteProduct() {
        // Arrange
        Product deletableProduct = Product.builder()
                .sku("TST-12345678")
                .name("Test Product")
                .price(10.0)
                .stock(0)
                .enabled(false)
                .build();
        
        when(productRepository.findBySku("TST-12345678")).thenReturn(Optional.of(deletableProduct));

        // Act
        productService.deleteProduct("TST-12345678");

        // Assert
        verify(productRepository).delete(deletableProduct);
    }

    @Test
    void deleteProduct_WithEnabledProduct_ShouldThrowException() {
        // Arrange
        when(productRepository.findBySku("TST-12345678")).thenReturn(Optional.of(testProduct));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> productService.deleteProduct("TST-12345678"));
        assertEquals("Product cannot be deleted because it is enabled or has stock", exception.getMessage());
        verify(productRepository, never()).delete(any(Product.class));
    }

    @Test
    void getProductBySku_ShouldReturnProduct() {
        // Arrange
        when(productRepository.findBySku("TST-12345678")).thenReturn(Optional.of(testProduct));

        // Act
        Product result = productService.getProductBySku("TST-12345678");

        // Assert
        assertNotNull(result);
        assertEquals("TST-12345678", result.getSku());
        assertEquals("Test Product", result.getName());
    }

    @Test
    void getProductBySku_WithNonExistingSku_ShouldThrowException() {
        // Arrange
        when(productRepository.findBySku("NONEXISTENT")).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> productService.getProductBySku("NONEXISTENT"));
        assertEquals("Product with SKU 'NONEXISTENT' not found", exception.getMessage());
    }

    @Test
    void getAllProducts_ShouldReturnAllProducts() {
        // Arrange
        Product product1 = Product.builder().sku("SKU1").name("Product 1").build();
        Product product2 = Product.builder().sku("SKU2").name("Product 2").build();
        List<Product> products = Arrays.asList(product1, product2);
        
        when(productRepository.findAll()).thenReturn(products);

        // Act
        List<Product> result = productService.getAllProducts();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Product 1", result.get(0).getName());
        assertEquals("Product 2", result.get(1).getName());
    }
}