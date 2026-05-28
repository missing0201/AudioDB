package com.pm.earphonesdb.mapper;

import com.pm.earphonesdb.dto.EarphoneDriverResponseDTO;
import com.pm.earphonesdb.dto.EarphoneRequestDTO;
import com.pm.earphonesdb.dto.EarphoneResponseDTO;
import com.pm.earphonesdb.model.Earphone;
import com.pm.earphonesdb.model.EarphoneDriver;

import java.util.ArrayList;
import java.util.List;

public class EarphoneMapper {
    public static EarphoneResponseDTO toDTO(Earphone earphone){
        EarphoneResponseDTO earphoneDTO = new EarphoneResponseDTO();

        earphoneDTO.setId(earphone.getId());
        earphoneDTO.setBrand(earphone.getBrand());
        earphoneDTO.setModel(earphone.getModel());
        earphoneDTO.setMsrp(earphone.getMsrp());

        List<EarphoneDriverResponseDTO> earphoneDrivers = new ArrayList<>();
        for(EarphoneDriver driver : earphone.getDrivers()){
            EarphoneDriverResponseDTO earphoneDriverResponseDTO = new EarphoneDriverResponseDTO();
            earphoneDriverResponseDTO.setDriverTypeName(driver.getDriverType().getName());
            earphoneDriverResponseDTO.setQuantity(driver.getQuantity());
            earphoneDrivers.add(earphoneDriverResponseDTO);
        }

        earphoneDTO.setDrivers(earphoneDrivers);

        return earphoneDTO;
    }

    public static Earphone toModel(EarphoneRequestDTO earphoneRequestDTO){
        Earphone earphone = new Earphone();
        earphone.setBrand(earphoneRequestDTO.getBrand());
        earphone.setModel(earphoneRequestDTO.getModel());
        earphone.setMsrp(earphoneRequestDTO.getMsrp());
        return earphone;
    }
}
