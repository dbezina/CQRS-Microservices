package com.bezina.ordersService.saga;

import com.bezina.core.commands.ReserveProductCommand;
import com.bezina.core.events.ProductReservedEvent;
import com.bezina.core.model.User;
import com.bezina.core.query.FetchUserPaymentDetailsQuery;
import com.bezina.ordersService.core.event.OrderCreatedEvent;
import jakarta.annotation.PostConstruct;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;

@Saga
@ProcessingGroup("OrderSagaProcessor")
public class OrderSaga {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderSaga.class);
    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient QueryGateway queryGateway;
    //bottleneck

    public OrderSaga(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }
    public OrderSaga() {
    }

    @PostConstruct
    public void init() {
        System.out.println("Saga instance created: " + this);
        System.out.println("CommandGateway injected: " + (commandGateway != null));
        LOGGER.info("Saga instance created: {}", this);
        LOGGER.info("CommandGateway injected: {}", (commandGateway != null));
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId") //property name from event object
    public void handle(OrderCreatedEvent orderCreatedEvent){
        ReserveProductCommand reserveProductCommand = new ReserveProductCommand.Builder()
                .orderId(orderCreatedEvent.getOrderId())
                .productId(orderCreatedEvent.getProductId())
                .quantity(orderCreatedEvent.getQuantity())
                .userId(orderCreatedEvent.getUserId())
                .build();
        LOGGER.info("OrderCreatedEvent handled for OrderId: "+ reserveProductCommand.getOrderId()
                + " and productId: "+ reserveProductCommand.getProductId());
        LOGGER.info("Sending ReserveProductCommand: {}", reserveProductCommand);

        commandGateway.send(reserveProductCommand,
                (commandMessage, commandResultMessage) -> {
                    if (commandResultMessage.isExceptional()){
                        LOGGER.error("commandResultMessage.isExceptional");
                        LOGGER.info("starting a compensating transaction");
                        //start a compensating transaction
                    }
                    else {
                        LOGGER.info("reserveProductCommand executed successfully");
                    }
                });
    }
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservedEvent productReservedEvent){
       //Process user payment
        LOGGER.info("ProductReservedEvent is called for OrderId: "+ productReservedEvent.getOrderId() +
                " and productId: "+ productReservedEvent.getProductId());
        FetchUserPaymentDetailsQuery query = new FetchUserPaymentDetailsQuery(productReservedEvent.getUserId());
        User userPaymentDetails = null;
        try {
            userPaymentDetails = queryGateway.query(query, ResponseTypes.instanceOf(User.class)).join() ;
        }
        catch (Exception e){
            LOGGER.error(e.getLocalizedMessage());
            //compensating transactions
            return;
        }
        if(userPaymentDetails == null){
            LOGGER.info("User payment details are null");
            //compensating transactions
            return;
        }

        LOGGER.info("Successfully fetched payment for user: "+ userPaymentDetails.getFirstName());
    }

//    @EndSaga
//    @SagaEventHandler(associationProperty = "orderId")
//    public void handle(OrderCompletedEvent event) {
//        LOGGER.info("Saga completed for order: " + event.getOrderId());
//    }
}