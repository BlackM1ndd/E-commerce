package com.example.orders.service;

import com.example.orders.exception.OrderNotFoundException;
import com.example.orders.model.Order;
import com.example.orders.model.OrderEvent;
import com.example.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Autowired
    public OrderService(OrderRepository orderRepository, KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Order createOrder(Order order) {
        // Подсчёт итоговой суммы и установка статуса заказа
        order.calculateTotal();
        order.setStatus("CREATED");
        Order savedOrder = orderRepository.save(order);
        // Отправка события в Kafka
        OrderEvent event = new OrderEvent(savedOrder.getId(), "ORDER_CREATED");
        kafkaTemplate.send("order-events", event);
        return savedOrder;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id " + id));
    }

}
