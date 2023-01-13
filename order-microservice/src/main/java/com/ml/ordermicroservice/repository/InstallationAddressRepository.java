package com.ml.ordermicroservice.repository;

import com.ml.ordermicroservice.model.InstallationAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InstallationAddressRepository extends JpaRepository<InstallationAddress, UUID> {
}
