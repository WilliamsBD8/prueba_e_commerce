package com.example.pedidos.domain.ports;

import java.util.List;
import java.util.Optional;

import com.example.pedidos.domain.model.Order;

public interface OrderRepositoryPort {
    Order save(Order order);
    Optional<Order> findById(Long id);
    List<Order> findAll();
    void deleteById(Long id);
}
