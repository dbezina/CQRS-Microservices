package com.bezina.ordersService.command.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class OrderDTO {

    @NotBlank(message = "productId cannot be empty")
    private String productId;
    @Min(value = 1, message = "quantity should be > 0")
    private Integer quantity;
    private String addressId;
    public OrderDTO(){}
    public OrderDTO(String productId, Integer quantity, String addressId) {
        this.productId = productId;
        this.quantity = quantity;
        this.addressId = addressId;
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

}
