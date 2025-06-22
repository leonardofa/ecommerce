package com.ecommerce.domain.port;

import com.ecommerce.domain.model.Product;
import java.util.List;
import java.util.Optional;

/**
 * Repository port for Product entity.
 * This is an output port that will be implemented by an adapter in the infrastructure layer.
 */
public interface ProductRepository {
    
    /**
     * Saves a product to the repository.
     * @param product the product to save
     * @return the saved product
     */
    Product save(Product product);
    
    /**
     * Finds a product by its SKU.
     * @param sku the SKU to search for
     * @return an Optional containing the product if found, or empty if not found
     */
    Optional<Product> findBySku(String sku);
    
    /**
     * Finds a product by its name.
     * @param name the name to search for
     * @return an Optional containing the product if found, or empty if not found
     */
    Optional<Product> findByName(String name);
    
    /**
     * Retrieves all products from the repository.
     * @return a list of all products
     */
    List<Product> findAll();
    
    /**
     * Deletes a product from the repository.
     * @param product the product to delete
     */
    void delete(Product product);
    
    /**
     * Checks if a product with the given SKU exists.
     * @param sku the SKU to check
     * @return true if a product with the given SKU exists, false otherwise
     */
    boolean existsBySku(String sku);
    
    /**
     * Checks if a product with the given name exists.
     * @param name the name to check
     * @return true if a product with the given name exists, false otherwise
     */
    boolean existsByName(String name);
}