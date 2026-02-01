package com.akif.assetguardian.controller;

import com.akif.assetguardian.DTO.AssetDemandRequest;
import com.akif.assetguardian.DTO.AssetDemandResponse;
import com.akif.assetguardian.enums.DemandStatus;
import com.akif.assetguardian.service.DemandService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/demands")
@RequiredArgsConstructor
@Validated
public class DemandController {

    private final DemandService demandService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    @PostMapping
    public ResponseEntity<AssetDemandResponse> createDemand(@Valid @RequestBody AssetDemandRequest assetDemandRequest) {
        return new ResponseEntity<>(demandService.createDemand(assetDemandRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    @GetMapping
    public ResponseEntity<List<AssetDemandResponse>> getAllDemands(@RequestParam(required = false) DemandStatus status) {
        return new ResponseEntity<>(demandService.getAllDemands(status), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    @PutMapping("/{demandId}")
    public ResponseEntity<AssetDemandResponse> updateDemand(@Positive(message = "Demand ID must be a positive value!") @PathVariable int demandId,@Valid @RequestBody AssetDemandRequest assetDemandRequest) {
        return new ResponseEntity<>(demandService.updateDemand(assetDemandRequest,demandId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/{demandId}")
    public ResponseEntity<Void> deleteDemand(@Positive(message = "Demand ID must be a positive value!") @PathVariable int demandId) {
        demandService.deleteDemand(demandId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/{demandId}/approve")
    public ResponseEntity<Void> approveDemand(@Positive(message = "Demand ID must be a positive value!") @PathVariable int demandId) {
        demandService.approveDemand(demandId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/{demandId}/reject")
    public ResponseEntity<Void> rejectDemand(@Positive(message = "Demand ID must be a positive value!") @PathVariable int demandId) {
        demandService.rejectDemand(demandId);
        return ResponseEntity.ok().build();
    }
}
