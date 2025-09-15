package com.example.pedidos.infrastructure.entity;

import java.math.BigDecimal;
import java.time.Instant;

import com.example.pedidos.domain.model.Product;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal price;
    private BigDecimal quantity;
    private int stock;
    private int reserved;
    private String state;
    private Instant createdAt;
    private Instant updatedAt;

	public ProductEntity() {}

    public ProductEntity(String name, BigDecimal price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.reserved = 0;
    }

	public static ProductEntity fromDomain(Product product) {
        ProductEntity entity = new ProductEntity(product.getName(), product.getPrice(), product.getStock());
        entity.setId(product.getId());
        entity.setReserved(product.getReserved());
        return entity;
    }

	public Product toDomain() {
        Product product = new Product(id, name, price, stock, state);
        product.reserve(reserved); // reflejar reservas actuales
        return product;
    }

	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getReserved() {
		return reserved;
	}
	public void setReserved(int reserved) {
		this.reserved = reserved;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
    public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Instant getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
	public Instant getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

    
}
