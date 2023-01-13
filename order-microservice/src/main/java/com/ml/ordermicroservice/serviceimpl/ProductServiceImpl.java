package com.ml.ordermicroservice.serviceimpl;

import com.ml.ordermicroservice.dto.OrderDTO;
import com.ml.ordermicroservice.dto.PackagesDTO;
import com.ml.ordermicroservice.dto.ProductDTO;
import com.ml.ordermicroservice.events.OrderEvent;
import com.ml.ordermicroservice.model.Packages;
import com.ml.ordermicroservice.model.Product;
import com.ml.ordermicroservice.repository.ProductsRepository;
import com.ml.ordermicroservice.service.ProductService;
import com.ml.ordermicroservice.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductsRepository productsRepository;
    private final ApplicationEventPublisher eventPublisher;


    @Override
    public Product save(Product product) {

        Product savedProduct =  productsRepository.save(product);
        eventPublisher.publishEvent(new OrderEvent(this, AppConstants.PRODUCT_CREATED,new OrderDTO()));
        log.info("[PRODUCT SAVED]");
        return  savedProduct;
    }

    @Override
    public Product fetch(UUID productId) {
        return productsRepository.findById(productId).orElseGet(Product::new);
    }

    @Override
    public List<Product> fetchAll() {
        return productsRepository.findAll();
    }

    @Override
    public Product updateProduct(UUID productId, ProductDTO product) {
        Optional<Product> isProduct = productsRepository.findById(productId);
        if (isProduct.isPresent()) {
            Product __mainProduct = isProduct.get();
            __mainProduct.setProductName(product.getProductName());
            __mainProduct.setPackages(getPackages(product.getPackages()));
            return productsRepository.save(__mainProduct);

        }
        return new Product();
    }

    @Override
    public Boolean deleteProduct(UUID productId) {
        boolean isExist = productsRepository.existsById(productId);
        if (isExist) {
            productsRepository.deleteById(productId);
            return true;
        }
        return false;
    }

    private List<Packages> getPackages(List<PackagesDTO> packagesDTOS) {
        if (packagesDTOS.isEmpty()) {
            return new ArrayList<Packages>();
        }
        return packagesDTOS.stream().map(w -> {
            Packages packages = new Packages();
            packages.setPackageName(w.getPackageName());
            packages.setRate(w.getRate());
            return packages;
        }).collect(Collectors.toList());
    }
}

