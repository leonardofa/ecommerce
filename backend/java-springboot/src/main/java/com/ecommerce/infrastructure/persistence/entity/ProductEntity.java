package com.ecommerce.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA entity for Product.
 * This is the persistence model for the Product domain entity.
 */
@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    
    @Id
    @Column(name = "sku", nullable = false, unique = true)
    private String sku;
    
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    
    @Column(name = "price", nullable = false)
    private double price;
    
    @Column(name = "stock", nullable = false)
    private int stock;
    
    @Column(name = "enabled", nullable = false)
    private boolean enabled;
}