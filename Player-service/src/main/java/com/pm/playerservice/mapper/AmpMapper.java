package com.pm.playerservice.mapper;

import com.pm.playerservice.dto.request.AmpRequestDTO;
import com.pm.playerservice.dto.response.AmpResponseDTO;
import com.pm.playerservice.dto.response.AmpSpecResponseDTO;
import com.pm.playerservice.model.Player;
import com.pm.playerservice.model.amp.AmpSpec;

public class AmpMapper {
    public static AmpResponseDTO toDTO(Player player) {
        AmpSpec spec=player.getAmpSpec();

        AmpResponseDTO ampResponseDTO = new AmpResponseDTO();
        ampResponseDTO.setId(player.getId());
        ampResponseDTO.setBrand(player.getBrand().getName());
        ampResponseDTO.setModel(player.getModel());
        ampResponseDTO.setPlayerType(player.getPlayerType());

        AmpSpecResponseDTO ampSpecResponseDTO = new AmpSpecResponseDTO();
        ampSpecResponseDTO.setPowerOutput(spec.getPowerOutput());
        ampSpecResponseDTO.setMinImpedance(spec.getMinImpedance());
        ampSpecResponseDTO.setMaxImpedance(spec.getMaxImpedance());
        ampSpecResponseDTO.setAmpClass(spec.getAmpClass());

        ampResponseDTO.setAmpSpec(ampSpecResponseDTO);

        return ampResponseDTO;
    }

//    public static Player toEntity(AmpRequestDTO ampRequestDTO){
//        Player player = new Player();
//        player.setId(ampRequestDTO.getId());
//        player.setModel(ampRequestDTO.getModel());
//        player.setPlayerType(ampRequestDTO.getPlayerType());
//        player.setAmpSpec(ampRequestDTO.getAmpSpec());
//        return  player;
//    }


}
