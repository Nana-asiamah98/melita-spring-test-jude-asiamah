package com.ml.ordermicroservice.dto;

import com.ml.ordermicroservice.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderStatusDTO {
    private String userName;
    private String orderStatus;
    private String comment;
    private OrderDTO order;
}
