package com.pm.earphonesdb.service;

import com.pm.earphonesdb.RepositoryFileValidator.BrandRepoValidator;
import com.pm.earphonesdb.RepositoryFileValidator.headphones.HeadphoneRepoValidator;
import com.pm.earphonesdb.dto.headphones.HeadphoneRequestDTO;
import com.pm.earphonesdb.dto.headphones.HeadphoneResponseDTO;
import com.pm.earphonesdb.grpc.SoundSignatureGrpcClient;
import com.pm.earphonesdb.mapper.earphones.EarphoneMapper;
import com.pm.earphonesdb.mapper.headphones.HeadphoneMapper;
import com.pm.earphonesdb.model.Brand;
import com.pm.earphonesdb.model.Headphone;
import com.pm.earphonesdb.repository.BrandRepository;
import com.pm.earphonesdb.repository.DriverTypeRepository;
import com.pm.earphonesdb.repository.HeadphoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeadphoneService {

    private HeadphoneRepository headphoneRepository;
    private DriverTypeRepository driverTypeRepository;
    private SoundSignatureGrpcClient soundSignatureGrpcClient;
    private BrandRepository brandRepository;
    private BrandRepoValidator brandRepoValidator;
    private HeadphoneRepoValidator headphoneRepoValidator;


    public HeadphoneService (HeadphoneRepository headphoneRepository,
                             DriverTypeRepository driverTypeRepository,
                             SoundSignatureGrpcClient soundSignatureGrpcClient,
                             BrandRepository brandRepository,
                             BrandRepoValidator brandRepoValidator,
                             HeadphoneRepoValidator headphoneRepoValidator) {
        this.headphoneRepository=headphoneRepository;
        this.driverTypeRepository=driverTypeRepository;
        this.soundSignatureGrpcClient=soundSignatureGrpcClient;
        this.brandRepository=brandRepository;
        this.brandRepoValidator=brandRepoValidator;
        this.headphoneRepoValidator=headphoneRepoValidator;
    }

    public List<HeadphoneResponseDTO> getHeadphones(){
        List<Headphone> headphones=headphoneRepository.findAll();

        return headphones.stream().map(HeadphoneMapper::toDTO).toList();
    }

    public  HeadphoneResponseDTO createHeadphone(HeadphoneRequestDTO headphoneRequestDTO){

        Headphone headphone=HeadphoneMapper.toModel(headphoneRequestDTO);
        Brand brand= brandRepoValidator.validateBrand(headphoneRequestDTO);

        headphone.setBrand(brand);
        headphoneRepository.save(headphone);

        return HeadphoneMapper.toDTO(headphone);
    }

    public HeadphoneResponseDTO getHeadphone(Long id) {
        Headphone headphone=headphoneRepoValidator.findHeadphoneById(id);
        return HeadphoneMapper.toDTO(headphone);
    }

    public void deleteHeadphone(Long id) {
        headphoneRepository.deleteById(id);
    }
}
