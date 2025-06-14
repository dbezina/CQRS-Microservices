package com.bezina.PaymentService.command;
import com.bezina.core.commands.ProcessPaymentCommand;
import com.bezina.core.events.PaymentProcessedEvent;
import com.bezina.core.model.PaymentDetails;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aggregate
public class PaymentAggregate {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentAggregate.class);

    @AggregateIdentifier
    private String paymentId;
    private String orderId;
    public PaymentAggregate(){}

    public PaymentAggregate(String paymentId, String orderId, PaymentDetails paymentDetails) {
        this.paymentId = paymentId;
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getOrderId() {
        return orderId;
    }


    @CommandHandler
    public PaymentAggregate(ProcessPaymentCommand processPaymentCommand)  {

        LOGGER.info(" @CommandHandler PaymentAggregate");
        //validate Create Product Command

        if ((processPaymentCommand.getOrderId() == null)&&(processPaymentCommand.getPaymentId() == null)){
            throw new IllegalArgumentException("Fields can't be empty");
        }

        PaymentProcessedEvent paymentProcessedEvent = PaymentProcessedEvent.from(processPaymentCommand);

        AggregateLifecycle.apply(paymentProcessedEvent);

    }
    @EventSourcingHandler
    public void on(PaymentProcessedEvent paymentProcessedEvent){
        LOGGER.info("   @EventSourcingHandler on PaymentProcessedEvent ");
        this.paymentId = paymentProcessedEvent.getPaymentId();
        this.orderId = paymentProcessedEvent.getOrderId();

    }
}
