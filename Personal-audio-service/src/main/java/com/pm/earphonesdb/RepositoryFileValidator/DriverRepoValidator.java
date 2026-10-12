package com.pm.earphonesdb.RepositoryFileValidator;

import com.pm.earphonesdb.exception.DriverNotFoundException;
import com.pm.earphonesdb.model.DriverType;
import com.pm.earphonesdb.repository.DriverTypeRepository;
import org.springframework.stereotype.Component;

@Component
public class DriverRepoValidator {
    private DriverTypeRepository driverTypeRepository;
    public DriverRepoValidator(DriverTypeRepository driverTypeRepository) {
        this.driverTypeRepository = driverTypeRepository;
    }

    public DriverType FindDriverTypeById(Long id){
        return driverTypeRepository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException("Driver Type Not Found"));
    }

}
