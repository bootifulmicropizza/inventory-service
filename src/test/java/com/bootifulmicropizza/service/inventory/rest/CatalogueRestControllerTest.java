package com.bootifulmicropizza.service.inventory.rest;

import com.bootifulmicropizza.service.inventory.TestUtils;
import com.bootifulmicropizza.service.inventory.domain.Catalogue;
import com.bootifulmicropizza.service.inventory.repository.CatalogueRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class to test the {@link CatalogueRestController} with security disabled.
 */
@WebMvcTest(controllers = CatalogueRestController.class, secure = false)
@Import(TestUtils.class)
@AutoConfigureDataJpa
@TestPropertySource("/test.properties")
@RunWith(SpringRunner.class)
public class CatalogueRestControllerTest {

    @Autowired
    private TestUtils testUtils;

    @MockBean
    private CatalogueRepository catalogueRepository;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        final Catalogue catalogue = new Catalogue("pizzas", "Pizza", Collections.EMPTY_SET);
        catalogue.setId(1L);

        when(catalogueRepository.findAll()).thenReturn(Collections.singletonList(catalogue));

        when(catalogueRepository.findById(1L)).thenReturn(Optional.of(catalogue));

        when(catalogueRepository.save(any(Catalogue.class))).thenReturn(catalogue);

        when(catalogueRepository.findByCatalogueIdContainingIgnoreCase(any(String.class)))
            .thenReturn(Collections.singletonList(catalogue));
    }

    @Test
    public void getAllCatalogues() throws Exception {
        mockMvc.perform(get("/catalogues/"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("@.[0].name").value("Pizza"));

        verify(catalogueRepository, times(1)).findAll();
    }

    @Test
    public void getSingleCatalogue() throws Exception {
        mockMvc.perform(get("/catalogues/1/"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("name").value("Pizza"));

        verify(catalogueRepository, times(1)).findById(1L);
    }

    @Test
    public void getSingleCatalogueThatDoesNotExist() throws Exception {
        mockMvc.perform(get("/catalogues/2/"))
               .andExpect(status().isNotFound());

        verify(catalogueRepository, times(1)).findById(2L);
    }

    @Test
    public void findSingleCatalogueByName() throws Exception {
        mockMvc.perform(get("/catalogues/search/by-catalogue-id/?catalogueId=pizzas"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("catalogueId").value("pizzas"));

        verify(catalogueRepository, times(1)).findByCatalogueIdContainingIgnoreCase("pizzas");
    }

    @Test
    public void saveSingleCatalogue() throws Exception {
        final Catalogue catalogue = new Catalogue();

        mockMvc.perform(
                    post("/catalogues/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(testUtils.asJsonString(catalogue)))
               .andExpect(status().isCreated())
               .andExpect(MockMvcResultMatchers.header().string("LOCATION", "/catalogues/1/"));

        verify(catalogueRepository, times(1)).save(any(Catalogue.class));
    }

    @Test
    public void updateSingleCatalogue() throws Exception {
        when(catalogueRepository.existsById(1L)).thenReturn(true);

        final Catalogue catalogue = new Catalogue();

        mockMvc.perform(
                    put("/catalogues/1/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(testUtils.asJsonString(catalogue)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("name").value("Pizza"));

        verify(catalogueRepository, times(1)).save(any(Catalogue.class));
    }

    @Test
    public void updateSingleCatalogueThatDoesNotExist() throws Exception {
        when(catalogueRepository.existsById(1L)).thenReturn(false);

        final Catalogue catalogue = new Catalogue();

        mockMvc.perform(
                    put("/catalogues/1/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(testUtils.asJsonString(catalogue)))
               .andExpect(status().isNotFound());

        verify(catalogueRepository, times(1)).existsById(1L);
    }

    @Test
    public void deleteSingleCatalogue() throws Exception {
        mockMvc.perform(delete("/catalogues/1/"))
               .andExpect(status().isNoContent());

        verify(catalogueRepository, times(1)).deleteById(1L);
    }
}
