package com.pm.earphonesdb.service;

import com.pm.earphonesdb.dto.DriverType.DriverTypeRequestDTO;
import com.pm.earphonesdb.dto.DriverType.DriverTypeResponseDTO;
import com.pm.earphonesdb.exception.DriverTypeDuplicateException;
import com.pm.earphonesdb.exception.DriverTypeNotFoundException;
import com.pm.earphonesdb.mapper.DriverTypeMapper;
import com.pm.earphonesdb.model.DriverType;
import com.pm.earphonesdb.repository.DriverTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverTypeService {

    private final DriverTypeRepository driverTypeRepository;

    public DriverTypeService(DriverTypeRepository driverTypeRepository) {
        this.driverTypeRepository = driverTypeRepository;
    }

    public List<DriverTypeResponseDTO> getDriverType() {
        List<DriverType> driverType=driverTypeRepository.findAll();
        return driverType.stream().map(DriverTypeMapper::toDTO).toList();
    }

    public DriverTypeResponseDTO getDriverTypeById(Long id){
        DriverType driverType=driverTypeRepository.findById(id).orElseThrow(() -> new DriverTypeNotFoundException("Driver Type Not Found"));
        return DriverTypeMapper.toDTO(driverType);
    }

    public DriverTypeResponseDTO createDriverType(DriverTypeRequestDTO driverTypeRequestDTO){
        DriverType driverType=DriverTypeMapper.toDriverType(driverTypeRequestDTO);

        if (driverTypeRepository.existsByName(
                driverTypeRequestDTO.getDriverType())) {
            throw new DriverTypeDuplicateException("Driver Type already exists");
        }

        DriverType newDriverType=driverTypeRepository.save(driverType);

        return DriverTypeMapper.toDTO(newDriverType);
    }

    @Transactional
    public DriverTypeResponseDTO updateDriverType(Long id,DriverTypeRequestDTO driverTypeRequestDTO){
        DriverType driverType=driverTypeRepository.findById(id).orElseThrow(() -> new DriverTypeNotFoundException("Driver Type Not Found"));

        if(driverTypeRepository.existsByNameAndIdNot(driverTypeRequestDTO.getDriverType(),id)){
            throw new DriverTypeDuplicateException("Driver Type already exists");
        }

        driverType.setName(driverTypeRequestDTO.getDriverType());
        DriverType updatedDriverType = driverTypeRepository.save(driverType);

        return DriverTypeMapper.toDTO(updatedDriverType);
    }

    public void deleteById(Long id) {
        DriverType driverType = driverTypeRepository.findById(id).orElseThrow(() -> new DriverTypeNotFoundException("Driver Type Not Found"));
        driverTypeRepository.delete(driverType);
    }
}
