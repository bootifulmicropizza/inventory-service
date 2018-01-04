package com.bootifulmicropizza.service.inventory.repository;

import com.bootifulmicropizza.service.inventory.domain.Catalogue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;

/**
 * Test to test the {@link CatalogueRepository} class.
 */
@DataJpaTest
@TestPropertySource("/test.properties")
@RunWith(SpringRunner.class)
public class CatalogueRepositoryTest {

    @Autowired
    private CatalogueRepository catalogueRepository;

    @Test
    public void findSingleCatalogueByCatalogueId() {
        catalogueRepository.save(new Catalogue("pizzas", "Pizza", Collections.EMPTY_SET));

        final Collection<Catalogue> pizza = catalogueRepository.findByCatalogueIdContainingIgnoreCase("pizza");

        assertThat(pizza, hasSize(1));
        assertThat(pizza.iterator().next().getName(), equalTo("Pizza"));
    }

    @Test
    public void findSingleCatalogueByNameThatDoesNotExist() {
        catalogueRepository.save(new Catalogue("pizzas", "Pizza", Collections.EMPTY_SET));

        final Collection<Catalogue> pizza = catalogueRepository.findByCatalogueIdContainingIgnoreCase("sides");

        assertThat(pizza, empty());
    }
}
