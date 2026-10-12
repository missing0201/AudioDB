package com.pm.earphonesdb.RepositoryFileValidator.headphones;

import com.pm.earphonesdb.exception.EarphoneNotFoundException;
import com.pm.earphonesdb.exception.HeadphoneNotFoundException;
import com.pm.earphonesdb.exception.ModelAlreadyExistsException;
import com.pm.earphonesdb.model.Brand;
import com.pm.earphonesdb.model.Earphone;
import com.pm.earphonesdb.model.Headphone;
import com.pm.earphonesdb.repository.EarphoneRepository;
import com.pm.earphonesdb.repository.HeadphoneRepository;
import org.springframework.stereotype.Component;

@Component
public class HeadphoneRepoValidator {
    private HeadphoneRepository headphoneRepository;

    private HeadphoneRepoValidator(HeadphoneRepository headphoneRepository) {
        this.headphoneRepository = headphoneRepository;
    }

    public void brandAndModelExist(Brand brand,String model){

        if (headphoneRepository.existsByBrandAndModelIgnoreCase(brand, model)) {
            throw new ModelAlreadyExistsException("This Model: " + brand.getName() + " "
                    + model+ " already exists in the database!");
        }

    }

    public void brandAndModelAndIdNotExist(Brand brand,String model,Long id){
        boolean exists = headphoneRepository.existsByBrandAndModelIgnoreCaseAndIdNot(
                brand, model, id);

        if(exists){
            throw new ModelAlreadyExistsException("This Model: " + brand.getName() + " "
                    + model + " already exists in the database!");
        }
    }

    public Headphone findHeadphoneById(Long id){
        return headphoneRepository.findById(id).orElseThrow(() -> new HeadphoneNotFoundException("Headphone Not Found"));
    }
}
