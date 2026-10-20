package com.pm.earphonesdb.mapper;

import com.pm.earphonesdb.dto.DriverType.DriverTypeRequestDTO;
import com.pm.earphonesdb.dto.DriverType.DriverTypeResponseDTO;
import com.pm.earphonesdb.model.DriverType;

public class DriverTypeMapper {

    public static DriverTypeResponseDTO toDTO(DriverType driverType) {
        DriverTypeResponseDTO driverTypeResponseDTO = new DriverTypeResponseDTO();
        driverTypeResponseDTO.setId(driverType.getId());
        driverTypeResponseDTO.setName(driverType.getName());
        return driverTypeResponseDTO;
    }

    public static DriverType toDriverType(DriverTypeRequestDTO driverTypeRequestDTO){
        DriverType driverType = new DriverType();
        driverType.setName(driverTypeRequestDTO.getDriverType());
        return driverType;
    }
}
