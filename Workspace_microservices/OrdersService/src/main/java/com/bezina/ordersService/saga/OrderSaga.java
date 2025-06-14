package com.bezina.ordersService.saga;

import com.bezina.core.commands.CancelProductReservationCommand;
import com.bezina.core.commands.ProcessPaymentCommand;
import com.bezina.core.commands.ReserveProductCommand;
import com.bezina.core.events.PaymentProcessedEvent;
import com.bezina.core.events.ProductReservationCancelledEvent;
import com.bezina.core.events.ProductReservedEvent;
import com.bezina.core.model.User;
import com.bezina.core.query.FetchUserPaymentDetailsQuery;
import com.bezina.ordersService.command.commands.ApproveOrderCommand;
import com.bezina.ordersService.command.commands.RejectOrderCommand;
import com.bezina.ordersService.core.event.OrderApprovedEvent;
import com.bezina.ordersService.core.event.OrderCreatedEvent;
import com.bezina.ordersService.core.event.OrderRejectedEvent;
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
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
            cancelProductReservation(productReservedEvent, e.getLocalizedMessage());
            return;
        }
        if(userPaymentDetails == null){
            LOGGER.info("User payment details are null");
            //compensating transactions
            cancelProductReservation(productReservedEvent, "User payment details are null");
            return;
        }

        LOGGER.info("Successfully fetched payment for user: "+ userPaymentDetails.getFirstName());

        ProcessPaymentCommand processPaymentCommand = new ProcessPaymentCommand.Builder()
                .orderId(productReservedEvent.getOrderId())
                .paymentDetails(userPaymentDetails.getPaymentDetails())
                .paymentId(UUID.randomUUID().toString()) // generated
                .build();
        String paymentCommandResult = null;
        try {
            paymentCommandResult = commandGateway.sendAndWait(processPaymentCommand, 10, TimeUnit.SECONDS);
            //wait until the result is returned or the timeout is reached or the thread is interrupted
        } catch (Exception ex){
            LOGGER.error("something went wrong with processPaymentCommand "+ ex.getMessage());
            //start a compensating transaction
            cancelProductReservation(productReservedEvent, ex.getLocalizedMessage());
            return;
        }
        if (paymentCommandResult == null){
            LOGGER.error("something went wrong with processPaymentCommand. Result is null. Initiating a compensating transaction");
            //start a compensating transaction
            cancelProductReservation(productReservedEvent, "Couldn't process user payment with the provided details");
        }
    }
    private void cancelProductReservation(ProductReservedEvent productReservedEvent, String reason){
        CancelProductReservationCommand cancelProductReservationCommand = new CancelProductReservationCommand.Builder()
                .orderId(productReservedEvent.getOrderId())
                .productId(productReservedEvent.getProductId())
                .userId(productReservedEvent.getUserId())
                .quantity(productReservedEvent.getQuantity())
                .reason(reason)
                .build();
        commandGateway.send(cancelProductReservationCommand);
    }
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentProcessedEvent paymentProcessedEvent){
        //send an ApproveOrderCommand
        ApproveOrderCommand approveOrderCommand = new ApproveOrderCommand(paymentProcessedEvent.getOrderId());
        commandGateway.send(approveOrderCommand);
    }
    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderApprovedEvent approvedEvent){
        LOGGER.info("Order is approved. Order Saga is completed for OrderId "+ approvedEvent.getOrderId());
       // SagaLifecycle.end();
        //after than this instance of saga could not handle any event anymore
    }
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservationCancelledEvent productReservationCancelledEvent){
        LOGGER.info("Order is cancelled for the id: "+ productReservationCancelledEvent.getOrderId());
        //create and send a rejectOrderCommand
        RejectOrderCommand rejectOrderCommand = new RejectOrderCommand(
                productReservationCancelledEvent.getOrderId(),
                productReservationCancelledEvent.getReason());
        commandGateway.send(rejectOrderCommand);
    }
    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderRejectedEvent orderRejectedEvent){
        LOGGER.info("Successfully rejected order for the id: " + orderRejectedEvent.getOrderId()+
                " and the reason "+orderRejectedEvent.getReason() );
        // SagaLifecycle.end();
        //after than this instance of saga could not handle any event anymore
    }

}