package com.bootifulmicropizza.service.inventory.rest;

import com.bootifulmicropizza.service.inventory.domain.Product;
import com.bootifulmicropizza.service.inventory.repository.ProductRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for the {@code /products/} endpoint.
 */
@RestController
@RequestMapping(value ="/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductRestController {

    private ProductRepository productRepository;

    public ProductRestController(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> getProducts() {
        final List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(product -> products.add(product));

        return ResponseEntity.ok(products);
    }


    @GetMapping("/{id}/")
    public ResponseEntity<Product> getProduct(@PathVariable("id") final Long id) {
        final Product product = productRepository.findOne(id);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(product);
    }

    @GetMapping("/search/by-product-id")
    public ResponseEntity<Product> getProductById(@RequestParam("productId") final String productId) {
        final Product product = productRepository.findByProductId(productId);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(product);
    }

    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@RequestBody final Product product) {
        final Product createdProduct = productRepository.save(product);

        return ResponseEntity
            .created(URI.create("/products/" + createdProduct.getId() + "/"))
            .body(createdProduct);
    }

    @PutMapping("/{id}/")
    public ResponseEntity<Product> updateProduct(@RequestBody final Product product, @PathVariable("id") final Long id) {
        if (!productRepository.exists(id)) {
            return ResponseEntity.notFound().build();
        }

        final Product createdProduct = productRepository.save(product);

        return ResponseEntity.ok(createdProduct);
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<Product> deleteProduct(@PathVariable final Long id) {
        productRepository.delete(id);

        return ResponseEntity.noContent().build();
    }
}
