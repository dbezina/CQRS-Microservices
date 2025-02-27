package com.bezina.ordersService.command.interceptor;

import com.bezina.ordersService.command.commands.CreateOrderCommand;
import jakarta.annotation.Nonnull;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

@Component
public class CreateOrderCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateOrderCommand.class);
  //  private final OrderLookupRepository orderLookupRepo;

//    public CreateOrderCommandInterceptor(OrderLookupRepository orderLookupRepo) {
//        this.orderLookupRepo = orderLookupRepo;
//    }

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle
            (@Nonnull List<? extends CommandMessage<?>> messages) {

        return (index,command) -> {

            LOGGER.info("Intercepted command "+command.getPayloadType());

            if(CreateOrderCommand.class.equals(command.getPayloadType())){
                CreateOrderCommand createProductCommand = (CreateOrderCommand)command.getPayload();

                 /*   OrderLookupEntity orderLookUpEntity = orderLookupRepo.findById(new OrderLookupId(
                            createProductCommand.getProductId(), createProductCommand.getUserId()))
                            .orElseThrow(() -> new EntityNotFoundException("Order not found: "
                                    + createProductCommand.getProductId()+" "+ createProductCommand.getUserId()));

                    if (orderLookUpEntity != null){
                        throw new IllegalStateException(String.format("Order with orderId %s and userId %s is already exists"
                                ,createProductCommand.getOrderId()
                                ,createProductCommand.getUserId())
                        );
                    }*/
            }
            return command;
        };
    }


}
