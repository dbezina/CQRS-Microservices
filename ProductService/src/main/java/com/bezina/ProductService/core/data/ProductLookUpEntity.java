package com.bezina.ProductService.core.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "product-lookup")
public class ProductLookUpEntity implements Serializable {
    public static final long serialVersionUID = -5274031794887574896L;
    @Id
    String productId;
    @Column(unique = true)
    String title;

    public ProductLookUpEntity(String productId, String title) {
        this.productId = productId;
        this.title = title;
    }
    public ProductLookUpEntity(){}

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
