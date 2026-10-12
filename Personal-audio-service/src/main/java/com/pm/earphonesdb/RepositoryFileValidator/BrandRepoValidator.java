package com.pm.earphonesdb.RepositoryFileValidator;

import com.pm.earphonesdb.dto.earphones.EarphoneRequestDTO;
import com.pm.earphonesdb.dto.headphones.HeadphoneRequestDTO;
import com.pm.earphonesdb.exception.BrandNotFoundException;
import com.pm.earphonesdb.model.Brand;
import com.pm.earphonesdb.repository.BrandRepository;
import org.springframework.stereotype.Component;

@Component
public class BrandRepoValidator {

    private BrandRepository brandRepository;

    public BrandRepoValidator(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public Brand validateBrand(EarphoneRequestDTO earphoneRequestDTO){
        Brand brand = brandRepository.findById(earphoneRequestDTO.getBrandId())
                .orElseThrow(() -> new BrandNotFoundException("Brand not found"));

        return brand;
    }

    public Brand validateBrand(HeadphoneRequestDTO headphoneRequestDTO){
        Brand brand = brandRepository.findById(headphoneRequestDTO.getBrandId())
                .orElseThrow(() -> new BrandNotFoundException("Brand not found"));

        return brand;
    }

}
