package com.bezina.ProductService.core.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity,String> {
    ProductEntity findByProductId(String productID);
    ProductEntity findByProductIdOrTitle(String productId, String title);
}
