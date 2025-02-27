package com.bezina.ProductService.query;

import com.bezina.ProductService.core.data.ProductEntity;
import com.bezina.ProductService.core.data.ProductRepository;
import com.bezina.ProductService.core.events.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public class ProductEventsHandler {
    private final ProductRepository productRepo;

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
}
