package com.pm.earphonesdb.mapper;

import com.pm.earphonesdb.dto.EarphoneDriverResponseDTO;
import com.pm.earphonesdb.model.EarphoneDriver;

public class EarphoneDriverMapper {
    public static EarphoneDriverResponseDTO toDTO(EarphoneDriver earphoneDriver) {
        EarphoneDriverResponseDTO earphoneDriverResponseDTO = new EarphoneDriverResponseDTO();
        earphoneDriverResponseDTO.setDriverTypeName(earphoneDriver.getDriverType().getName());
        earphoneDriverResponseDTO.setQuantity(earphoneDriver.getQuantity());
        return earphoneDriverResponseDTO;
    }
}
