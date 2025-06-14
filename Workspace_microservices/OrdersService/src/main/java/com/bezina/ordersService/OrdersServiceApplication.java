package com.bezina.ordersService;

import com.bezina.ordersService.command.interceptor.CreateOrderCommandInterceptor;
import com.bezina.ordersService.core.errorHandling.OrderServiceEventsHandler;
import com.thoughtworks.xstream.XStream;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
public class OrdersServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdersServiceApplication.class, args);
	}
	@Autowired
	public void registerCreateOrderCommandInterceptor(ApplicationContext context,
													  CommandBus commandBus) {
		commandBus.registerDispatchInterceptor(context.getBean(CreateOrderCommandInterceptor.class));
	}
	@Autowired
	public void configure (EventProcessingConfigurer configurer){
		//to register listener and Invocation ErrorMessage Handler as specific processing group
		configurer.registerListenerInvocationErrorHandler("order-group"
				, configuration ->
						new OrderServiceEventsHandler()
		);
		//instead of using custom error handler we can use Axon PropagatingErrorHandler
//		configurer.registerListenerInvocationErrorHandler("product-group"
//				, configuration ->
//						PropagatingErrorHandler.instance()
//		);
	}

}
