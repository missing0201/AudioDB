package com.pm.earphonesdb.controller;

import com.pm.earphonesdb.dto.DriverType.DriverTypeRequestDTO;
import com.pm.earphonesdb.dto.DriverType.DriverTypeResponseDTO;
import com.pm.earphonesdb.model.DriverType;
import com.pm.earphonesdb.service.DriverTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivertype")
@Tag(name="Driver Type", description = "API for managing Driver Types")
public class DriverTypeController {

    private DriverTypeService driverTypeService;

    public DriverTypeController(DriverTypeService driverTypeService) {
        this.driverTypeService = driverTypeService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('read:driver-types')")
    @Operation(summary = "get all Driver Types")
    public ResponseEntity<List<DriverTypeResponseDTO>> getAllDriverTypes() {
        List<DriverTypeResponseDTO> driverTypeResponseDTO = driverTypeService.getDriverType();
        return ResponseEntity.ok().body(driverTypeResponseDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('read:driver-types')")
    @Operation(summary = "get Driver Type by ID")
    public ResponseEntity<DriverTypeResponseDTO> getDriverType(@PathVariable Long id){
        DriverTypeResponseDTO driverTypeResponseDTO=driverTypeService.getDriverTypeById(id);
        return ResponseEntity.ok().body(driverTypeResponseDTO);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('create:driver-types')")
    public ResponseEntity<DriverTypeResponseDTO> createDriverType(@Valid @RequestBody DriverTypeRequestDTO driverTypeRequestDTO){
        DriverTypeResponseDTO driverTypeResponseDTO=driverTypeService.createDriverType(driverTypeRequestDTO);
        return ResponseEntity.ok().body(driverTypeResponseDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('update:driver-types')")
    public ResponseEntity<DriverTypeResponseDTO> updateDriverType(@PathVariable Long id,@RequestBody DriverTypeRequestDTO driverTypeRequestDTO){
        DriverTypeResponseDTO driverTypeResponseDTO=driverTypeService.updateDriverType(id,driverTypeRequestDTO);
        return ResponseEntity.ok().body(driverTypeResponseDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('delete:driver-types')")
    public ResponseEntity<Void> deleteDriverType(@PathVariable Long id){
        driverTypeService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
