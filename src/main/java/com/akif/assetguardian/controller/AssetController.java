package com.akif.assetguardian.controller;


import com.akif.assetguardian.DTO.AssetRequest;
import com.akif.assetguardian.DTO.AssetResponse;
import com.akif.assetguardian.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assets")
@RequiredArgsConstructor
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
    public ResponseEntity<AssetResponse> addAsset(@RequestBody AssetRequest asset) {
        AssetResponse response = assetService.saveAsset(asset);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<AssetResponse> deleteAsset(@PathVariable int id) {
        AssetResponse response = assetService.deleteAsset(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/batch-delete") //URL becomes unnecessarily long if we use DELETEMAPPING method with PathVariable
    public ResponseEntity<List<AssetResponse>> deleteAssets(@RequestBody List<Integer> ids) {
        List<AssetResponse> response = assetService.deleteAssets(ids);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AssetResponse> updateAsset(@PathVariable int id, @RequestBody AssetRequest asset) {
        AssetResponse response = assetService.updateAsset(id, asset);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
