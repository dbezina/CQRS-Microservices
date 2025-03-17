package com.bezina.ProductService.command.rest;

import com.bezina.ProductService.command.CreateProductCommand;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductsCommandController {
    @Autowired
    public ProductsCommandController(Environment env, CommandGateway commandGateway) {
        this.env = env;
        this.commandGateway = commandGateway;
    }

    private final Environment env;
    private final CommandGateway commandGateway;

    @PostMapping
    public String postProduct(@Valid @RequestBody CreateProductRestModel model){

        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .price(model.getPrice())
                .quantity(model.getQuantity())
                .title(model.getTitle())
                .productId(UUID.randomUUID().toString())
                .build();

        String returnValue ;
        returnValue = commandGateway.sendAndWait(createProductCommand);
//        try {
//            returnValue = commandGateway.sendAndWait(createProductCommand);
//        } catch (Exception e) {
//            returnValue = e.getLocalizedMessage();
//        }
        return returnValue;
       // return "POST handling " + model.getTitle();
    }
//    @GetMapping
//    public String getProduct(){
//        return "GET handling " +env.getProperty("local.server.port");
//    }
//    @PutMapping
//    public String putProduct(){
//        return "PUT handling";
//    }
//    @DeleteMapping
//    public String deleteProduct(){
//        return "DELETE handling";
//    }

}
