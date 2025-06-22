package com.ecommerce.domain.port;

import com.ecommerce.domain.model.Product;
import java.util.List;

/**
 * Service port for Product entity.
 * This is an input port in the hexagonal architecture that will be implemented
 * by a service in the application layer.
 */
public interface ProductService {
    
    /**
     * Creates a new product.
     * @param name the name of the product
     * @param price the price of the product
     * @param initialStock the initial stock of the product
     * @return the created product
     * @throws IllegalArgumentException if a product with the same name already exists
     */
    Product createProduct(String name, double price, int initialStock);
    
    /**
     * Updates an existing product.
     * @param sku the SKU of the product to update
     * @param name the new name of the product
     * @param price the new price of the product
     * @return the updated product
     * @throws IllegalArgumentException if the product does not exist or if the new name is already taken
     */
    Product updateProduct(String sku, String name, double price);
    
    /**
     * Enables a product.
     * @param sku the SKU of the product to enable
     * @return the enabled product
     * @throws IllegalArgumentException if the product does not exist
     */
    Product enableProduct(String sku);
    
    /**
     * Disables a product.
     * @param sku the SKU of the product to disable
     * @return the disabled product
     * @throws IllegalArgumentException if the product does not exist
     */
    Product disableProduct(String sku);
    
    /**
     * Deletes a product.
     * @param sku the SKU of the product to delete
     * @throws IllegalArgumentException if the product does not exist or cannot be deleted
     */
    void deleteProduct(String sku);
    
    /**
     * Retrieves a product by its SKU.
     * @param sku the SKU of the product to retrieve
     * @return the product
     * @throws IllegalArgumentException if the product does not exist
     */
    Product getProductBySku(String sku);
    
    /**
     * Retrieves all products.
     * @return a list of all products
     */
    List<Product> getAllProducts();
}