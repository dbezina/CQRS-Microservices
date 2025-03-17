package com.bezina.ProductService.core.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


public interface ProductRepository extends JpaRepository<ProductEntity,String> {
    ProductEntity findByProductId(String productID);
    ProductEntity findByProductIdOrTitle(String productId, String title);
}
