package com.pm.earphonesdb.mapper.earphones;

import com.pm.earphonesdb.dto.earphones.EarphoneDriverResponseDTO;
import com.pm.earphonesdb.model.Driver;

public class EarphoneDriverMapper {
    public static EarphoneDriverResponseDTO toDTO(Driver driver) {
        EarphoneDriverResponseDTO earphoneDriverResponseDTO = new EarphoneDriverResponseDTO();
        earphoneDriverResponseDTO.setDriverTypeName(driver.getDriverType().getName());
        earphoneDriverResponseDTO.setQuantity(driver.getQuantity());
        return earphoneDriverResponseDTO;
    }
}
