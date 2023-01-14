package com.ml.ordermicroservice.service;

import com.ml.ordermicroservice.dto.ProductDTO;
import com.ml.ordermicroservice.model.Product;

import java.util.List;

public interface ProductService extends BaseService<Product>{

    Product save(Product product);

    List<Product> fetchAll();

    Product updateProduct(Integer productId, ProductDTO product);

    Boolean deleteProduct(Integer productId);
}
