package com.pm.earphonesdb.service;

import com.pm.earphonesdb.dto.headphones.HeadphoneResponseDTO;
import com.pm.earphonesdb.grpc.SoundSignatureGrpcClient;
import com.pm.earphonesdb.mapper.earphones.EarphoneMapper;
import com.pm.earphonesdb.mapper.headphones.HeadphoneMapper;
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


    public HeadphoneService (HeadphoneRepository headphoneRepository, DriverTypeRepository driverTypeRepository,SoundSignatureGrpcClient soundSignatureGrpcClient,BrandRepository brandRepository){
        this.headphoneRepository=headphoneRepository;
        this.driverTypeRepository=driverTypeRepository;
        this.soundSignatureGrpcClient=soundSignatureGrpcClient;
        this.brandRepository=brandRepository;
    }

    public List<HeadphoneResponseDTO> getHeadphones(){
        List<Headphone> headphones=headphoneRepository.findAll();

        return headphones.stream().map(HeadphoneMapper::toDTO).toList();
    }
}
