package com.example.pedidos.domain.events;

import com.example.pedidos.domain.model.Order;
import com.example.pedidos.domain.model.OrderStates;

public class OrderStateChangedEvent {
    private final Order order;
    private final OrderStates previousState;
    private final OrderStates newState;

    public OrderStateChangedEvent(Order order, OrderStates previousState, OrderStates newState) {
        this.order = order;
        this.previousState = previousState;
        this.newState = newState;
    }

    public Order getOrder() {
        return order;
    }

    public OrderStates getPreviousState() {
        return previousState;
    }

    public OrderStates getNewState() {
        return newState;
    }
}
