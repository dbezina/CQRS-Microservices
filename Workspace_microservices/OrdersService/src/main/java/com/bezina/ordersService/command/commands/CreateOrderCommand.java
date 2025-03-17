package com.bezina.ordersService.command.commands;

import com.bezina.ordersService.core.entity.enums.OrderStatus;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Objects;

public class CreateOrderCommand {
    @TargetAggregateIdentifier
    public final String orderId;
    private final String userId;
    private final String productId;
    private final int quantity;
    private final String addressId;
    private final OrderStatus orderStatus;

    // Приватный конструктор (теперь используется билдер)
    private CreateOrderCommand(Builder builder) {
        this.orderId = builder.orderId;
        this.productId = builder.productId;
        this.userId = builder.userId;
        this.quantity = builder.quantity;
        this.addressId = builder.addressId;
        this.orderStatus = builder.orderStatus;
    }

    // Добавляем метод `builder()`
    public static Builder builder() {
        return new Builder();
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

    public int getQuantity() {
        return quantity;
    }

    public String getAddressId() {
        return addressId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    // equals(), hashCode(), toString()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateOrderCommand that = (CreateOrderCommand) o;
        return  Objects.equals(orderId, that.orderId)&&
                Objects.equals(productId, that.productId) &&
                Objects.equals(quantity, that.quantity)&&
                Objects.equals(addressId, that.addressId) &&
                Objects.equals(orderStatus, that.orderStatus)
                ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId, quantity
                ,addressId,orderStatus);
    }

    @Override
    public String toString() {
        return "CreateOrderCommand{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                ", addressId='" + addressId + '\'' +
                ", orderStatus=" + orderStatus +
                '}';
    }

    // Новый билдер
    public static class Builder {
        public  String orderId;
        private  String userId;
        private  String productId;
        private  int quantity;
        private  String addressId;
        private  OrderStatus orderStatus;
        public Builder orderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder productId(String productId) {
            this.productId = productId;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }
        public Builder addressId(String addressId) {
            this.addressId = addressId;
            return this;
        }

        public Builder orderStatus(OrderStatus orderStatus) {
            this.orderStatus = OrderStatus.CREATED;
            return this;
        }
        public CreateOrderCommand build() {
            return new CreateOrderCommand(this);
        }
    }

}
