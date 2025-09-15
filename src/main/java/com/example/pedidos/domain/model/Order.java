package com.example.pedidos.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long id;
    private Long customerId;
    private BigDecimal total;
    private OrderStates state;
    private List<OrderItem> items = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public OrderStates getState() {
		return state;
	}
	public void setState(OrderStates state) {
		this.state = state;
	}

	public List<OrderItem> getItems() {
		return items;
	}
	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

    // ==== CONSTRUCTORES ====
    protected Order() {} // JPA

    public Order(Long customerId, List<OrderItem> items) {
        this.customerId = customerId;
        this.items = items;
        this.state = OrderStates.PENDING;
        this.total = BigDecimal.ZERO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        calculateTotal();
    }

    // ==== MÉTODOS DE DOMINIO ====
    public void calculateTotal() {
        this.total = items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calcularTotal() {
        return items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void actualizarTotal() {
        this.total = calcularTotal();
        this.updatedAt = LocalDateTime.now();
    }

	public void nextState() {
		switch (state) {
			case PENDING -> state = OrderStates.CONFIRMED;
			case CONFIRMED -> state = OrderStates.SHIPPED;
			case SHIPPED -> state = OrderStates.DELIVERED;
			case DELIVERED, CANCELLED, FAILED -> 
				throw new IllegalStateException("No se puede avanzar desde el estado: " + state);
			default -> throw new IllegalStateException("Estado desconocido: " + state);
		}
	}

	public void previousState() {
        switch (state) {
            case CONFIRMED -> state = OrderStates.PENDING;
            case SHIPPED -> state = OrderStates.CONFIRMED;
            case DELIVERED -> state = OrderStates.SHIPPED;
            case CANCELLED, FAILED ->
                throw new IllegalStateException("No se puede retroceder desde el estado: " + state);
            default -> throw new IllegalStateException("Estado desconocido: " + state);
        }
    }

}
