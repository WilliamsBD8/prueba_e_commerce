package com.example.pedidos.domain.model;

import java.math.BigDecimal;
// import java.time.Instant;

public class Product {
    private Long id;
    private String name;
    private BigDecimal price;
    private int stock;
    private int reserved;
    private String state;

    // Constructor vacío y con parámetros
    public Product() {}
    public Product(Long id, String name, BigDecimal price, int stock, String state) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.state = state;
        this.stock = stock;
        this.reserved = 0;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public int getStock() { return stock; }
    public int getReserved() { return reserved; }
    public void setStock(int stock) { this.stock = stock; }
    public void setReserved(int reserved) { this.reserved = reserved; }

    // ==== LÓGICA DE DOMINIO ====
    public boolean reserve(int quantity) {
        if (quantity > stock - reserved) {
            return false; // No hay suficiente inventario disponible
        }
        reserved += quantity;
        return true;
    }

    public void release(int quantity) {
        reserved -= quantity;
        if (reserved < 0) reserved = 0;
    }

    public void deductStock(int quantity) {
        if (quantity > stock) throw new IllegalStateException("No hay suficiente stock");
        stock -= quantity;
        reserved -= quantity;
        if (reserved < 0) reserved = 0;
    }

}
