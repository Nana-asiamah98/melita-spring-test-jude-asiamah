package com.ml.ordermicroservice.respository;

import com.ml.ordermicroservice.model.Packages;
import com.ml.ordermicroservice.model.Product;
import com.ml.ordermicroservice.repository.ProductsRepository;
import com.ml.ordermicroservice.service.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.bouncycastle.util.Pack;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest extends BaseTest {
    @Autowired
    private ProductsRepository productsRepository;

    @Test
    @DisplayName("Should Save Product")
    public void shouldSaveProduct() {
        Product expectedProductObject = sampleData();
        Product actualProductObject = productsRepository.save(expectedProductObject);
        assertThat(actualProductObject).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedProductObject);
    }


    @Test
    @DisplayName("Should Fetch Empty Lists Products")
    public void shouldFetchEmptyProductList() {
        List<Product> products = productsRepository.findAll();
        Assertions.assertTrue(products.isEmpty());
    }

    @Test
    @DisplayName("Should Fetch List Of Products")
    public void shouldFetchProductList(){
        Product expectedProductObject = sampleData();
        Product actualProductObject = productsRepository.save(expectedProductObject);
        List<Product> products = productsRepository.findAll();
        Assertions.assertFalse(products.isEmpty());
    }

    private Product sampleData() {
        Product product = new Product();
        List<Integer> samplePackageSize = Arrays.asList(1, 2, 3);
        List<Packages> productPackages = samplePackageSize.stream().map(w -> {
            Packages __package = new Packages();
            __package.setProductId(null);
            __package.setPackageName("Package " + String.valueOf(Math.random()));
            __package.setRate(100.21);
            __package.setCreatedAt(ZonedDateTime.now());
            __package.setUpdatedAt(ZonedDateTime.now());
            return __package;
        }).collect(Collectors.toList());
        product.setProductName("Product " + String.valueOf(Math.random()));
        product.setId(null);
        product.setPackages(productPackages);
        product.setCreatedAt(ZonedDateTime.now());
        product.setUpdatedAt(ZonedDateTime.now());
        return product;
    }


}
