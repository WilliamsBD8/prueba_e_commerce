package com.example.pedidos.infrastructure.adapters;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.pedidos.domain.events.OrderStateChangedEvent;
import com.example.pedidos.domain.ports.OrderEventPublisherPort;

@Component
@Primary
public class SpringOrderEventPublisherAdapter implements OrderEventPublisherPort{
    private final ApplicationEventPublisher publisher;

    public SpringOrderEventPublisherAdapter(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    @Async
    public void publishOrderStateChangedEvent(OrderStateChangedEvent event) {
        publisher.publishEvent(event);
    }
}
