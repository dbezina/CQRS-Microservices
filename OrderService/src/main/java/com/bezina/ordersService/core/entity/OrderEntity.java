package com.bezina.ordersService.core.entity;

import com.bezina.ordersService.core.entity.enums.OrderStatus;
import jakarta.persistence.*;
import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.io.Serializable;

@Entity
@Table(name = "orders")
public class OrderEntity implements Serializable {
    private static final long serialVersionUID = 406796610575695471L;
    @Id
    @Column(unique = true)
    private String orderId;
    private String userId;
    private String productId;
    private Integer quantity;
    private String addressId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Version
    private Long version = 0L;
    public OrderEntity(){}



    public OrderEntity(String orderId, String userId, String productId,
                       Integer quantity, String addressId, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.addressId = addressId;
        this.orderStatus = orderStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                ", addressId='" + addressId + '\'' +
                ", orderStatus=" + orderStatus +
                ", version=" + version +
                '}';
    }
}
