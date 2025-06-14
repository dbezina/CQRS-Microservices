package com.bezina.PaymentService.core.DAO;

import com.bezina.PaymentService.core.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, String > {
}
