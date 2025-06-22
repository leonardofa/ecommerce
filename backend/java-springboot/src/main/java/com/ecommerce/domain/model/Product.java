package com.ecommerce.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Product domain entity representing a product in the e-commerce system.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    private String sku;
    private String name;
    private double price;
    private int stock;
    private boolean enabled;
    
    /**
     * Enables the product if it's currently disabled.
     * @return true if the product was enabled, false if it was already enabled
     */
    public boolean enable() {
        if (!this.enabled) {
            this.enabled = true;
            return true;
        }
        return false;
    }
    
    /**
     * Disables the product if it's currently enabled.
     * @return true if the product was disabled, false if it was already disabled
     */
    public boolean disable() {
        if (this.enabled) {
            this.enabled = false;
            return true;
        }
        return false;
    }
    
    /**
     * Checks if the product can be deleted.
     * A product can be deleted if it's not enabled and has no stock.
     * @return true if the product can be deleted, false otherwise
     */
    public boolean canBeDeleted() {
        return !this.enabled && this.stock == 0;
    }
}