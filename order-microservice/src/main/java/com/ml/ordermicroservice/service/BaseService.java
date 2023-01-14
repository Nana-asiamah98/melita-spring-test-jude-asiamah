package com.ml.ordermicroservice.service;

import java.util.Optional;

public interface BaseService<T> {
    Optional<T> findById(Integer id);
    Optional<T> findByStringValue(String stringValue);
}
