package com.ml.ordermicroservice.service;

import com.ml.ordermicroservice.dto.ProductDTO;
import com.ml.ordermicroservice.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    Product save(Product product);

    Product fetch(UUID productId);

    List<Product> fetchAll();

    Product updateProduct(UUID productId, ProductDTO product);

    Boolean deleteProduct(UUID productId);
}
