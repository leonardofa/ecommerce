package com.ecommerce.infrastructure.rest.dto;

import com.ecommerce.domain.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for product responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    
    private String sku;
    private String name;
    private double price;
    private int stock;
    private boolean enabled;
    
    /**
     * Creates a ProductResponse from a Product domain entity.
     * @param product the product domain entity
     * @return the product response DTO
     */
    public static ProductResponse fromDomain(Product product) {
        return ProductResponse.builder()
                .sku(product.getSku())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .enabled(product.isEnabled())
                .build();
    }
}