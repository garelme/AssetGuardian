package com.akif.assetguardian.controller;

import com.akif.assetguardian.DTO.AssetDemandRequest;
import com.akif.assetguardian.DTO.AssetDemandResponse;
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

import static com.akif.assetguardian.utils.SecurityUtils.hasRole;

@RestController
@RequestMapping("/api/v1/demands")
@RequiredArgsConstructor
@Validated
public class DemandController { //demand approve ve reject yetkileri düzenlenecek

    private final DemandService demandService;

    @PostMapping
    public ResponseEntity<AssetDemandResponse> createDemand(@Valid @RequestBody AssetDemandRequest assetDemandRequest) {
        AssetDemandResponse response = demandService.createDemand(assetDemandRequest);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AssetDemandResponse>> getAllDemands(@RequestParam(required = false) String filter) {
        List<AssetDemandResponse> response = demandService.getAllDemands(filter);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/{demandId}")
    public ResponseEntity<AssetDemandResponse> updateDemand(@Positive(message = "Demand ID must be a positive value!") @PathVariable int demandId,@Valid @RequestBody AssetDemandRequest assetDemandRequest) {
        AssetDemandResponse response = demandService.updateDemand(assetDemandRequest,demandId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

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
