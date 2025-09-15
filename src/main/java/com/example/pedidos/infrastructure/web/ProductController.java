package com.example.pedidos.infrastructure.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pedidos.domain.model.Product;
import com.example.pedidos.domain.service.product.CreateUseCase;
import com.example.pedidos.domain.service.product.DeleteUseCase;
import com.example.pedidos.domain.service.product.FindUseCase;
import com.example.pedidos.domain.service.product.GetAllUseCase;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final GetAllUseCase getAllUseCase;
    private final CreateUseCase createUseCase;
    private final FindUseCase findUseCase;
    private final DeleteUseCase deleteUseCase;

    public ProductController(GetAllUseCase getAllUseCase, CreateUseCase createUseCase, FindUseCase findUseCase,
            DeleteUseCase deleteUseCase) {
        this.getAllUseCase = getAllUseCase;
        this.createUseCase = createUseCase;
        this.findUseCase = findUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(createUseCase.createProduct(product));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(getAllUseCase.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return findUseCase.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        deleteUseCase.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
