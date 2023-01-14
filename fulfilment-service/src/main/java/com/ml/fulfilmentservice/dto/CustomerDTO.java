package com.ml.fulfilmentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private UUID id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String address;
}
