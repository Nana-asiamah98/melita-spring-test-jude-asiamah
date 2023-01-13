package com.ml.ordermicroservice.model;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "tbl_order")
@Data
@ToString
public class Order implements Serializable {

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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "order_number", referencedColumnName = "order_number")
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(name = "notes")
    private String notes;

    @Column(name = "total")
    private Double totalAmount;

    @Column(name = "order_status")
    private String orderStatus;

    @CreationTimestamp
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    private ZonedDateTime updatedAt;
}
