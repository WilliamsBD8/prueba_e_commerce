package com.example.pedidos.domain.ports;

import com.example.pedidos.domain.events.OrderStateChangedEvent;

public interface OrderEventPublisherPort {
    void publishOrderStateChangedEvent(OrderStateChangedEvent event);
}
