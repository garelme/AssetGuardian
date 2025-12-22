package com.akif.assetguardian.service;


import com.akif.assetguardian.DTO.AssetAllocationResponse;
import com.akif.assetguardian.enums.AssetStatus;
import com.akif.assetguardian.enums.DemandStatus;
import com.akif.assetguardian.model.Allocation;
import com.akif.assetguardian.model.Asset;
import com.akif.assetguardian.model.Demand;
import com.akif.assetguardian.repository.AllocationRepo;
import com.akif.assetguardian.repository.AssetRepo;
import com.akif.assetguardian.repository.DemandRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AllocationService {
    private final DemandRepo demandRepo;
    private final AssetRepo assetRepo;
    private final AllocationRepo allocationRepo;

    @Transactional
    public void allocateAssetToDemand(int assetId, int demandId, LocalDate returnDate, String notes) {
        Demand demand = demandRepo.findById(demandId).orElseThrow(() -> new EntityNotFoundException("Demand not found"));

        if (demand.getStatus() != DemandStatus.APPROVED) {
            throw new IllegalStateException("Invalid demand status for allocation: " + demand.getStatus());
        }

        Asset asset = assetRepo.findById(assetId).orElseThrow(() -> new EntityNotFoundException("Asset not found"));

        if(asset.getStatus() != AssetStatus.IN_STOCK){
            throw new IllegalStateException("Only assets with status ‘IN_STOCK’ can be assigned. Current status:" + asset.getStatus());
        }

        Allocation allocation = new Allocation();
        allocation.setAsset(asset);
        allocation.setDemand(demand);
        allocation.setReturnDate(returnDate);
        allocation.setUser(demand.getUser());
        allocation.setNotes(notes);

        asset.setStatus(AssetStatus.ASSIGNED);

        demand.setAssignedAsset(asset);
        demand.setStatus(DemandStatus.COMPLETED);

        demandRepo.save(demand);
        assetRepo.save(asset);
        allocationRepo.save(allocation);
    }

    public void returnAllocatedAsset(int allocationId) {
        Allocation allocation = allocationRepo.findById(allocationId)
                .orElseThrow(() -> new EntityNotFoundException("No allocation record found! ID: " + allocationId));
        if (!allocation.isActive()) {
            throw new IllegalStateException("This allocation has already been returned. ID: " + allocationId);
        }
        allocation.setActive(false);
        allocation.setReturnDate(LocalDate.now());

        Asset asset = allocation.getAsset();
        asset.setStatus(AssetStatus.IN_STOCK);

        allocationRepo.save(allocation);
        assetRepo.save(asset);
    }

    public List<AssetAllocationResponse> getActiveAllocations() {
        List<Allocation> activeAllocations = allocationRepo.findByIsActiveTrue();
        return activeAllocations.stream()
                .map(allocation ->  mapToResponse(allocation))
                .toList();
    }

    private AssetAllocationResponse mapToResponse(Allocation allocation) {
        return new AssetAllocationResponse(
                allocation.getId(),
                allocation.getAsset().getId(),
                allocation.getAsset().getName(),
                allocation.getAsset().getSerialNumber(),

                allocation.getUser().getId(),
                allocation.getUser().getName(),
                allocation.getUser().getDepartment(),

                allocation.getCreatedAt(),
                allocation.getReturnDate(),
                allocation.getNotes()
        );
    }
}
