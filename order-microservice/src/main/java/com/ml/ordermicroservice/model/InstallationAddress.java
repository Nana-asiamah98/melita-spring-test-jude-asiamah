package com.ml.ordermicroservice.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity(name = "tbl_installation_address")
@Data
public class InstallationAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "town")
    private String town;

    @Column(name = "region")
    private String region;

    @CreationTimestamp
    @Column(name = "created_at")
    private ZonedDateTime createdAt;
}
