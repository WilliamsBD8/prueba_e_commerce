package com.example.pedidos.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// import com.example.pedidos.domain.model.Product;
import com.example.pedidos.infrastructure.entity.ProductEntity;

@Repository
public interface SpringDataProductRepository extends JpaRepository<ProductEntity, Long>{

}
