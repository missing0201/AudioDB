package com.pm.earphonesdb.controller;

import com.pm.earphonesdb.dto.earphones.EarphoneRequestDTO;
import com.pm.earphonesdb.dto.earphones.EarphoneResponseDTO;
import com.pm.earphonesdb.dto.headphones.HeadphoneRequestDTO;
import com.pm.earphonesdb.dto.headphones.HeadphoneResponseDTO;
import com.pm.earphonesdb.grpc.SoundSignatureGrpcClient;
import com.pm.earphonesdb.service.EarphoneService;
import com.pm.earphonesdb.service.HeadphoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hp")
@Tag(name="Headphone",description = "API for managing headphones")
public class HeadphoneController {
    private final HeadphoneService headphoneService;
    private final SoundSignatureGrpcClient soundSignatureGrpcClient;

    public HeadphoneController(HeadphoneService headphoneService, SoundSignatureGrpcClient soundSignatureGrpcClient) {
        this.headphoneService = headphoneService;
        this.soundSignatureGrpcClient=soundSignatureGrpcClient;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('read:headphones')")
    @Operation(summary = "Get all headphones")
    public ResponseEntity<List<HeadphoneResponseDTO>> getHeadphones() {
        List<HeadphoneResponseDTO> headphones = headphoneService.getHeadphones();
        return ResponseEntity.ok().body(headphones);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('read:headphones')")
    @Operation(summary = "Get headphone by ID")
    public ResponseEntity<HeadphoneResponseDTO> getHeadphone(@PathVariable Long id) {
        HeadphoneResponseDTO headphoneResponseDTO = headphoneService.getHeadphone(id);
        return ResponseEntity.ok().body(headphoneResponseDTO);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create:headphones')")
    @Operation(summary = "Put new headphones")
    public ResponseEntity<HeadphoneResponseDTO> createHeadphone(@Valid @RequestBody HeadphoneRequestDTO headphoneRequestDTO){
        HeadphoneResponseDTO headphoneResponseDTO = headphoneService.createHeadphone(headphoneRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(headphoneResponseDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('update:headphones')")
    @Operation(summary = "update headphone by id and new info")
    public ResponseEntity<HeadphoneResponseDTO> updateHeadphone(@PathVariable Long id,@Valid @RequestBody HeadphoneRequestDTO headphoneRequestDTO){
        HeadphoneResponseDTO headphoneResponseDTO = headphoneService.updateHeadphone(id,headphoneRequestDTO);
        return ResponseEntity.ok().body(headphoneResponseDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('delete:headphones')")
    @Operation(summary = "Delete headphone by ID")
    public ResponseEntity<HeadphoneResponseDTO> deleteHeadphone(@PathVariable Long id) {
        headphoneService.deleteHeadphone(id);
        return ResponseEntity.noContent().build();
    }
}
