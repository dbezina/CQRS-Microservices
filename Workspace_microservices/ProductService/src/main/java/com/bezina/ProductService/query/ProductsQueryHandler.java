package com.bezina.ProductService.query;

import com.bezina.ProductService.core.data.ProductEntity;
import com.bezina.ProductService.core.data.ProductRepository;
import com.bezina.ProductService.query.rest.ProductRestModel;
import com.mysql.cj.xdevapi.Collection;
import org.axonframework.queryhandling.QueryHandler;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ProductsQueryHandler {
    private final ProductRepository productRepo;
    public ProductsQueryHandler(ProductRepository productRepository) {
        productRepo = productRepository;
    }
    @QueryHandler
    public List<ProductRestModel> findProducts(FindProductsQuery query){
     //   List<ProductRestModel> productList = new ArrayList<>();

        return  Optional.ofNullable(productRepo.findAll().stream().map(productEntity ->{
            ProductRestModel restModel = new ProductRestModel();
            BeanUtils.copyProperties(productEntity,restModel);
            return restModel;
        }).collect(Collectors.toList())).orElse(Collections.EMPTY_LIST);
    }
}
