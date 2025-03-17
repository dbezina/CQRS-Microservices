package com.bezina.ordersService.command.rest;

import com.bezina.ordersService.command.DTO.OrderDTO;
import com.bezina.ordersService.command.commands.CreateOrderCommand;
import com.bezina.ordersService.core.entity.enums.OrderStatus;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderCommandController {
    private final Environment env;
    private final CommandGateway commandGateway;
    @Value("${order.default.userId}")
    private String defaultUserId;

    public OrderCommandController(Environment env, CommandGateway commandGateway) {
        this.env = env;
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String postOrder(@Valid @RequestBody OrderDTO model){

        CreateOrderCommand createProductCommand = CreateOrderCommand.builder()
                .productId(model.getProductId())
                .addressId(model.getAddressId())
                .quantity(model.getQuantity())
                .userId(defaultUserId)
                .orderId(UUID.randomUUID().toString())
                .orderStatus(null)
                .build();

        String returnValue ;
        returnValue = commandGateway.sendAndWait(createProductCommand);
//        try {
//            returnValue = commandGateway.sendAndWait(createProductCommand);
//        } catch (Exception e) {
//            returnValue = e.getLocalizedMessage();
//        }
        return returnValue;
    }

}
