package com.ml.fulfilmentservice.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderDTO {

    private UUID id;
    private CustomerDTO customer;
    private InstallationAddressDTO installationAddress;
    private List<OrderItemDTO> orderItem;
    private String orderNumber;
    private Double totalAmount;
    private String orderState;

}
