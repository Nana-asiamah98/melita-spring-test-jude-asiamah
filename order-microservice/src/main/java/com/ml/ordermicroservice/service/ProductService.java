package com.ml.ordermicroservice.service;

import com.ml.ordermicroservice.dto.PaginatedProductResponse;
import com.ml.ordermicroservice.dto.ProductDTO;
import com.ml.ordermicroservice.model.Product;

import java.util.List;

public interface ProductService extends BaseService<Product>{

    Product save(ProductDTO product);

    List<Product> fetchAll();

    PaginatedProductResponse fetchPaginatedResults(int page, int size, String sortBy, String sortDir);

    Product updateProduct(Integer productId, ProductDTO product);

    Boolean deleteProduct(Integer productId);
}
