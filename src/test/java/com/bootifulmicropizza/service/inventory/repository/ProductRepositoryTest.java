package com.bootifulmicropizza.service.inventory.repository;

import com.bootifulmicropizza.service.inventory.domain.Money;
import com.bootifulmicropizza.service.inventory.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test to test the {@link ProductRepository} class.
 */
@DataJpaTest
@TestPropertySource("/test.properties")
@RunWith(SpringRunner.class)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product =
        new Product("12345", "Cheese Feast", "Classic mixed cheese",
                    "/images/products/cheese-feast.jpg", new BigDecimal(5.99));


    @Test
    public void findSingleProductByName() {
        productRepository.save(product);

        final Product product = productRepository.findByProductId("12345");

        assertThat(product, notNullValue());
        assertThat(product.getName(), equalTo("Cheese Feast"));
    }

    @Test
    public void findSingleProductByNameThatDoesNotExist() {
        productRepository.save(product);

        final Product product = productRepository.findByProductId("54321");

        assertThat(product, nullValue());
    }
}
