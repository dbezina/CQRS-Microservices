package com.bezina.ordersService.command.rest;

import com.bezina.ordersService.command.DTO.OrderDTO;
import com.bezina.ordersService.command.commands.CreateOrderCommand;
import com.bezina.ordersService.core.entity.OrderSummary;
import com.bezina.ordersService.query.FindOrderQuery;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderCommandController {
    private final Environment env;
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;
    @Value("${order.default.userId}")
    private String defaultUserId;

    public OrderCommandController(Environment env, CommandGateway commandGateway, QueryGateway queryGateway) {
        this.env = env;
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping
    public OrderSummary postOrder(@Valid @RequestBody OrderDTO model){
        String newOrderId = UUID.randomUUID().toString();

        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                .productId(model.getProductId())
                .addressId(model.getAddressId())
                .quantity(model.getQuantity())
                .userId(defaultUserId)
                .orderId(newOrderId)
                .orderStatus(null)
                .build();

        SubscriptionQueryResult<OrderSummary, OrderSummary> queryResult =
                queryGateway.subscriptionQuery(new FindOrderQuery(newOrderId),
                        ResponseTypes.instanceOf(OrderSummary.class),
                        ResponseTypes.instanceOf(OrderSummary.class));

//        String returnValue ; //before subscription
//        returnValue = commandGateway.sendAndWait(createOrderCommand);
        try {
           commandGateway.sendAndWait(createOrderCommand);
           return queryResult.updates().blockFirst(Duration.of(10, ChronoUnit.SECONDS));
           /*
         blockFirst-  "Ожидай первый элемент из потока обновлений, и когда он придёт — продолжай выполнение".

           blockFirst() используется только в императивном (не реактивном) коде.
            В реактивной среде (WebFlux, Mono, Flux)
             следует использовать .flatMap, .switchMap, и т.д., без блокировки.*/
        } finally {
           queryResult.close();
        }
    }

}
