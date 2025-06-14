package com.bezina.ProductService.query;

import com.bezina.ProductService.core.data.ProductEntity;
import com.bezina.ProductService.core.data.ProductRepository;
import com.bezina.ProductService.core.events.ProductCreatedEvent;
import com.bezina.core.events.ProductReservationCancelledEvent;
import com.bezina.core.events.ProductReservedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public class ProductEventsHandler {
    private final ProductRepository productRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductReservedEvent.class);

    public ProductEventsHandler(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }
    @ExceptionHandler(resultType = Exception.class)
    public void handle(Exception ex) throws Exception {
       throw ex;
    }
    @ExceptionHandler(resultType = IllegalArgumentException.class)
    public void handle(IllegalArgumentException ex){
        //Log error message
        //can handle only IllegalArgumentException exceptions in this class
    }

    @EventHandler
    public void on(ProductCreatedEvent event) throws Exception {
        ProductEntity product = new ProductEntity();
        BeanUtils.copyProperties(event, product);
        try{
            productRepo.save(product);}
        catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }
//        if (true) throw new Exception("Forcing Exception in ProductEventHandler class");
    }

    @EventHandler
    public void on(ProductReservedEvent productReservedEvent){
       ProductEntity product = productRepo.findByProductId(productReservedEvent.getProductId());

        LOGGER.info("productReservedEvent BEFORE setQuantity Quantity is "+productReservedEvent.getQuantity() );
       product.setQuantity(product.getQuantity() - productReservedEvent.getQuantity());

       productRepo.save(product);

        LOGGER.info("productReservedEvent AFTER setQuantity Quantity is "+product.getQuantity() );


        LOGGER.info("ProductReservedEvent is called for productId: "+ productReservedEvent.getProductId()+
               " and orederId "+ productReservedEvent.getOrderId());
    }

    @EventHandler
    public void on(ProductReservationCancelledEvent productReservationCancelledEvent){
        ProductEntity product = productRepo.findByProductId(productReservationCancelledEvent.getProductId());

        LOGGER.info("ProductReservationCancelledEvent BEFORE setQuantity Quantity is "+product.getQuantity() );
        product.setQuantity(product.getQuantity() + productReservationCancelledEvent.getQuantity());
        productRepo.save(product);

        LOGGER.info("ProductReservationCancelledEvent AFTER setQuantity Quantity is "+product.getQuantity() );

        LOGGER.info("ProductReservationCancelledEvent is called for productId: "+ product.getProductId()+
                " and orederId "+ productReservationCancelledEvent.getOrderId() +
                " with quantity "+ productReservationCancelledEvent.getQuantity());
    }
}
