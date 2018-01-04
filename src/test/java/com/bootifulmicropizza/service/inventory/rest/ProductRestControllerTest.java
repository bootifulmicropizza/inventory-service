package com.bootifulmicropizza.service.inventory.rest;

import com.bootifulmicropizza.service.inventory.TestUtils;
import com.bootifulmicropizza.service.inventory.domain.Product;
import com.bootifulmicropizza.service.inventory.repository.ProductRepository;
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

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class to test the {@link ProductRestController} with security disabled.
 */
@WebMvcTest(controllers = ProductRestController.class, secure = false)
@Import(TestUtils.class)
@AutoConfigureDataJpa
@TestPropertySource("/test.properties")
@RunWith(SpringRunner.class)
public class ProductRestControllerTest {

    @Autowired
    private TestUtils testUtils;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        final Product product = createTestProduct();

        product.setId(1L);

        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));

        when(productRepository.findOne(1L)).thenReturn(product);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        when(productRepository.findByProductId(any(String.class))).thenReturn(product);
    }

    @Test
    public void getAllProducts() throws Exception {
        mockMvc.perform(get("/products/"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("@.[0].name").value("Cheese Feast"));

        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void getSingleProduct() throws Exception {
        mockMvc.perform(get("/products/1/"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("name").value("Cheese Feast"));

        verify(productRepository, times(1)).findOne(1L);
    }

    @Test
    public void getSingleProductThatDoesNotExist() throws Exception {
        mockMvc.perform(get("/products/2/"))
               .andExpect(status().isNotFound());

        verify(productRepository, times(1)).findOne(2L);
    }

    @Test
    public void findSingleProductById() throws Exception {
        mockMvc.perform(get("/products/search/by-product-id?productId=12345"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("name").value("Cheese Feast"));

        verify(productRepository, times(1)).findByProductId("12345");
    }

    @Test
    public void saveSingleProduct() throws Exception {
        final Product product = createTestProduct();

        mockMvc.perform(
                    post("/products/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(testUtils.asJsonString(product)))
               .andExpect(status().isCreated())
               .andExpect(MockMvcResultMatchers.header().string("LOCATION", "/products/1/"));

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void updateSingleProduct() throws Exception {
        when(productRepository.exists(1L)).thenReturn(true);

        final Product product = createTestProduct();

        mockMvc.perform(
                    put("/products/1/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(testUtils.asJsonString(product)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
               .andExpect(jsonPath("name").value("Cheese Feast"));

        verify(productRepository, times(1)).exists(1L);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void updateSingleProductThatDoesNotExist() throws Exception {
        when(productRepository.exists(1L)).thenReturn(false);

        final Product product = createTestProduct();

        mockMvc.perform(
                    put("/products/1/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(testUtils.asJsonString(product)))
               .andExpect(status().isNotFound());

        verify(productRepository, times(1)).exists(1L);
    }

    @Test
    public void deleteSingleProduct() throws Exception {
        mockMvc.perform(delete("/products/1/"))
               .andExpect(status().isNoContent());

        verify(productRepository, times(1)).delete(1L);
    }

    private Product createTestProduct() {
        return new Product("12345", "Cheese Feast", "Classic mixed cheese",
                           "/images/products/cheese-feast.jpg", new BigDecimal(5.99));
    }
}
