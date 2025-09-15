package com.example.pedidos.domain.service.product;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.pedidos.domain.model.Product;
import com.example.pedidos.domain.ports.ProductRepositoryPort;

@Service
public class GetAllUseCase {
    private final ProductRepositoryPort productRepository;

    public GetAllUseCase(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
