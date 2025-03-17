package com.bezina.ProductService.command.interceptors;

import com.bezina.ProductService.command.CreateProductCommand;
import com.bezina.ProductService.core.data.ProductLookUpEntity;
import com.bezina.ProductService.core.data.ProductLookUpRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.BiFunction;

@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateProductCommand.class);
    private final ProductLookUpRepository productLookUpRepo;

    public CreateProductCommandInterceptor(ProductLookUpRepository productLookUpRepo) {
        this.productLookUpRepo = productLookUpRepo;
    }

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle
            (@Nonnull List<? extends CommandMessage<?>> messages) {

        return (index,command) -> {

            LOGGER.info("Intercepted command "+command.getPayloadType());

            if(CreateProductCommand.class.equals(command.getPayloadType())){
                CreateProductCommand createProductCommand = (CreateProductCommand)command.getPayload();

                ProductLookUpEntity productLookUpEntity = productLookUpRepo.findByProductIdOrTitle(
                      createProductCommand.getProductId(), createProductCommand.getTitle());

                if (productLookUpEntity != null){
                      throw new IllegalStateException(String.format("Product with productId %s or title %s is already exists"
                              ,createProductCommand.getProductId()
                              ,createProductCommand.getTitle())
                      );
                }
            }
            return command;
        };
    }
}
