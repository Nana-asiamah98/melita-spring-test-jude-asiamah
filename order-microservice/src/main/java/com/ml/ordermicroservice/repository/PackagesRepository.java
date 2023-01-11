package com.ml.ordermicroservice.repository;

import com.ml.ordermicroservice.model.Packages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PackagesRepository extends JpaRepository<Packages, UUID> {
    Optional<Packages> findByPackageName(String packageName);
}
