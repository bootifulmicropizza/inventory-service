package com.bootifulmicropizza.service.inventory.repository;

import com.bootifulmicropizza.service.inventory.domain.Catalogue;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

/**
 * Spring Data repository for catalogues.
 */
public interface CatalogueRepository extends PagingAndSortingRepository<Catalogue, Long> {

    Collection<Catalogue> findByCatalogueIdContainingIgnoreCase(final String catalogueId);
}
