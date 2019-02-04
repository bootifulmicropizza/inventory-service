package com.bootifulmicropizza.service.inventory;

import com.bootifulmicropizza.service.inventory.domain.Catalogue;
import com.bootifulmicropizza.service.inventory.domain.Product;
import com.bootifulmicropizza.service.inventory.repository.CatalogueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@EnableTransactionManagement
@EnableJpaAuditing
@EnableDiscoveryClient
@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }
}

@Component
class InventoryRunner implements CommandLineRunner {

    private final CatalogueRepository catalogueRepository;

    @Autowired
    private DataSource dataSource;

    public InventoryRunner(CatalogueRepository catalogueRepository) {
        this.catalogueRepository = catalogueRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("DataSource: " + dataSource);

        if (catalogueRepository.count() == 0) {
            buildPizzaCatalogue();
            buildSideCatalogue();
            buildDessertCatalogue();
            buildDrinkCatalogue();
        }

        catalogueRepository.findAll().forEach(catalogue -> {
            System.out.println("Catalogue id: " + catalogue.getId());
            System.out.println("Catalogue name: " + catalogue.getName());
            catalogue.getProducts().forEach(product -> System.out.println("Product: " + product));
        });
    }

    private void buildPizzaCatalogue() {
        final Set<Product> products = new HashSet<>();

        products.add(new Product("1", "Sloppy Joe", "Hot and spicy", "", BigDecimal.valueOf(9.99)));
        products.add(new Product("2", "Classic cheese", "Just cheese", "", BigDecimal.valueOf(9.99)));
        products.add(new Product("3", "Farmhouse", "Classic ham and mushroom", "", BigDecimal.valueOf(9.99)));

        catalogueRepository.save(new Catalogue("PIZZAS", "Pizza", products));
    }

    private void buildSideCatalogue() {
        final Set<Product> products = new HashSet<>();

        products.add(new Product("1", "Garlic pizza", "Cheese and garlic on a pizza base", "", BigDecimal.valueOf(6.99)));
        products.add(new Product("2", "Potato wedges", "Wedges made of potatoe", "", BigDecimal.valueOf(3.99)));
        products.add(new Product("3", "Chicken dippers", "Chicken that you dip", "", BigDecimal.valueOf(4.99)));

        catalogueRepository.save(new Catalogue("SIDES", "Side", products));
    }

    private void buildDessertCatalogue() {
        final Set<Product> products = new HashSet<>();

        products.add(new Product("1", "Vanilla ice cream", "500ml tub of vanilla flavor ice cream", "", BigDecimal.valueOf(4.99)));
        products.add(new Product("2", "Chocolate ice cream", "500ml tub of chocolate flavor ice cream", "", BigDecimal.valueOf(4.99)));
        products.add(new Product("3", "Fudge brownie", "Mmmmmmm....fudge.", "", BigDecimal.valueOf(3.99)));

        catalogueRepository.save(new Catalogue("DESSERTS", "Dessert", products));
    }

    private void buildDrinkCatalogue() {
        final Set<Product> products = new HashSet<>();

        products.add(new Product("1", "Coke cola", "1 litre bottle of coke cola", "", BigDecimal.valueOf(1.99)));
        products.add(new Product("2", "Lemonade", "1 litre bottle of lemonade", "", BigDecimal.valueOf(1.99)));
        products.add(new Product("3", "Orangeade", "1 litre bottle of orangeade", "", BigDecimal.valueOf(1.99)));

        catalogueRepository.save(new Catalogue("DRINKS", "Drink", products));
    }
}
