package com.ml.fulfilmentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstallationAddressDTO {
    private String streetName;
    private String town;
    private String region;
}
