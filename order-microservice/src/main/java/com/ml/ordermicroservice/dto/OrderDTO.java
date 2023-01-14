package com.ml.ordermicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private UUID id;
    private CustomerDTO customer;
    private InstallationAddressDTO installationAddress;
    private List<OrderItemDTO> orderItem;
    private String orderNumber;
    private Double totalAmount;
    private String orderStatus;
    private String notes;


}
