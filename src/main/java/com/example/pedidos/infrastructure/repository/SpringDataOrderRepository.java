package com.example.pedidos.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// import com.example.pedidos.domain.model.Order;
import com.example.pedidos.infrastructure.entity.OrderEntity;

@Repository
public interface SpringDataOrderRepository extends JpaRepository<OrderEntity, Long> {
}
