package com.ml.caremicroservice.dto;

import lombok.Data;

@Data
public class PackagesDTO {
    private Integer id;
    private String packageName;
    private Double rate;
}
