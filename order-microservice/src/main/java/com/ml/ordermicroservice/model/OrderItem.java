package com.ml.ordermicroservice.model;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "tbl_order_item")
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "package_name")
    private String packageName;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "amount")
    private Double amount;
}
