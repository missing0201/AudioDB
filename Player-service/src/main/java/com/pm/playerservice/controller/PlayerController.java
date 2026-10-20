package com.pm.playerservice.controller;

import com.pm.playerservice.dto.request.DacRequestDTO;
import com.pm.playerservice.dto.request.PlayerRequest;
import com.pm.playerservice.dto.response.PlayerResponse;
import com.pm.playerservice.model.PlayerType;
import com.pm.playerservice.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<List<PlayerResponse>> getPlayers(
            @RequestParam(required = false) PlayerType type) {
        return ResponseEntity.ok().body(playerService.getPlayers(type));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponse> getPlayer(@PathVariable Long id) {
        return ResponseEntity.ok().body(playerService.getPlayer(id));
    }


    @PostMapping
    public ResponseEntity<PlayerResponse> createPlayer(@Valid @RequestBody PlayerRequest playerRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(playerService.addPlayer(playerRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerResponse> updatePlayer(@PathVariable Long id,@Valid @RequestBody DacRequestDTO playerRequestDTO) {
        return ResponseEntity.ok(playerService.updatePlayer(id, playerRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@RequestParam Long id){
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
}
