package com.pm.earphonesdb.RepositoryFileValidator.earphones;

import com.pm.earphonesdb.exception.EarphoneNotFoundException;
import com.pm.earphonesdb.exception.ModelAlreadyExistsException;
import com.pm.earphonesdb.model.Brand;
import com.pm.earphonesdb.model.Earphone;
import com.pm.earphonesdb.repository.EarphoneRepository;
import org.springframework.stereotype.Component;

@Component
public class EarphoneRepoValidator {
    private EarphoneRepository earphoneRepository;
    private EarphoneRepoValidator(EarphoneRepository earphoneRepository) {
        this.earphoneRepository = earphoneRepository;
    }

    public void brandAndModelExist(Brand brand, String model){

        if (earphoneRepository.existsByBrandAndModelIgnoreCase(brand, model)) {
            throw new ModelAlreadyExistsException("This Model: " + brand.getName() + " "
                    + model+ " already exists in the database!");
        }

    }

    public void brandAndModelAndIdNotExist(Brand brand, String model, Long id){
        boolean exists = earphoneRepository.existsByBrandAndModelIgnoreCaseAndIdNot(
                brand, model, id);

        if(exists){
            throw new ModelAlreadyExistsException("This Model: " + brand.getName() + " "
                    + model + " already exists in the database!");
        }
    }

    public Earphone findEarphoneById(Long id){
        return earphoneRepository.findById(id).orElseThrow(() -> new EarphoneNotFoundException("Earphone Not Found"));
    }
}
