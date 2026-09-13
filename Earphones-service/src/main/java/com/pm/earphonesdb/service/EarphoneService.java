package com.pm.earphonesdb.service;

import com.pm.earphonesdb.dto.EarphoneDriverRequestDTO;
import com.pm.earphonesdb.dto.EarphoneRequestDTO;
import com.pm.earphonesdb.dto.EarphoneResponseDTO;
import com.pm.earphonesdb.exception.DriverNotFoundException;
import com.pm.earphonesdb.exception.EarphoneNotFoundException;
import com.pm.earphonesdb.exception.ModelAlreadyExistsException;
import com.pm.earphonesdb.grpc.SoundSignatureGrpcClient;
import com.pm.earphonesdb.mapper.EarphoneMapper;
import com.pm.earphonesdb.model.DriverType;
import com.pm.earphonesdb.model.Earphone;
import com.pm.earphonesdb.model.EarphoneDriver;
import com.pm.earphonesdb.repository.DriverTypeRepository;
import com.pm.earphonesdb.repository.EarphoneRepository;
import org.springframework.stereotype.Service;
import sound_signature.SoundSignatureServiceGrpc;

import java.util.ArrayList;
import java.util.List;

@Service
public class EarphoneService {
    private EarphoneRepository earphoneRepository;
    private DriverTypeRepository driverTypeRepository;
    private SoundSignatureGrpcClient soundSignatureGrpcClient;

    public EarphoneService(EarphoneRepository earphoneRepository, DriverTypeRepository driverTypeRepository,SoundSignatureGrpcClient soundSignatureGrpcClient) {
        this.earphoneRepository = earphoneRepository;
        this.driverTypeRepository = driverTypeRepository;
        this.soundSignatureGrpcClient = soundSignatureGrpcClient;
    }

    //Service layer of get all earphones
    public List<EarphoneResponseDTO> getEarphones() {
        List<Earphone> earphones = earphoneRepository.findAll();

        return earphones.stream().map(EarphoneMapper::toDTO).toList();
    }

    //Service layer of creating a new earphone
    public EarphoneResponseDTO createEarphone(EarphoneRequestDTO earphoneRequestDTO) {

        //lookup in the repository to ensure that the new brand and model does not already exist.
        if (earphoneRepository.existsByBrandAndModel(earphoneRequestDTO.getBrand(), earphoneRequestDTO.getModel())) {
            throw new ModelAlreadyExistsException("This Model: " + earphoneRequestDTO.getBrand() + " "
                    + earphoneRequestDTO.getModel() + " already exists in the database!");
        }

        //save the earphone to the repository
        Earphone earphone = EarphoneMapper.toModel(earphoneRequestDTO);
        earphone.setDrivers(checkEarphoneDrivers(earphone, earphoneRequestDTO));
        Earphone savedEarphone = earphoneRepository.save(earphone);

        return EarphoneMapper.toDTO(earphone);
    }

    //Service Layer of updating existing earphone
    public EarphoneResponseDTO updateEarphone(Long id, EarphoneRequestDTO earphoneRequestDTO) {

        //check if the given earphone id exists in the repository
        Earphone earphone = earphoneRepository.findById(id).orElseThrow(() -> new EarphoneNotFoundException("Earphone Not Found"));

        //checks if the new brand and model exists in the repository
        boolean exists = earphoneRepository.existsByBrandAndModelAndIdNot(
                earphoneRequestDTO.getBrand(), earphoneRequestDTO.getModel(), id);

        if(exists){
            throw new ModelAlreadyExistsException("This Model: " + earphoneRequestDTO.getBrand() + " "
                    + earphoneRequestDTO.getModel() + " already exists in the database!");
        }

        //set the new earphone variables
        earphone.setBrand(earphoneRequestDTO.getBrand());
        earphone.setModel(earphoneRequestDTO.getModel());
        earphone.setMsrp(earphoneRequestDTO.getMsrp());

        //update the new drivers list of the new earphones
        earphone.getDrivers().clear();
        List<EarphoneDriver> updatedDrivers=checkEarphoneDrivers(earphone, earphoneRequestDTO);
        earphone.getDrivers().addAll(updatedDrivers);
        Earphone updatedEarphone=earphoneRepository.save(earphone);
        return EarphoneMapper.toDTO(updatedEarphone);
    }

    //Service Layer of deleting existing earphone
    public void deleteEarphone(Long id) {
        earphoneRepository.deleteById(id);
    }

    public List<EarphoneDriver> checkEarphoneDrivers(Earphone earphone, EarphoneRequestDTO earphoneRequestDTO) {
        List<EarphoneDriver> driverTypes = new ArrayList<>();

        //validates every Earphone Driver DTO in the list
        for (EarphoneDriverRequestDTO driverDTO : earphoneRequestDTO.getDrivers()) {

            //find if the driver type id is valid
            DriverType driverType = driverTypeRepository.findById(driverDTO.getDriverTypeId())
                    .orElseThrow(() -> new DriverNotFoundException("Driver Type Not Found"));

            EarphoneDriver driver = new EarphoneDriver();

            //add each validated driver to the list
            driver.setDriverType(driverType);
            driver.setQuantity(driverDTO.getQuantity());

            driver.setEarphone(earphone);
            driverTypes.add(driver);
        }

        return driverTypes;
    }
}
