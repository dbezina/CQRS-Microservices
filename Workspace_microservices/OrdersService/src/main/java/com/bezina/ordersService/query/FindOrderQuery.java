package com.bezina.ordersService.query;

public class FindOrderQuery {
    private final String orderId;

    public FindOrderQuery(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
