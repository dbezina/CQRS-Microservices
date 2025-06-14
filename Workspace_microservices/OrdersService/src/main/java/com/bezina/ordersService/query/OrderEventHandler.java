package com.bezina.ordersService.query;

import com.bezina.ordersService.core.DAO.OrderRepository;
import com.bezina.ordersService.core.entity.OrderEntity;
import com.bezina.ordersService.core.event.OrderApprovedEvent;
import com.bezina.ordersService.core.event.OrderCreatedEvent;
import com.bezina.ordersService.core.event.OrderRejectedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.axonframework.messaging.interceptors.ExceptionHandler;

@Component
@ProcessingGroup("order-group")
public class OrderEventHandler {

    @Autowired
    private OrderRepository orderRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEventHandler.class);


    public OrderEventHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @EventHandler
    public void on(OrderCreatedEvent event) throws Exception {
        LOGGER.info("class OrderEventHandler on "+ event.toString());

        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(event, orderEntity);
        LOGGER.info(orderEntity.toString());
        this.orderRepository.save(orderEntity);
    }
    @EventHandler
    public void on(OrderApprovedEvent event){
        OrderEntity orderEntity = orderRepository.findByOrderId(event.getOrderId());
        if (orderEntity == null){
            //some logic
            return;
        }
        orderEntity.setOrderStatus( event.getOrderStatus());
        orderRepository.save(orderEntity);
    }

    @EventHandler
    public void on(OrderRejectedEvent event){
        OrderEntity orderEntity = orderRepository.findByOrderId(event.getOrderId());
        orderEntity.setOrderStatus( event.getOrderStatus());
        orderRepository.save(orderEntity);
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




}
