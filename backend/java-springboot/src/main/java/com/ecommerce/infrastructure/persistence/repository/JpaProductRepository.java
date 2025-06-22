package com.ecommerce.infrastructure.persistence.repository;

import com.ecommerce.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA repository for ProductEntity.
 * This interface extends Spring Data JPA's JpaRepository to provide CRUD operations for ProductEntity.
 */
@Repository
public interface JpaProductRepository extends JpaRepository<ProductEntity, String> {
    
    /**
     * Finds a product entity by its name.
     * @param name the name to search for
     * @return an Optional containing the product entity if found, or empty if not found
     */
    Optional<ProductEntity> findByName(String name);
    
    /**
     * Checks if a product entity with the given name exists.
     * @param name the name to check
     * @return true if a product entity with the given name exists, false otherwise
     */
    boolean existsByName(String name);
}