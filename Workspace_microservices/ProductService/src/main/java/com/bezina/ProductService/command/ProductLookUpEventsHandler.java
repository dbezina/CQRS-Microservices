package com.bezina.ProductService.command;

import com.bezina.ProductService.core.data.ProductLookUpEntity;
import com.bezina.ProductService.core.data.ProductLookUpRepository;
import com.bezina.ProductService.core.events.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public class ProductLookUpEventsHandler {
    private final ProductLookUpRepository productLookUpRepo;

    public ProductLookUpEventsHandler(ProductLookUpRepository productLookUpRepo) {
        this.productLookUpRepo = productLookUpRepo;
    }

    @EventHandler
    public void on(ProductCreatedEvent event){
        ProductLookUpEntity productLookUpEntity = new ProductLookUpEntity(
                event.getProductId(),event.getTitle()
        );
        productLookUpRepo.save(productLookUpEntity);

    }
}
