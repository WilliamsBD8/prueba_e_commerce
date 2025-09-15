package com.example.pedidos.domain.ports;

import java.util.List;
import java.util.Optional;

import com.example.pedidos.domain.model.Product;

public interface ProductRepositoryPort {
    Product save(Product product);
    Optional<Product> findById(Long id);
    List<Product> findAll();
    void deleteById(Long id);
}
