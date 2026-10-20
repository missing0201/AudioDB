package com.pm.earphonesdb.service;

import com.pm.earphonesdb.RepositoryFileValidator.BrandRepoValidator;
import com.pm.earphonesdb.RepositoryFileValidator.DriverRepoValidator;
import com.pm.earphonesdb.RepositoryFileValidator.headphones.HeadphoneRepoValidator;
import com.pm.earphonesdb.dto.headphones.HeadphoneDriverRequestDTO;
import com.pm.earphonesdb.dto.headphones.HeadphoneRequestDTO;
import com.pm.earphonesdb.dto.headphones.HeadphoneResponseDTO;
import com.pm.earphonesdb.exception.DriverTypeDuplicateException;
import com.pm.earphonesdb.grpc.SoundSignatureGrpcClient;
import com.pm.earphonesdb.mapper.earphones.EarphoneMapper;
import com.pm.earphonesdb.mapper.headphones.HeadphoneMapper;
import com.pm.earphonesdb.model.Brand;
import com.pm.earphonesdb.model.Driver;
import com.pm.earphonesdb.model.Headphone;
import com.pm.earphonesdb.repository.BrandRepository;
import com.pm.earphonesdb.repository.DriverTypeRepository;
import com.pm.earphonesdb.repository.HeadphoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HeadphoneService {

    private HeadphoneRepository headphoneRepository;
    private DriverTypeRepository driverTypeRepository;
    private SoundSignatureGrpcClient soundSignatureGrpcClient;
    private BrandRepository brandRepository;
    private BrandRepoValidator brandRepoValidator;
    private HeadphoneRepoValidator headphoneRepoValidator;
    private DriverRepoValidator driverRepoValidator;


    public HeadphoneService (HeadphoneRepository headphoneRepository,
                             DriverTypeRepository driverTypeRepository,
                             SoundSignatureGrpcClient soundSignatureGrpcClient,
                             BrandRepository brandRepository,
                             BrandRepoValidator brandRepoValidator,
                             HeadphoneRepoValidator headphoneRepoValidator,
                             DriverRepoValidator driverRepoValidator) {

        this.headphoneRepository=headphoneRepository;
        this.driverTypeRepository=driverTypeRepository;
        this.soundSignatureGrpcClient=soundSignatureGrpcClient;
        this.brandRepository=brandRepository;
        this.brandRepoValidator=brandRepoValidator;
        this.headphoneRepoValidator=headphoneRepoValidator;
        this.driverRepoValidator=driverRepoValidator;
    }

    public List<HeadphoneResponseDTO> getHeadphones(){
        List<Headphone> headphones=headphoneRepository.findAll();

        return headphones.stream().map(HeadphoneMapper::toDTO).toList();
    }

    public HeadphoneResponseDTO getHeadphone(Long id) {
        Headphone headphone=headphoneRepoValidator.findHeadphoneById(id);
        return HeadphoneMapper.toDTO(headphone);
    }

    @Transactional
    public  HeadphoneResponseDTO createHeadphone(HeadphoneRequestDTO headphoneRequestDTO){

        Brand brand= brandRepoValidator.validateBrand(headphoneRequestDTO);
        headphoneRepoValidator.brandAndModelExist(brand,headphoneRequestDTO.getModel());
        Headphone headphone=HeadphoneMapper.toModel(headphoneRequestDTO);

        headphone.setBrand(brand);
        syncDrivers(headphone,headphoneRequestDTO);

        return HeadphoneMapper.toDTO(headphoneRepository.save(headphone));
    }

    @Transactional
    public HeadphoneResponseDTO updateHeadphone(Long id, HeadphoneRequestDTO headphoneRequestDTO) {

        Headphone headphone=headphoneRepoValidator.findHeadphoneById(id);

        Brand brand=brandRepoValidator.validateBrand(headphoneRequestDTO);
        headphoneRepoValidator.brandAndModelAndIdNotExist(brand,headphoneRequestDTO.getModel(),id);

        headphone.setBrand(brand);
        headphone.setModel(headphoneRequestDTO.getModel());
        headphone.setMsrp(headphoneRequestDTO.getMsrp());
        syncDrivers(headphone, headphoneRequestDTO);

        return HeadphoneMapper.toDTO(headphoneRepository.save(headphone));

    }

    public void deleteHeadphone(Long id) {
        headphoneRepository.delete(headphoneRepoValidator.findHeadphoneById(id));
    }

    private void syncDrivers(Headphone headphone, HeadphoneRequestDTO dto) {

        Map<Long, Driver> existing = headphone.getDrivers().stream()
                .collect(Collectors.toMap(
                        d -> d.getDriverType().getId(),
                        d -> d
                ));

        Set<Long> incomingTypeIds = new HashSet<>();

        for (HeadphoneDriverRequestDTO driverDTO : dto.getDrivers()) {
            Long typeId = driverDTO.getDriverTypeId();

            if (!incomingTypeIds.add(typeId)) {
                throw new DriverTypeDuplicateException(
                        "Duplicate driver type in request: " + typeId
                );
            }

            Driver driver = existing.get(typeId);

            if (driver != null) {
                driver.setQuantity(driverDTO.getQuantity());
            } else {
                Driver newDriver = new Driver();
                newDriver.setDriverType(
                        driverRepoValidator.FindDriverTypeById(typeId)
                );
                newDriver.setQuantity(driverDTO.getQuantity());
                newDriver.setHeadphone(headphone);
                headphone.getDrivers().add(newDriver);
            }
        }

        headphone.getDrivers().removeIf(
                d -> !incomingTypeIds.contains(d.getDriverType().getId())
        );
    }
}
