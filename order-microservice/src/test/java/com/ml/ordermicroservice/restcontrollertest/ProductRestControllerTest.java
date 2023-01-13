package com.ml.ordermicroservice.restcontrollertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml.ordermicroservice.model.Packages;
import com.ml.ordermicroservice.model.Product;
import com.ml.ordermicroservice.repository.ProductsRepository;
import com.ml.ordermicroservice.restcontroller.ProductRestController;
import com.ml.ordermicroservice.service.ProductService;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.print.attribute.standard.Media;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductRestController.class)
@ActiveProfiles("test")
public class ProductRestControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    @DisplayName("Should List All Products When Making A GET Request To Endpoint - /api/v1/product/")
    public void shouldListAllProducts() throws Exception{

        Product product1 = sampleProductData();
        Product product2 = sampleProductData();
        List<Product> expectedProducts = Arrays.asList(product1,product2);

        Mockito.when(productService.fetchAll()).thenReturn(expectedProducts);


        mockMvc.perform(get("/api/v1/product/"))
                .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.size()", Matchers.is(2)));
    }

    @Test
    @DisplayName("Should SAVE Products When Making A POST Request To Endpoint - /api/v1/product/")
    public void shouldSaveProduct() throws Exception{
        Product mockProduct = sampleProductData();
        mockMvc.perform(
                post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(mockProduct))
        ).andDo(print())
                .andExpect(status().isOk())
        .andExpect(jsonPath("$.packages", Matchers.hasSize(3)));
    }


    private Product sampleProductData() {
        Product product = new Product();
        List<Integer> samplePackageSize = Arrays.asList(1, 2, 3);
        List<Packages> productPackages = samplePackageSize.stream().map(w -> {
            Packages __package = new Packages();
            __package.setProductId(null);
            __package.setPackageName("Package " + w.toString());
            __package.setRate(100.21);
            __package.setCreatedAt(ZonedDateTime.now());
            __package.setUpdatedAt(ZonedDateTime.now());
            return __package;
        }).collect(Collectors.toList());
        product.setProductName("Product 1");
        product.setId(null);
        product.setPackages(productPackages);
        product.setCreatedAt(ZonedDateTime.now());
        product.setUpdatedAt(ZonedDateTime.now());
        return product;
    }
}

