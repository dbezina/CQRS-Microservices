package com.bezina.ordersService.command.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class ApproveOrderCommand {
    @TargetAggregateIdentifier
    private final String orderId;

    public ApproveOrderCommand(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
