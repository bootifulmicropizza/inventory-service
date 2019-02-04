package com.bootifulmicropizza.service.inventory.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Catalogue entity.
 */
@Entity
public class Catalogue extends BaseEntity implements Serializable {

    private Long id;

    private String catalogueId;

    private String name;

    private Set<Product> products = new HashSet<>();

    public Catalogue() {
    }

    public Catalogue(final String catalogueId, final String name, final Set<Product> products) {
        this.catalogueId = catalogueId;
        this.name = name;
        this.products = products;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getCatalogueId() {
        return catalogueId;
    }

    public void setCatalogueId(String catalogueId) {
        this.catalogueId = catalogueId;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(final Set<Product> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Catalogue catalogue = (Catalogue) o;
        return Objects.equals(id, catalogue.id) &&
               Objects.equals(name, catalogue.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
