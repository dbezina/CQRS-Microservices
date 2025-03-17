package com.bezina.ordersService.core.DAO;

import com.bezina.ordersService.core.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, String> {

}
