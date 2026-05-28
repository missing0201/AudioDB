package com.pm.earphonesdb.service;

import com.pm.earphonesdb.dto.EarphoneDriverRequestDTO;
import com.pm.earphonesdb.dto.EarphoneRequestDTO;
import com.pm.earphonesdb.dto.EarphoneResponseDTO;
import com.pm.earphonesdb.exception.DriverNotFoundException;
import com.pm.earphonesdb.exception.EarphoneNotFoundException;
import com.pm.earphonesdb.exception.ModelAlreadyExistsException;
import com.pm.earphonesdb.mapper.EarphoneMapper;
import com.pm.earphonesdb.model.DriverType;
import com.pm.earphonesdb.model.Earphone;
import com.pm.earphonesdb.model.EarphoneDriver;
import com.pm.earphonesdb.repository.DriverTypeRepository;
import com.pm.earphonesdb.repository.EarphoneRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EarphoneService {
    private EarphoneRepository earphoneRepository;
    private DriverTypeRepository driverTypeRepository;

    public EarphoneService(EarphoneRepository earphoneRepository, DriverTypeRepository driverTypeRepository) {
        this.earphoneRepository = earphoneRepository;
        this.driverTypeRepository = driverTypeRepository;
    }

    public List<EarphoneResponseDTO> getEarphones() {
        List<Earphone> earphones = earphoneRepository.findAll();

        return earphones.stream().map(EarphoneMapper::toDTO).toList();
    }

    public EarphoneResponseDTO createEarphone(EarphoneRequestDTO earphoneRequestDTO) {

        if (earphoneRepository.existsByBrandAndModel(earphoneRequestDTO.getBrand(), earphoneRequestDTO.getModel())) {
            throw new ModelAlreadyExistsException("This Model: " + earphoneRequestDTO.getBrand() + " "
                    + earphoneRequestDTO.getModel() + " already exists in the database!");
        }

        Earphone earphone = EarphoneMapper.toModel(earphoneRequestDTO);
        earphone.setDrivers(checkEarphoneDrivers(earphone, earphoneRequestDTO));
        Earphone savedEarphone = earphoneRepository.save(earphone);

        return EarphoneMapper.toDTO(earphone);
    }

    public EarphoneResponseDTO updateEarphone(Long id, EarphoneRequestDTO earphoneRequestDTO) {
        Earphone earphone = earphoneRepository.findById(id).orElseThrow(() -> new EarphoneNotFoundException("Earphone Not Found"));

        boolean exists = earphoneRepository.existsByBrandAndModelAndIdNot(
                earphoneRequestDTO.getBrand(), earphoneRequestDTO.getModel(), id);

        if(exists){
            throw new ModelAlreadyExistsException("This Model: " + earphoneRequestDTO.getBrand() + " "
                    + earphoneRequestDTO.getModel() + " already exists in the database!");
        }

        earphone.setBrand(earphoneRequestDTO.getBrand());
        earphone.setModel(earphoneRequestDTO.getModel());
        earphone.setMsrp(earphoneRequestDTO.getMsrp());

        earphone.getDrivers().clear();
        List<EarphoneDriver> updatedDrivers=checkEarphoneDrivers(earphone, earphoneRequestDTO);
        earphone.getDrivers().addAll(updatedDrivers);
        Earphone updatedEarphone=earphoneRepository.save(earphone);
        return EarphoneMapper.toDTO(updatedEarphone);
    }

    public void deleteEarphone(Long id) {
        earphoneRepository.deleteById(id);
    }

    public List<EarphoneDriver> checkEarphoneDrivers(Earphone earphone, EarphoneRequestDTO earphoneRequestDTO) {
        List<EarphoneDriver> driverTypes = new ArrayList<>();

        for (EarphoneDriverRequestDTO driverDTO : earphoneRequestDTO.getDrivers()) {
            DriverType driverType = driverTypeRepository.findById(driverDTO.getDriverTypeId())
                    .orElseThrow(() -> new DriverNotFoundException("Driver Type Not Found"));

            EarphoneDriver driver = new EarphoneDriver();

            driver.setDriverType(driverType);
            driver.setQuantity(driverDTO.getQuantity());

            driver.setEarphone(earphone);
            driverTypes.add(driver);
        }

        return driverTypes;
    }
}
