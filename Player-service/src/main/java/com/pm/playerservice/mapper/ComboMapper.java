package com.pm.playerservice.mapper;

import com.pm.playerservice.dto.ComboResponseDTO;
import com.pm.playerservice.model.Player;

public class ComboMapper {
    public static ComboResponseDTO toDTO(Player player) {

        ComboResponseDTO comboResponseDTO = new ComboResponseDTO();
        comboResponseDTO.setId(player.getId());
        comboResponseDTO.setBrand(player.getBrand().getName());
        comboResponseDTO.setModel(player.getModel());
        comboResponseDTO.setPlayerType(player.getPlayerType());

        comboResponseDTO.setDacSpec(DacMapper.toDTO(player).getDacSpec());
        comboResponseDTO.setAmpSpec(AmpMapper.toDTO(player).getAmpSpec());

        return comboResponseDTO;
    }
}
