package com.ml.ordermicroservice.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class ProductDTO {
    private Integer id;
    private String productName;
    private List<PackagesDTO> packages = new ArrayList<>();
}
