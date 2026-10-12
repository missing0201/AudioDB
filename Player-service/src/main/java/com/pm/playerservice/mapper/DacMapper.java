package com.pm.playerservice.mapper;

import com.pm.playerservice.dto.ChipResponseDTO;
import com.pm.playerservice.dto.dac.DacResponseDTO;
import com.pm.playerservice.dto.dac.DacSpecResponseDTO;
import com.pm.playerservice.model.Player;
import com.pm.playerservice.model.dac.DacSpec;

public class DacMapper {
    public static DacResponseDTO toDTO(Player player) {
        DacSpec spec=player.getDacSpec();

        DacResponseDTO dacResponseDTO = new DacResponseDTO();
        dacResponseDTO.setId(player.getId());
        dacResponseDTO.setBrand(player.getBrand().getName());
        dacResponseDTO.setModel(player.getModel());
        dacResponseDTO.setPlayerType(player.getPlayerType());

        DacSpecResponseDTO specDto = new DacSpecResponseDTO();
        specDto.setBitDepth(spec.getBitDepth());
        specDto.setOverSampling(spec.isOverSampling());

        specDto.setChips(spec.getDacChips().stream().map(c -> {
            ChipResponseDTO chip = new ChipResponseDTO();
            chip.setBrand(c.getModel().getBrand().getName());
            chip.setModel(c.getModel().getModel());
            chip.setQuantity(c.getQuantity());
            return chip;
        }).toList());

        dacResponseDTO.setDacSpec(specDto);

        return dacResponseDTO;
    }
}
