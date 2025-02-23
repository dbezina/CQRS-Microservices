package com.bezina.ProductService.command.rest;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.checkerframework.common.value.qual.MinLen;

import java.math.BigDecimal;

public class CreateProductRestModel {
    @NotBlank(message = "title shouldn't be empty")
    private String title;
    @Min(value=1,message = "Price couldn't be lower than 1")
    private BigDecimal price;
    @Min(value=1,message = "Quantity can't be lower than 1")
    @Max(value = 10,message = "Quantity can't be more than 10")
    private Integer quantity;

    public CreateProductRestModel(String title, BigDecimal price, Integer quantity) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }
}
