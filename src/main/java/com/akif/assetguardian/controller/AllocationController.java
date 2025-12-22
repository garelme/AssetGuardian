package com.akif.assetguardian.controller;

import com.akif.assetguardian.DTO.AssetAllocationRequest;
import com.akif.assetguardian.DTO.AssetAllocationResponse;
import com.akif.assetguardian.service.AllocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/allocation")
@RequiredArgsConstructor
public class AllocationController {
    private final AllocationService allocationService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping("/allocate")
    public ResponseEntity<Void> allocateAssets(@RequestBody AssetAllocationRequest request) {
        allocationService.allocateAssetToDemand(request.assetId(), request.demandId(), request.returnDate(), request.notes());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping("/allocate/{allocationId}")
    public ResponseEntity<Void> returnAsset(@PathVariable int allocationId) {
        allocationService.returnAllocatedAsset(allocationId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @GetMapping("/active")
    public ResponseEntity<List<AssetAllocationResponse>> getActiveAllocations() {
        return ResponseEntity.ok(allocationService.getActiveAllocations());
    }

}
