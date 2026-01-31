package com.akif.assetguardian.controller;


import com.akif.assetguardian.DTO.AssetRequest;
import com.akif.assetguardian.DTO.AssetResponse;
import com.akif.assetguardian.service.AssetService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assets")
@RequiredArgsConstructor
@Validated
public class AssetController {

    private final AssetService assetService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<AssetResponse>> getAllAssets(@RequestParam(required = false) String name) {
        List<AssetResponse> assets = assetService.getAllAssets(name);
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AssetResponse> addAsset(@Valid @RequestBody AssetRequest asset) {
        AssetResponse response = assetService.saveAsset(asset);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<AssetResponse> deleteAsset(@Positive(message = "Asset ID must be a positive value!") @PathVariable int id) {
        AssetResponse response = assetService.deleteAsset(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/batch-delete") //URL becomes unnecessarily long if we use DELETEMAPPING method with PathVariable
    public ResponseEntity<List<AssetResponse>> deleteAssets(@NotEmpty(message = "The ID list for deletion cannot be empty!") @RequestBody List<@Positive(message = "Each ID in the list must be positive!") Integer> ids) {
        List<AssetResponse> response = assetService.deleteAssets(ids);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AssetResponse> updateAsset(@Positive(message = "Asset ID must be a positive value!") @PathVariable int id, @Valid @RequestBody AssetRequest asset) {
        AssetResponse response = assetService.updateAsset(id, asset);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
