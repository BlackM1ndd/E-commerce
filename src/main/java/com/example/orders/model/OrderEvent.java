package com.example.orders.model;

public class OrderEvent {
    private Long orderId;
    private String eventType;

    public OrderEvent() {}

    public OrderEvent(Long orderId, String eventType) {
        this.orderId = orderId;
        this.eventType = eventType;
    }

    // Геттеры и сеттеры

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
}
