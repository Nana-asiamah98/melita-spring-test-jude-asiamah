package com.ml.ordermicroservice.repository;

import com.ml.ordermicroservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ProductsRepository extends JpaRepository<Product, Integer> {
    @Query("select p from tbl_products p where p.productName like  concat('%',:productName,'%') ")
    Optional<Product> findByProductName(String productName);

}
