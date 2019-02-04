package com.bootifulmicropizza.service.inventory;

import com.bootifulmicropizza.service.inventory.domain.Catalogue;
import com.bootifulmicropizza.service.inventory.domain.Money;
import com.bootifulmicropizza.service.inventory.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test to verify JPA configuration.
 */
@DataJpaTest
@TestPropertySource("/test.properties")
@RunWith(SpringRunner.class)
public class InventoryJpaTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void verifyJpaConfigurationForCatalogue() {
        Catalogue catagloue = testEntityManager.persistFlushFind(new Catalogue("pizzas", "Pizzas", Collections.EMPTY_SET));

        assertThat(catagloue.getName(), equalTo("Pizzas"));
    }

    @Test
    public void verifyJpaConfigurationForProduct() {
        Product product = testEntityManager.persistFlushFind(
            new Product("12345", "Cheese Feast", "Classic mixed cheese",
                        "/images/products/cheese-feast.jpg", new BigDecimal(2.99)));

        assertThat(product.getProductId(), equalTo("12345"));
        assertThat(product.getName(), equalTo("Cheese Feast"));
        assertThat(product.getDescription(), equalTo("Classic mixed cheese"));
        assertThat(product.getImagePath(), equalTo("/images/products/cheese-feast.jpg"));

        final BigDecimal expectedUnitPrice = (new BigDecimal(2.99)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        assertThat(product.getUnitPrice(), equalTo(expectedUnitPrice));
    }
}
