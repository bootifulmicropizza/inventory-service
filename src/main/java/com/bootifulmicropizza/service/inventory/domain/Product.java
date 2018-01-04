package com.bootifulmicropizza.service.inventory.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Product entity.
 */
@Entity
public class Product extends BaseEntity implements Serializable {

    private Long id;

    private String productId;

    private String name;

    private String description;

    private String imagePath;

    private BigDecimal unitPrice;

    public Product() {

    }

    public Product(final String productId, final String name, final String description, final String imagePath,
                   final BigDecimal unitPrice) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.unitPrice = setScale(unitPrice);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = setScale(unitPrice);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
            Objects.equals(productId, product.productId) &&
            Objects.equals(name, product.name) &&
            Objects.equals(description, product.description) &&
            Objects.equals(unitPrice, product.unitPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, name, description, unitPrice);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    private BigDecimal setScale(final BigDecimal value) {
        return value.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
}
