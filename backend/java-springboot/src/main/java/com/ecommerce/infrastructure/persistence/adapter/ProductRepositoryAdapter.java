package com.ecommerce.infrastructure.persistence.adapter;

import com.ecommerce.domain.model.Product;
import com.ecommerce.domain.port.ProductRepository;
import com.ecommerce.infrastructure.persistence.entity.ProductEntity;
import com.ecommerce.infrastructure.persistence.repository.JpaProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter implementation of the ProductRepository port.
 * This class adapts the JpaProductRepository to the ProductRepository interface.
 */
@Component
public class ProductRepositoryAdapter implements ProductRepository {

    private final JpaProductRepository jpaProductRepository;

    public ProductRepositoryAdapter(JpaProductRepository jpaProductRepository) {
        this.jpaProductRepository = jpaProductRepository;
    }

    /**
     * Maps a domain Product to a persistence ProductEntity.
     * @param product the domain product to map
     * @return the mapped product entity
     */
    private ProductEntity mapToEntity(Product product) {
        return ProductEntity.builder()
                .sku(product.getSku())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .enabled(product.isEnabled())
                .build();
    }

    /**
     * Maps a persistence ProductEntity to a domain Product.
     * @param entity the product entity to map
     * @return the mapped domain product
     */
    private Product mapToDomain(ProductEntity entity) {
        return Product.builder()
                .sku(entity.getSku())
                .name(entity.getName())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .enabled(entity.isEnabled())
                .build();
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = mapToEntity(product);
        ProductEntity savedEntity = jpaProductRepository.save(entity);
        return mapToDomain(savedEntity);
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        return jpaProductRepository.findById(sku)
                .map(this::mapToDomain);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return jpaProductRepository.findByName(name)
                .map(this::mapToDomain);
    }

    @Override
    public List<Product> findAll() {
        return jpaProductRepository.findAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Product product) {
        jpaProductRepository.deleteById(product.getSku());
    }

    @Override
    public boolean existsBySku(String sku) {
        return jpaProductRepository.existsById(sku);
    }

    @Override
    public boolean existsByName(String name) {
        return jpaProductRepository.existsByName(name);
    }
}