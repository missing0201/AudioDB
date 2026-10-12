package com.pm.playerservice.service;

import com.pm.playerservice.mapper.AmpMapper;
import com.pm.playerservice.mapper.ComboMapper;
import com.pm.playerservice.mapper.DacMapper;
import com.pm.playerservice.model.Player;
import com.pm.playerservice.model.PlayerType;
import com.pm.playerservice.repository.PlayerRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerService {
    private final PlayerRepo playerRepo;

    public PlayerService(PlayerRepo playerRepo) {
        this.playerRepo = playerRepo;
    }

    @Transactional(readOnly = true)
    public List<?> getPlayers(PlayerType type) {

        List<Player> players = type == null
                ? playerRepo.findAll()
                : playerRepo.findByPlayerType(type);

        return players.stream()
                .map(this::toDTO)
                .toList();
    }

    private Object toDTO(Player player) {
        return switch (player.getPlayerType()) {
            case DAC -> DacMapper.toDTO(player);
            case AMP -> AmpMapper.toDTO(player);
            case DAC_AMP -> ComboMapper.toDTO(player);
        };
    }
}
