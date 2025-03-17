package com.bezina.ordersService.core.event;

import com.bezina.ordersService.command.commands.CreateOrderCommand;
import com.bezina.ordersService.core.entity.enums.OrderStatus;

public class OrderCreatedEvent {

    private  String orderId;
    private  String userId;
    private  String productId;
    private  Integer quantity;
    private  String addressId;
    private  OrderStatus orderStatus;
    public OrderCreatedEvent(){}
    public OrderCreatedEvent(String orderId, String userId, String productId, Integer quantity, String addressId, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.addressId = addressId;
        this.orderStatus = orderStatus;
    }

    public static OrderCreatedEvent from(CreateOrderCommand orderCommand){
        return new OrderCreatedEvent(
                orderCommand.getOrderId()
                ,orderCommand.getUserId()
                ,orderCommand.getProductId()
                ,orderCommand.getQuantity()
                ,orderCommand.getAddressId()
              //  ,orderCommand.getOrderStatus()
               , OrderStatus.CREATED
        );
    }

    public String getOrderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }

    public String getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getAddressId() {
        return addressId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

}
