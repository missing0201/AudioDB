package com.pm.earphonesdb.mapper.headphones;

import com.pm.earphonesdb.dto.headphones.HeadphoneDriverResponseDTO;
import com.pm.earphonesdb.dto.headphones.HeadphoneRequestDTO;
import com.pm.earphonesdb.dto.headphones.HeadphoneResponseDTO;
import com.pm.earphonesdb.model.Driver;
import com.pm.earphonesdb.model.Headphone;

import java.util.ArrayList;
import java.util.List;

public class HeadphoneMapper {

    public static HeadphoneResponseDTO toDTO(Headphone headphone){
        HeadphoneResponseDTO headphoneDTO = new HeadphoneResponseDTO();
        headphoneDTO.setId(headphone.getId());
        headphoneDTO.setBrandId(headphone.getBrand().getId());
        headphoneDTO.setBrand(headphone.getBrand().getName());
        headphoneDTO.setModel(headphone.getModel());
        headphoneDTO.setMsrp(headphone.getMsrp());

        List<HeadphoneDriverResponseDTO> headphoneDrivers = new ArrayList<>();
        for(Driver driver : headphone.getDrivers()){
            HeadphoneDriverResponseDTO headphoneDriverDTO = new HeadphoneDriverResponseDTO();
            headphoneDriverDTO.setDriverTypeName(driver.getDriverType().getName());
            headphoneDriverDTO.setQuantity(driver.getQuantity());
            headphoneDrivers.add(headphoneDriverDTO);
        }

        headphoneDTO.setDrivers(headphoneDrivers);
        return headphoneDTO;
    }

    public static Headphone toModel(HeadphoneRequestDTO headphoneRequestDTO){
        Headphone headphone = new Headphone();
        headphone.setModel(headphoneRequestDTO.getModel());
        headphone.setMsrp(headphoneRequestDTO.getMsrp());
        return headphone;
    }
}
