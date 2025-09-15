package com.example.pedidos.infrastructure.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.pedidos.domain.events.OrderStateChangedEvent;

@Component
public class OrderEventListener {
    @Async
    @EventListener
    public void handleOrderStateChangedEvent(OrderStateChangedEvent event) {
        // Aquí se podría enviar un email, notificación push o mensaje a otra cola
        System.out.println("Pedido " + event.getOrder().getId() +
                " cambió de " + event.getPreviousState() + " a " + event.getNewState());
        
        // Ejemplo: enviar a cola de procesamiento diferido
        // queueService.enqueue(event);
    }
}
