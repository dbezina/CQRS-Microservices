package com.bezina.ProductService.query;

import com.bezina.ProductService.core.data.ProductEntity;
import com.bezina.ProductService.core.data.ProductRepository;
import com.bezina.ProductService.core.events.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public class ProductEventsHandler {
    private final ProductRepository productRepo;

    public ProductEventsHandler(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @EventHandler
    public void on(ProductCreatedEvent event){
        ProductEntity product = new ProductEntity();
        BeanUtils.copyProperties(event, product);

        productRepo.save(product);
    }
}
