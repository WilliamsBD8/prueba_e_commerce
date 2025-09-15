package com.example.pedidos.domain.ports;

import java.util.Map;

import com.example.pedidos.domain.model.Product;

public interface InventoryPort {
    Product getProductById(Long productId);
    Product saveProduct(Product product);
    void decreaseStock(Long productId, int quantity);
    void releaseStock(Long productId, int quantity);   // Liberar productos
    void reserveStock(Map<Long, Integer> productQuantities); // Reservar productos
}
