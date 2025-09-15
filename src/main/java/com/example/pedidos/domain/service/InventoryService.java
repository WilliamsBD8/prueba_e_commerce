package com.example.pedidos.domain.service;

import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.pedidos.domain.model.Product;
import com.example.pedidos.domain.ports.InventoryPort;

@Service
public class InventoryService {
    private final InventoryPort inventoryPort;

    public InventoryService(InventoryPort inventoryPort) {
        this.inventoryPort = inventoryPort;
    }

    @Cacheable(value = "products", key = "#productId")
    public Product getProduct(Long productId) {
        return inventoryPort.getProductById(productId);
    }

    @CacheEvict(value = "products", key = "#product.id")
    public Product updateProductStock(Product product) {
        return inventoryPort.saveProduct(product);
    }

    public void reserveStock(Map<Long, Integer> items) {
        inventoryPort.reserveStock(items);
    }

    public void releaseStock(Long id, int quantity) {
        inventoryPort.releaseStock(id, quantity);
    }

    public void decreaseStock(Long productId, int quantity) {
        inventoryPort.decreaseStock(productId, quantity);
    }


}
