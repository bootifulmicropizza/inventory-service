package com.bootifulmicropizza.service.inventory.rest;

import com.bootifulmicropizza.service.inventory.domain.Catalogue;
import com.bootifulmicropizza.service.inventory.repository.CatalogueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * REST controller for the {@code /catalogues/} endpoint.
 */
@RestController
@RequestMapping(value ="/catalogues", produces = MediaType.APPLICATION_JSON_VALUE)
public class CatalogueRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CatalogueRestController.class);

    private CatalogueRepository catalogueRepository;

    public CatalogueRestController(final CatalogueRepository catalogueRepository) {
        this.catalogueRepository = catalogueRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<Catalogue>> getCatalogues() {
        final List<Catalogue> catalogues = new ArrayList<>();
        catalogueRepository.findAll().forEach(catalogue -> catalogues.add(catalogue));

        return ResponseEntity.ok(catalogues);
    }

    @GetMapping("/{id}/")
    public ResponseEntity<Catalogue> getCatalogue(@PathVariable("id") final Long id) {
        final Catalogue catalogue = catalogueRepository.findOne(id);

        if (catalogue == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(catalogue);
    }

    @GetMapping("/search/by-catalogue-id")
    public ResponseEntity<Catalogue> getCatalogueByCatalogueId(@RequestParam("catalogueId") final String catalogueId) {
        final Collection<Catalogue> catalogues = catalogueRepository.findByCatalogueIdContainingIgnoreCase(catalogueId);

        if (catalogues.size() > 1) {
            LOGGER.info("Got more than one catalogue when searching for catalogue by catalogue ID {}.", catalogueId);
//            return ResponseEntity.badRequest().build();
        }

        if (catalogues.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(catalogues.iterator().next());
    }

    @PostMapping("/")
    public ResponseEntity<Catalogue> createCatalogue(@RequestBody final Catalogue catalogue) {
        final Catalogue createdCatalogue = catalogueRepository.save(catalogue);

        return ResponseEntity
            .created(URI.create("/catalogues/" + createdCatalogue.getId() + "/"))
            .body(createdCatalogue);
    }

    @PutMapping("/{id}/")
    public ResponseEntity<Catalogue> updateCatalogue(@RequestBody final Catalogue catalogue, @PathVariable("id") final Long id) {
        if (!catalogueRepository.exists(id)) {
            return ResponseEntity.notFound().build();
        }

        final Catalogue createdCatalogue = catalogueRepository.save(catalogue);

        return ResponseEntity.ok(createdCatalogue);
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<Catalogue> deleteCatalogue(@PathVariable final Long id) {
        catalogueRepository.delete(id);

        return ResponseEntity.noContent().build();
    }
}
