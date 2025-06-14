package com.bezina.ordersService.command;

import com.bezina.ordersService.command.commands.ApproveOrderCommand;
import com.bezina.ordersService.command.commands.CreateOrderCommand;
import com.bezina.ordersService.command.commands.RejectOrderCommand;
import com.bezina.ordersService.core.entity.enums.OrderStatus;
import com.bezina.ordersService.core.event.OrderApprovedEvent;
import com.bezina.ordersService.core.event.OrderCreatedEvent;
import com.bezina.ordersService.core.event.OrderRejectedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

@Aggregate
public class OrderAggregate {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderAggregate.class);

    @AggregateIdentifier
    private String orderId;
    private String productId;
    private String userId;
    private int quantity;
    private String addressId;
    private OrderStatus orderStatus;
    public OrderAggregate() {
    }
    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand)  {
        LOGGER.info(" @CommandHandler OrderAggregate");
        //validate Create Product Command
        if ( createOrderCommand.getQuantity()<=0 ){
            throw new IllegalArgumentException("Quantity can't be less or equal to zero");
        }
        if ((createOrderCommand.getOrderId() == null)&&(createOrderCommand.getProductId()== null)){
            throw new IllegalArgumentException("Fields can't be empty");
        }
        OrderCreatedEvent orderCreatedEvent = OrderCreatedEvent.from(createOrderCommand);
        AggregateLifecycle.apply(orderCreatedEvent);

    }
    @EventSourcingHandler
    public void on(OrderCreatedEvent orderCreatedEvent){
        LOGGER.info("   @EventSourcingHandler on OrderCreatedEvent ");
        this.productId = orderCreatedEvent.getProductId();
        this.userId = orderCreatedEvent.getUserId();
        this.orderId = orderCreatedEvent.getOrderId();
        this.orderStatus = orderCreatedEvent.getOrderStatus();
        this.quantity = orderCreatedEvent.getQuantity();
    }
    @CommandHandler
    public void handle(ApproveOrderCommand approveOrderCommand){
        //Create and publish ApproveOrderEvent
        OrderApprovedEvent orderApprovedEvent = new OrderApprovedEvent(approveOrderCommand.getOrderId());
        AggregateLifecycle.apply(orderApprovedEvent);
    }
    @EventSourcingHandler
    protected void on(OrderApprovedEvent orderApprovedEvent){
        this.orderStatus = orderApprovedEvent.getOrderStatus();
    }

    @CommandHandler
    public void handle(RejectOrderCommand rejectOrderCommand){
        //Create and publish RejectOrderEvent
        OrderRejectedEvent orderRejectedEvent = new OrderRejectedEvent(rejectOrderCommand.getOrderId(),rejectOrderCommand.getReason());
        AggregateLifecycle.apply(orderRejectedEvent);
    }
    @EventSourcingHandler
    protected void on(OrderRejectedEvent rejectedEvent){
        this.orderStatus = rejectedEvent.getOrderStatus();
    }
}
