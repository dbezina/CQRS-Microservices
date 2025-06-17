package com.bezina.ordersService.core.entity;

import com.bezina.ordersService.core.entity.enums.OrderStatus;

public class OrderSummary {
    private final String orderId;
    private final OrderStatus orderStatus;
    private final String message;

    public OrderSummary(String orderId, OrderStatus orderStatus, String message) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.message = message;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getMessage() {
        return message;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
