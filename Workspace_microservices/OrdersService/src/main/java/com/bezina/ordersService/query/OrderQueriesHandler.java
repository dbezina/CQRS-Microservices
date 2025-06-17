package com.bezina.ordersService.query;

import com.bezina.ordersService.core.DAO.OrderRepository;
import com.bezina.ordersService.core.entity.OrderEntity;
import com.bezina.ordersService.core.entity.OrderSummary;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class OrderQueriesHandler {
    OrderRepository orderRepo;

    public OrderQueriesHandler(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @QueryHandler
    public OrderSummary findOrder(FindOrderQuery findOrderQuery){
        OrderEntity orderEntity = orderRepo.findByOrderId(findOrderQuery.getOrderId());
        return new OrderSummary(orderEntity.getOrderId(),orderEntity.getOrderStatus(), "");
    }
}
