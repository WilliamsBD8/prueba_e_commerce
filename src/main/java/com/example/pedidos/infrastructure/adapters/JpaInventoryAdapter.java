package com.example.pedidos.infrastructure.adapters;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.pedidos.domain.model.Product;
import com.example.pedidos.domain.ports.InventoryPort;
import com.example.pedidos.infrastructure.entity.ProductEntity;
import com.example.pedidos.infrastructure.repository.SpringDataProductRepository;



@Component
public class JpaInventoryAdapter implements InventoryPort{
    private final SpringDataProductRepository repository;

    public JpaInventoryAdapter(SpringDataProductRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductById(Long productId) {
        return repository.findById(productId)
                         .map(ProductEntity::toDomain)
                         .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
    }

    @Override
    @Transactional
    public Product saveProduct(Product product) {
        ProductEntity entity = ProductEntity.fromDomain(product);
        ProductEntity saved = repository.save(entity);
        return saved.toDomain();
    }

    @Override
    public void decreaseStock(Long productId, int quantity) {
        var product = repository.findById(productId).orElseThrow();
        product.setStock(product.getStock() - quantity);
        product.setReserved(product.getReserved() - quantity);
        repository.save(product);
    }

    @Override
    public void releaseStock(Long id, int quantity) {
        var product = repository.findById(id).orElseThrow();
        product.setReserved(product.getReserved() + quantity);
        repository.save(product);
        // for (Map.Entry<Long, Integer> entry : items.entrySet()) {
        //     var product = repository.findById(entry.getKey()).orElseThrow();
        //     product.setReserved(product.getReserved() + entry.getValue());
        //     repository.save(product);
        // }
    }

    @Override
    public void reserveStock(Map<Long, Integer> items) {
        for (Map.Entry<Long, Integer> entry : items.entrySet()) {
            var product = repository.findById(entry.getKey()).orElseThrow();
            int newStock = product.getStock() - entry.getValue();
            if (newStock < 0) {
                throw new RuntimeException("Stock insuficiente para el producto " + entry.getKey());
            }
            product.setStock(newStock);
            repository.save(product);
        }
    }
}
