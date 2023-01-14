package com.ml.ordermicroservice.serviceimpl;

import com.ml.ordermicroservice.model.Packages;
import com.ml.ordermicroservice.repository.PackagesRepository;
import com.ml.ordermicroservice.service.PackageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PackageServiceImpl implements PackageService {

    private final PackagesRepository packagesRepository;

    @Override
    public Optional<Packages> findById(Integer id) {
        return packagesRepository.findById(id);
    }

    @Override
    public Optional<Packages> findByStringValue(String stringNumber) {
        return packagesRepository.findByPackageName(stringNumber);
    }
}
