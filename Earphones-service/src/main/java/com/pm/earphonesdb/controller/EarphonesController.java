package com.pm.earphonesdb.controller;

import com.pm.earphonesdb.dto.EarphoneRequestDTO;
import com.pm.earphonesdb.dto.EarphoneResponseDTO;
import com.pm.earphonesdb.dto.SignatureResponseDTO;
import com.pm.earphonesdb.grpc.SoundSignatureGrpcClient;
import com.pm.earphonesdb.service.EarphoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sound_signature.GetSignatureResponse;

import java.util.List;

@RestController
@RequestMapping("/iem")
@Tag(name="Earphone",description = "API for managing earphones")
public class EarphonesController {
    private final EarphoneService earphoneService;
    private final SoundSignatureGrpcClient soundSignatureGrpcClient;

    public EarphonesController(EarphoneService earphoneService,SoundSignatureGrpcClient soundSignatureGrpcClient) {
        this.earphoneService = earphoneService;
        this.soundSignatureGrpcClient=soundSignatureGrpcClient;
    }

    @GetMapping
    @Operation(summary = "Get all earphones")
    public ResponseEntity<List<EarphoneResponseDTO>> getEarphones() {
        List<EarphoneResponseDTO> earphones = earphoneService.getEarphones();
        return ResponseEntity.ok().body(earphones);
    }

    @GetMapping("/{id}/signature")
    @Operation(summary = "Get an earphone's signature")
    public ResponseEntity<SignatureResponseDTO> getEarphoneSignature(
            @PathVariable String id) {

        return ResponseEntity.ok(earphoneService.getSignature(id));
    }

    @PostMapping
    @Operation(summary = "Create a new earphone")
    public ResponseEntity<EarphoneResponseDTO> createEarphone(@Valid @RequestBody EarphoneRequestDTO earphoneRequestDTO) {
        EarphoneResponseDTO earphoneResponseDTO = earphoneService.createEarphone(earphoneRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(earphoneResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an earphone by ID")
    public ResponseEntity<EarphoneResponseDTO> updateEarphone(@PathVariable Long id,
                                                              @Valid @RequestBody EarphoneRequestDTO earphoneRequestDTO) {
        EarphoneResponseDTO earphoneResponseDTO = earphoneService.updateEarphone(id, earphoneRequestDTO);

        return ResponseEntity.ok().body(earphoneResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an earphone by ID")
    public ResponseEntity<Void> deleteEarphone(@PathVariable Long id) {
        earphoneService.deleteEarphone(id);
        return ResponseEntity.noContent().build();
    }
}
