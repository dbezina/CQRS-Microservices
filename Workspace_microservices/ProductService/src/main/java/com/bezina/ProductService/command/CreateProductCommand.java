package com.bezina.ProductService.command;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.util.Objects;

public class CreateProductCommand {
    @TargetAggregateIdentifier
    private final String productId;
    private final String title;
    private final BigDecimal price;
    private final Integer quantity;

        // Приватный конструктор (теперь используется билдер)
        private CreateProductCommand(Builder builder) {
            this.productId = builder.productId;
            this.title = builder.title;
            this.price = builder.price;
            this.quantity = builder.quantity;
        }

        // Добавляем метод `builder()`
        public static Builder builder() {
            return new Builder();
        }

        // Геттеры
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

        // equals(), hashCode(), toString()
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CreateProductCommand that = (CreateProductCommand) o;
            return Objects.equals(productId, that.productId) &&
                    Objects.equals(title, that.title) &&
                    Objects.equals(price, that.price) &&
                    Objects.equals(quantity, that.quantity);
        }

        @Override
        public int hashCode() {
            return Objects.hash(productId, title, price, quantity);
        }

        @Override
        public String toString() {
            return "CreateProductCommand{" +
                    "productId='" + productId + '\'' +
                    ", title='" + title + '\'' +
                    ", price=" + price +
                    ", quantity=" + quantity +
                    '}';
        }

        // Новый билдер
        public static class Builder {
            private String productId;
            private String title;
            private BigDecimal price;
            private Integer quantity;

            public Builder productId(String productId) {
                this.productId = productId;
                return this;
            }

            public Builder title(String title) {
                this.title = title;
                return this;
            }

            public Builder price(BigDecimal price) {
                this.price = price;
                return this;
            }

            public Builder quantity(Integer quantity) {
                this.quantity = quantity;
                return this;
            }

            public CreateProductCommand build() {
                return new CreateProductCommand(this);
            }
        }

}
