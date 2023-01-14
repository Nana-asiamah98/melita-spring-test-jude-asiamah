package com.ml.ordermicroservice.utils.validators.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class BaseValidator {

    protected String requestId;
    protected Map<String, Object> errors = new HashMap<>();
    protected ObjectMapper mapper = new ObjectMapper();

    public BaseValidator(String requestId) {
        this.requestId = requestId;
    }

    public List<ErrorResponse> errors() {
        return this.errors
                .entrySet()
                .stream()
                .map(entry -> {
                    ErrorResponse error = new ErrorResponse();
                    error.setField(entry.getKey());
                    error.setMessage(entry.getValue());
                    return error;
                }).collect(Collectors.toList());
    }

    protected String writePayloadAsString(Object object){
        try {
            return this.mapper.writeValueAsString(object);
        }catch (Exception e){
            return "{}";
        }
    }
}
