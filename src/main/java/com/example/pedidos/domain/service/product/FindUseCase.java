package com.example.pedidos.domain.service.product;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.pedidos.domain.model.Product;
import com.example.pedidos.domain.ports.ProductRepositoryPort;

@Service
public class FindUseCase {
    private final ProductRepositoryPort productRepository;

    public FindUseCase(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
}
