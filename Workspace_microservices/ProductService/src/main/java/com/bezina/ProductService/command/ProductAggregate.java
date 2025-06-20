package com.bezina.ProductService.command;

import com.bezina.ProductService.core.events.ProductCreatedEvent;
import com.bezina.core.commands.CancelProductReservationCommand;
import com.bezina.core.commands.ReserveProductCommand;
import com.bezina.core.events.ProductReservationCancelledEvent;
import com.bezina.core.events.ProductReservedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Aggregate(snapshotTriggerDefinition = "productSnapshotTriggerDefinition")
public class ProductAggregate {

    @AggregateIdentifier
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;

    public ProductAggregate() {
    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand) {
        //validate Create Product Command
        if (createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price can't be less or equal to zero");
        }
        if ((createProductCommand.getTitle() == null) || (createProductCommand.getTitle().isBlank())) {
            throw new IllegalArgumentException("Title can't be empty");

        }
        ProductCreatedEvent productCreatedEvent = ProductCreatedEvent.from(createProductCommand);
        AggregateLifecycle.apply(productCreatedEvent);

    }

    @CommandHandler
    public void handle(ReserveProductCommand reserveProductCommand) {
        System.out.println("   @CommandHandler\n" +
                "    public void handle(ReserveProductCommand reserveProductCommand)");

        if (quantity < reserveProductCommand.getQuantity()) {
            throw new IllegalArgumentException("Insufficient number of items in stock");
        }
        ProductReservedEvent productReservedEvent = new ProductReservedEvent.Builder()
                .orderId(reserveProductCommand.getOrderId())
                .productId(reserveProductCommand.getProductId())
                .quantity(reserveProductCommand.getQuantity())
                .userId(reserveProductCommand.getUserId())
                .build();
        AggregateLifecycle.apply(productReservedEvent);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        this.productId = productCreatedEvent.getProductId();
        this.title = productCreatedEvent.getTitle();
        this.price = productCreatedEvent.getPrice();
        this.quantity = productCreatedEvent.getQuantity();
    }

    @CommandHandler
    public void handle(CancelProductReservationCommand cancelProductReservationCommand) {
        ProductReservationCancelledEvent productReservationCancelledEvent = new ProductReservationCancelledEvent.Builder()
                .orderId(cancelProductReservationCommand.getOrderId())
                .productId(cancelProductReservationCommand.getProductId())
                .quantity(cancelProductReservationCommand.getQuantity())
                .userId(cancelProductReservationCommand.getUserId())
                .reason(cancelProductReservationCommand.getReason())
                .build();
        AggregateLifecycle.apply(productReservationCancelledEvent);
    }

    @EventSourcingHandler
    public void on(ProductReservedEvent productReservedEvent) {
        this.quantity -= productReservedEvent.getQuantity();
    }

    @EventSourcingHandler
    public void on(ProductReservationCancelledEvent productReservationCancelledEvent) {
        this.quantity += productReservationCancelledEvent.getQuantity();
    }
}
