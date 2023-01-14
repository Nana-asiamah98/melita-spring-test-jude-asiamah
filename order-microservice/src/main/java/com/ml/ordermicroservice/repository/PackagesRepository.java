package com.ml.ordermicroservice.repository;

import com.ml.ordermicroservice.model.Packages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface PackagesRepository extends JpaRepository<Packages, Integer> {
    @Query("select p from tbl_products_package p where p.packageName like  concat('%',:packageName,'%') ")
    Optional<Packages> findByPackageName(String packageName);
}
