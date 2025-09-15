package com.example.pedidos.domain.service.product;

import org.springframework.stereotype.Service;

import com.example.pedidos.domain.model.Product;
import com.example.pedidos.domain.ports.ProductRepositoryPort;

@Service
public class CreateUseCase {
    private final ProductRepositoryPort productRepository;

    public CreateUseCase(ProductRepositoryPort productRepository) {
        this.productRepository = productRepository;
    }
    
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
}
