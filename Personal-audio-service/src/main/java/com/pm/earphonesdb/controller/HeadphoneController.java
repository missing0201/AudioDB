package com.pm.earphonesdb.controller;

import com.pm.earphonesdb.dto.earphones.EarphoneResponseDTO;
import com.pm.earphonesdb.dto.headphones.HeadphoneResponseDTO;
import com.pm.earphonesdb.grpc.SoundSignatureGrpcClient;
import com.pm.earphonesdb.service.EarphoneService;
import com.pm.earphonesdb.service.HeadphoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<HeadphoneResponseDTO>> getHeadphones() {
        List<HeadphoneResponseDTO> headphones = headphoneService.getHeadphones();
        return ResponseEntity.ok().body(headphones);
    }
}
