package com.pm.playerservice.controller;

import com.pm.playerservice.model.Player;
import com.pm.playerservice.model.PlayerType;
import com.pm.playerservice.service.PlayerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<?> getPlayers(
            @RequestParam(required = false) PlayerType type) {
        return playerService.getPlayers(type);
    }
}
