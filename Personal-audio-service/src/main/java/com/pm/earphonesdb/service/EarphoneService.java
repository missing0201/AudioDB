package com.pm.earphonesdb.service;

import com.pm.earphonesdb.RepositoryFileValidator.BrandRepoValidator;
import com.pm.earphonesdb.RepositoryFileValidator.DriverRepoValidator;
import com.pm.earphonesdb.RepositoryFileValidator.earphones.EarphoneRepoValidator;
import com.pm.earphonesdb.dto.*;
import com.pm.earphonesdb.dto.earphones.EarphoneDriverRequestDTO;
import com.pm.earphonesdb.dto.earphones.EarphoneRequestDTO;
import com.pm.earphonesdb.dto.earphones.EarphoneResponseDTO;
import com.pm.earphonesdb.grpc.SoundSignatureGrpcClient;
import com.pm.earphonesdb.mapper.earphones.EarphoneMapper;
import com.pm.earphonesdb.mapper.SignatureMapper;
import com.pm.earphonesdb.model.Brand;
import com.pm.earphonesdb.model.Earphone;
import com.pm.earphonesdb.model.Driver;
import com.pm.earphonesdb.repository.DriverTypeRepository;
import com.pm.earphonesdb.repository.EarphoneRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import sound_signature.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EarphoneService {
    private EarphoneRepository earphoneRepository;
    private DriverTypeRepository driverTypeRepository;
    private SoundSignatureGrpcClient soundSignatureGrpcClient;
    private BrandRepoValidator brandRepoValidator;
    private EarphoneRepoValidator earphoneRepoValidator;
    private DriverRepoValidator driverRepoValidator;

    public EarphoneService(
                EarphoneRepository earphoneRepository,
                DriverTypeRepository driverTypeRepository,
                SoundSignatureGrpcClient soundSignatureGrpcClient,
                BrandRepoValidator brandRepoValidator,
                EarphoneRepoValidator earphoneRepoValidator,
                DriverRepoValidator driverRepoValidator) {
        this.earphoneRepository = earphoneRepository;
        this.driverTypeRepository = driverTypeRepository;
        this.soundSignatureGrpcClient = soundSignatureGrpcClient;
        this.brandRepoValidator = brandRepoValidator;
        this.earphoneRepoValidator = earphoneRepoValidator;
        this.driverRepoValidator = driverRepoValidator;
    }

    public SignatureResponseDTO getSignature(String id){
        GetSignatureResponse response =
                soundSignatureGrpcClient.getSignature(id);

        var signature = response.getSignature();
        return SignatureMapper.toDTO(signature);
    }

    //get all earphones
    public List<EarphoneResponseDTO> getEarphones() {
        List<Earphone> earphones = earphoneRepository.findAll();

        return earphones.stream().map(EarphoneMapper::toDTO).toList();
    }

    public EarphoneResponseDTO getEarphone(Long id) {
        Earphone earphone=earphoneRepoValidator.findEarphoneById(id);
        return EarphoneMapper.toDTO(earphone);
    }

    //creating a new earphone
    public EarphoneResponseDTO createEarphone(EarphoneRequestDTO earphoneRequestDTO) {

        Brand brand= brandRepoValidator.validateBrand(earphoneRequestDTO);

        //lookup in the repository to ensure that the new brand and model does not already exist.
        earphoneRepoValidator.brandAndModelExist(brand, earphoneRequestDTO.getModel());

        //save the earphone to the repository
        Earphone earphone = EarphoneMapper.toModel(earphoneRequestDTO);
        earphone.setBrand(brand);
        earphone.setDrivers(checkEarphoneDrivers(earphone, earphoneRequestDTO));
        Earphone savedEarphone = earphoneRepository.save(earphone);

        return EarphoneMapper.toDTO(savedEarphone);
    }

    //updating existing earphone
    @Transactional
    public EarphoneResponseDTO updateEarphone(Long id, EarphoneRequestDTO earphoneRequestDTO) {

        //check if the given earphone id exists in the repository
        Earphone earphone = earphoneRepoValidator.findEarphoneById(id);

        Brand brand= brandRepoValidator.validateBrand(earphoneRequestDTO);

        //checks if the new brand and model exists in the repository
        earphoneRepoValidator.brandAndModelAndIdNotExist(brand, earphoneRequestDTO.getModel(), id);

        //set the new earphone variables
        earphone.setBrand(brand);
        earphone.setModel(earphoneRequestDTO.getModel());
        earphone.setMsrp(earphoneRequestDTO.getMsrp());

        syncDrivers(earphone, earphoneRequestDTO);

        Earphone updatedEarphone = earphoneRepository.save(earphone);
        return EarphoneMapper.toDTO(updatedEarphone);
    }

    public SignatureResponseDTO updateEarphoneSignature(String id,SignatureRequestDTO signatureRequestDTO) {

        UpdateSignatureResponse response=soundSignatureGrpcClient.updateSignature(SignatureMapper.toSignature(id,signatureRequestDTO));

        return SignatureMapper.toDTO(response.getSignature());

    }

    //deleting existing earphone
    public void deleteEarphone(Long id) {
        earphoneRepository.deleteById(id);
    }

    public List<Driver> checkEarphoneDrivers(Earphone earphone, EarphoneRequestDTO earphoneRequestDTO) {
        List<Driver> driverTypes = new ArrayList<>();

        //validates every Earphone Driver DTO in the list
        for (EarphoneDriverRequestDTO driverDTO : earphoneRequestDTO.getDrivers()) {

            Driver driver = new Driver();

            //add each validated driver to the list
            driver.setDriverType(driverRepoValidator.FindDriverTypeById(driverDTO.getDriverTypeId()));
            driver.setQuantity(driverDTO.getQuantity());

            driver.setEarphone(earphone);
            driverTypes.add(driver);
        }

        return driverTypes;
    }

    private void syncDrivers(Earphone earphone, EarphoneRequestDTO dto) {
        // Existing drivers keyed by driver type id
        Map<Long, Driver> existing = earphone.getDrivers().stream()
                .collect(Collectors.toMap(d -> d.getDriverType().getId(), d -> d));

        Set<Long> incomingTypeIds = new HashSet<>();

        for (EarphoneDriverRequestDTO driverDTO : dto.getDrivers()) {
            Long typeId = driverDTO.getDriverTypeId();

            if (!incomingTypeIds.add(typeId)) {
                // same driver type listed twice in the request -> would violate the unique constraint
                throw new IllegalArgumentException("Duplicate driver type in request: " + typeId);
            }

            Driver driver = existing.get(typeId);
            if (driver != null) {
                // update in place
                driver.setQuantity(driverDTO.getQuantity());
            } else {
                // new driver type for this earphone
                Driver newDriver = new Driver();
                newDriver.setDriverType(driverRepoValidator.FindDriverTypeById(typeId));
                newDriver.setQuantity(driverDTO.getQuantity());
                newDriver.setEarphone(earphone);
                earphone.getDrivers().add(newDriver);
            }
        }

        // remove drivers whose type is no longer in the request (orphanRemoval deletes them)
        earphone.getDrivers().removeIf(d -> !incomingTypeIds.contains(d.getDriverType().getId()));
    }
}
