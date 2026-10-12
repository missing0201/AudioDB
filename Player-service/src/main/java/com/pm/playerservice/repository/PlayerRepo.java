package com.pm.playerservice.repository;

import com.pm.playerservice.model.Player;
import com.pm.playerservice.model.PlayerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepo extends JpaRepository<Player,Long> {
    List<Player> findByPlayerType(PlayerType type);
}
