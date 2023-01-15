package com.ml.ordermicroservice.serviceimpl;

import com.ml.ordermicroservice.dto.PackagesDTO;
import com.ml.ordermicroservice.dto.PaginatedProductResponse;
import com.ml.ordermicroservice.dto.ProductDTO;
import com.ml.ordermicroservice.model.Packages;
import com.ml.ordermicroservice.model.Product;
import com.ml.ordermicroservice.repository.ProductsRepository;
import com.ml.ordermicroservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductsRepository productsRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final ModelMapper modelMapper;


    @Override
    public Product save(ProductDTO product) {
        Product product1 = modelMapper.map(product, Product.class);
        return productsRepository.save(product1);
    }

    @Override
    public List<Product> fetchAll() {
        return productsRepository.findAll();
    }

    @Override
    public PaginatedProductResponse fetchPaginatedResults(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> productPage = productsRepository.findAll(pageable);

        return productDTOPaginatedObjectResponse(productPage);
    }

    @Override
    public Product updateProduct(Integer productId, ProductDTO product) {
        Optional<Product> isProduct = productsRepository.findById(productId);
        if (isProduct.isPresent()) {
            Product __mainProduct = isProduct.get();
            __mainProduct.setProductName(product.getProductName());
            __mainProduct.getPackages().clear();
            __mainProduct.getPackages().addAll(getPackages(product.getPackages()));
            return productsRepository.save(__mainProduct);

        }
        return new Product();
    }

    @Override
    public Boolean deleteProduct(Integer productId) {
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
            Packages packages = modelMapper.map(w, Packages.class);
            packages.setPackageName(w.getPackageName());
            packages.setRate(w.getRate());
            return packages;
        }).collect(Collectors.toList());
    }


    @Override
    public Optional<Product> findById(Integer id) {
        return productsRepository.findById(id);
    }

    @Override
    public Optional<Product> findByStringValue(String stringValue) {
        return productsRepository.findByProductName(stringValue);
    }

    private PaginatedProductResponse productDTOPaginatedObjectResponse(Page<Product> productPage) {
        List<ProductDTO> productDTO = productPage.getContent().stream().map(w ->
        {
            ProductDTO __mappedProductDTO = modelMapper.map(w, ProductDTO.class);
            List<PackagesDTO> packagesDTOList = __mappedProductDTO.getPackages().stream().map(l -> modelMapper.map(l, PackagesDTO.class)).collect(Collectors.toList());
            __mappedProductDTO.setPackages(packagesDTOList);
            return __mappedProductDTO;
        }).collect(Collectors.toList());
        return PaginatedProductResponse
                .builder()
                .content(productDTO)
                .last(productPage.isLast())
                .pageNo(productPage.getNumber())
                .pageSize(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .build();
    }
}

