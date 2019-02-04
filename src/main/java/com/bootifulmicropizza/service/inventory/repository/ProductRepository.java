package com.bootifulmicropizza.service.inventory.repository;

import com.bootifulmicropizza.service.inventory.domain.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Spring Data repository for products.
 */
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    Product findByProductId(final String productId);
}
