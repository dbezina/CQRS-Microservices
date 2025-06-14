package com.bezina.ordersService.core.event;

import com.bezina.ordersService.core.entity.enums.OrderStatus;

public class OrderRejectedEvent {
    private final String orderId;
    private final String reason;
    private final OrderStatus orderStatus = OrderStatus.REJECTED;

    public OrderRejectedEvent(String orderId, String reason) {
        this.orderId = orderId;
        this.reason = reason;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getReason() {
        return reason;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
