package com.bezina.ProductService.core.events;

import com.bezina.ProductService.command.CreateProductCommand;

import java.math.BigDecimal;

public class ProductCreatedEvent {
    private final String productId;
    private final String title;
    private final BigDecimal price;
    private final Integer quantity;

    public ProductCreatedEvent(String productId, String title, BigDecimal price, Integer quantity) {
        this.productId = productId;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }

    // Добавляем билдер
    public static ProductCreatedEvent from(CreateProductCommand command) {
        return new ProductCreatedEvent(
                command.getProductId(),
                command.getTitle(),
                command.getPrice(),
                command.getQuantity()
        );
    }
    public String getProductId() {
        return productId;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
