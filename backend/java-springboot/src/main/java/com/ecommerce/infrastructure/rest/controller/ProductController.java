package com.ecommerce.infrastructure.rest.controller;

import com.ecommerce.domain.model.Product;
import com.ecommerce.domain.port.ProductService;
import com.ecommerce.infrastructure.rest.dto.ProductRequest;
import com.ecommerce.infrastructure.rest.dto.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for product management.
 */
@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Product management API")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Create a new product",
        description = "Creates a new product with the provided details. Requires ADMIN role.",
        security = @SecurityRequirement(name = "basicAuth"),
        responses = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "Forbidden - requires ADMIN role")
        }
    )
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        Product product = productService.createProduct(
                request.getName(),
                request.getPrice(),
                request.getInitialStock() != null ? request.getInitialStock() : 0
        );
        return new ResponseEntity<>(ProductResponse.fromDomain(product), HttpStatus.CREATED);
    }

    @PutMapping("/{sku}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Update a product",
        description = "Updates an existing product with the provided details. Requires ADMIN role.",
        security = @SecurityRequirement(name = "basicAuth"),
        responses = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden - requires ADMIN role")
        }
    )
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable String sku,
            @Valid @RequestBody ProductRequest request) {
        Product product = productService.updateProduct(sku, request.getName(), request.getPrice());
        return ResponseEntity.ok(ProductResponse.fromDomain(product));
    }

    @PatchMapping("/{sku}/enable")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Enable a product",
        description = "Enables a product with the specified SKU. Requires ADMIN role.",
        security = @SecurityRequirement(name = "basicAuth"),
        responses = {
            @ApiResponse(responseCode = "200", description = "Product enabled successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden - requires ADMIN role")
        }
    )
    public ResponseEntity<ProductResponse> enableProduct(@PathVariable String sku) {
        Product product = productService.enableProduct(sku);
        return ResponseEntity.ok(ProductResponse.fromDomain(product));
    }

    @PatchMapping("/{sku}/disable")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Disable a product",
        description = "Disables a product with the specified SKU. Requires ADMIN role.",
        security = @SecurityRequirement(name = "basicAuth"),
        responses = {
            @ApiResponse(responseCode = "200", description = "Product disabled successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden - requires ADMIN role")
        }
    )
    public ResponseEntity<ProductResponse> disableProduct(@PathVariable String sku) {
        Product product = productService.disableProduct(sku);
        return ResponseEntity.ok(ProductResponse.fromDomain(product));
    }

    @DeleteMapping("/{sku}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Delete a product",
        description = "Deletes a product with the specified SKU. Requires ADMIN role.",
        security = @SecurityRequirement(name = "basicAuth"),
        responses = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Product cannot be deleted"),
            @ApiResponse(responseCode = "403", description = "Forbidden - requires ADMIN role")
        }
    )
    public ResponseEntity<Void> deleteProduct(@PathVariable String sku) {
        productService.deleteProduct(sku);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{sku}")
    @Operation(
        summary = "Get a product by SKU",
        description = "Retrieves a product with the specified SKU.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
        }
    )
    public ResponseEntity<ProductResponse> getProduct(@PathVariable String sku) {
        Product product = productService.getProductBySku(sku);
        return ResponseEntity.ok(ProductResponse.fromDomain(product));
    }

    @GetMapping
    @Operation(
        summary = "Get all products",
        description = "Retrieves all products.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
        }
    )
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts().stream()
                .map(ProductResponse::fromDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }
}