package com.akif.assetguardian.controller;

import com.akif.assetguardian.DTO.AssetAllocationRequest;
import com.akif.assetguardian.DTO.AssetAllocationResponse;
import com.akif.assetguardian.service.AllocationService;
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
@RequestMapping("/api/v1/allocation")
@RequiredArgsConstructor
@Validated
public class AllocationController {

    private final AllocationService allocationService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping
    public ResponseEntity<AssetAllocationResponse> allocateAssets(@Valid @RequestBody AssetAllocationRequest request) {
        return new ResponseEntity<>(allocationService.allocateAssetToDemand(request.assetId(), request.demandId(), request.returnDate(), request.notes()), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/{allocationId}")
    public ResponseEntity<AssetAllocationResponse> returnAsset(@Positive(message = "Allocation ID must be a positive value!") @PathVariable int allocationId) {
        return new ResponseEntity<>(allocationService.returnAllocatedAsset(allocationId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping
    public ResponseEntity<List<AssetAllocationResponse>> getActiveAllocations() {
        return ResponseEntity.ok(allocationService.getActiveAllocations());
    }
}
