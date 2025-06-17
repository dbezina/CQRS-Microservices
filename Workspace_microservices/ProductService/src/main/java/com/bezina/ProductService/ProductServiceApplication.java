package com.bezina.ProductService;

import com.bezina.ProductService.command.interceptors.CreateProductCommandInterceptor;
import com.bezina.ProductService.core.errorHandling.ProductServiceEventsHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.PropagatingErrorHandler;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Autowired
    public void registerCreateProductCommandInterceptor(ApplicationContext context,
                                                        CommandBus commandBus) {
        commandBus.registerDispatchInterceptor(context.getBean(CreateProductCommandInterceptor.class));
    }

    @Autowired
    public void configure(EventProcessingConfigurer configurer) {
        //to register listener and Invokation Error Handler as specific processing group
        configurer.registerListenerInvocationErrorHandler("product-group"
                , configuration ->
                        new ProductServiceEventsHandler()
        );
        //instead of using custom error handler we can use Axon PropagatingErrorHandler
//		configurer.registerListenerInvocationErrorHandler("product-group"
//				, configuration ->
//						PropagatingErrorHandler.instance()
//		);
    }

    @Bean(name = "productSnapshotTriggerDefinition")
    public SnapshotTriggerDefinition productSnapshotTriggerDefinition(Snapshotter snapshotter) {
        return new EventCountSnapshotTriggerDefinition(snapshotter, 3); // 3 just for demo
    }

}
