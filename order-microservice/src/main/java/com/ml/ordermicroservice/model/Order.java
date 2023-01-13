package com.ml.ordermicroservice.model;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity(name = "tbl_order")
@Data
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "order_number")
    private String orderNumber;

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    @JoinColumn(name = "customer_detail_id")
    private Customer customerDetails;

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    @JoinColumn(name = "installation_detail_id")
    private InstallationAddress installationAddress;

    @Column(name = "notes")
    private String notes;

    @Column(name = "total")
    private String totalAmount;

    @Column(name = "order_status")
    private String orderStatus;

    @CreationTimestamp
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    private ZonedDateTime updatedAt;
}
