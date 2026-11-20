package com.pm.playerservice.controller;

import com.pm.playerservice.dto.request.DacRequestDTO;
import com.pm.playerservice.dto.request.PlayerRequest;
import com.pm.playerservice.dto.response.PlayerResponse;
import com.pm.playerservice.model.PlayerType;
import com.pm.playerservice.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('read:players')")
    public ResponseEntity<List<PlayerResponse>> getPlayers(
            @RequestParam(required = false) PlayerType type) {
        return ResponseEntity.ok().body(playerService.getPlayers(type));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('read:players')")
    public ResponseEntity<PlayerResponse> getPlayer(@PathVariable Long id) {
        return ResponseEntity.ok().body(playerService.getPlayer(id));
    }


    @PostMapping
    @PreAuthorize("hasAuthority('create:players')")
    public ResponseEntity<PlayerResponse> createPlayer(@Valid @RequestBody PlayerRequest playerRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(playerService.addPlayer(playerRequest));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('update:players')")
    public ResponseEntity<PlayerResponse> updatePlayer(@PathVariable Long id,@Valid @RequestBody DacRequestDTO playerRequestDTO) {
        return ResponseEntity.ok(playerService.updatePlayer(id, playerRequestDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('delete:players')")
    public ResponseEntity<Void> deletePlayer(@RequestParam Long id){
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
}
