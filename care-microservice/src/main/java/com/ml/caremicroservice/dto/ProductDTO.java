package com.ml.caremicroservice.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductDTO {
    private Integer id;
    private String productName;
    private List<PackagesDTO> packages = new ArrayList<>();
}
