package com.ml.ordermicroservice.repository;

import com.ml.ordermicroservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductsRepository extends JpaRepository<Product, UUID> {
    Product findByProductName(String productName);
}
