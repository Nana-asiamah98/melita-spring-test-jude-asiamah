package com.ml.ordermicroservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PackagesDTO {
    private UUID id;
    private String packageName;
    private Double rate;
}
