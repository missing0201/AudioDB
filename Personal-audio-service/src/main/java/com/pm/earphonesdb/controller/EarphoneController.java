package com.pm.earphonesdb.controller;

import com.pm.earphonesdb.dto.earphones.EarphoneRequestDTO;
import com.pm.earphonesdb.dto.earphones.EarphoneResponseDTO;
import com.pm.earphonesdb.dto.SignatureRequestDTO;
import com.pm.earphonesdb.dto.SignatureResponseDTO;
import com.pm.earphonesdb.grpc.SoundSignatureGrpcClient;
import com.pm.earphonesdb.service.EarphoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/iem")
@Tag(name="Earphone",description = "API for managing earphones")
public class EarphoneController {
    private final EarphoneService earphoneService;

    public EarphoneController(EarphoneService earphoneService) {
        this.earphoneService = earphoneService;
    }

    @GetMapping
    @Operation(summary = "Get all earphones")
    public ResponseEntity<List<EarphoneResponseDTO>> getEarphones() {
        List<EarphoneResponseDTO> earphones = earphoneService.getEarphones();
        return ResponseEntity.ok().body(earphones);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get earphone by id")
    public ResponseEntity<EarphoneResponseDTO> getEarphone(@PathVariable Long id) {
        EarphoneResponseDTO earphoneResponseDTO = earphoneService.getEarphone(id);
        return ResponseEntity.ok().body(earphoneResponseDTO);
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

    @PutMapping("/{id}/signature")
    @Operation(summary = "Update an earphone's signature")
    public ResponseEntity<SignatureResponseDTO> updateEarphoneSignature(@PathVariable String id,
            @Valid @RequestBody SignatureRequestDTO signatureRequestDTO) {

        return ResponseEntity.ok(earphoneService.updateEarphoneSignature(id,signatureRequestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an earphone by ID")
    public ResponseEntity<Void> deleteEarphone(@PathVariable Long id) {
        earphoneService.deleteEarphone(id);
        return ResponseEntity.noContent().build();
    }
}
