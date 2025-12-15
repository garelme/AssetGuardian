package com.akif.assetguardian.controller;

import com.akif.assetguardian.DTO.AssetDemandRequest;
import com.akif.assetguardian.DTO.AssetDemandResponse;
import com.akif.assetguardian.service.DemandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/demands")
@RequiredArgsConstructor
public class DemandController {

    private final DemandService demandService;

    @PostMapping
    public ResponseEntity<AssetDemandResponse> createDemand(@RequestBody AssetDemandRequest assetDemandRequest) {
        AssetDemandResponse response = demandService.createDemand(assetDemandRequest);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AssetDemandResponse>> getAllDemands(@RequestParam(required = false) String filter) {
        List<AssetDemandResponse> response = demandService.getAllDemands(filter);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/{demandId}")
    public ResponseEntity<AssetDemandResponse> updateDemand(@PathVariable int demandId,@RequestBody AssetDemandRequest assetDemandRequest) {
        AssetDemandResponse response = demandService.updateDemand(assetDemandRequest,demandId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{demandId}")
    public ResponseEntity<Void> deleteDemand(@PathVariable int demandId) {
        demandService.deleteDemand(demandId);
        return ResponseEntity.noContent().build();
    }


}
