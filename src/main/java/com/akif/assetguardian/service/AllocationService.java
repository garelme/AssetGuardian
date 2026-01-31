package com.akif.assetguardian.service;


import com.akif.assetguardian.DTO.AssetAllocationResponse;
import com.akif.assetguardian.enums.AssetStatus;
import com.akif.assetguardian.enums.AssignmentStatus;
import com.akif.assetguardian.enums.DemandStatus;
import com.akif.assetguardian.exception.BadRequestException;
import com.akif.assetguardian.exception.ResourceNotFoundException;
import com.akif.assetguardian.model.*;
import com.akif.assetguardian.repository.*;
import com.akif.assetguardian.utils.SecurityUtils;
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
    private final AssignmentRepo assignmentRepo;
    private final UserRepo userRepo;

    @Transactional
    public void allocateAssetToDemand(int assetId, int demandId, LocalDate returnDate, String notes)  {
        Demand demand = demandRepo.findById(demandId).orElseThrow(() -> new EntityNotFoundException("Demand not found"));

        Integer currentAdminId = SecurityUtils.getCurrentUserId();
        User adminOrManager = userRepo.findById(currentAdminId)
                .orElseThrow(() -> new ResourceNotFoundException("Authorized user not found with ID: " + currentAdminId));

        if (demand.getStatus() != DemandStatus.APPROVED) {
            throw new BadRequestException("Allocation failed. Demand status must be 'APPROVED'. Current status: " + demand.getStatus());
        }

        Asset asset = assetRepo.findById(assetId).orElseThrow(() -> new ResourceNotFoundException("Asset not found with ID: " + assetId));

        if(asset.getStatus() != AssetStatus.IN_STOCK){
            throw new BadRequestException("Only assets with status ‘IN_STOCK’ can be assigned. Current status:" + asset.getStatus());
        }

        Allocation allocation = new Allocation();
        allocation.setAsset(asset);
        allocation.setDemand(demand);
        allocation.setReturnDate(returnDate);
        allocation.setUser(demand.getUser());
        allocation.setNotes(notes);

        updateAssignments(demand.getUser(),asset,AssignmentStatus.ACTIVE,adminOrManager,LocalDate.now());

        asset.setStatus(AssetStatus.ASSIGNED);

        demand.setAssignedAsset(asset);
        demand.setStatus(DemandStatus.COMPLETED);

        allocationRepo.save(allocation);
    }

    @Transactional
    public void returnAllocatedAsset(int allocationId) {
        Allocation allocation = allocationRepo.findById(allocationId)
                .orElseThrow(() -> new ResourceNotFoundException("No allocation record found! ID: " + allocationId));

        if (!allocation.isActive()) {
            throw new BadRequestException("This allocation has already been returned. ID: " + allocationId);
        }

        allocation.setActive(false);
        allocation.setReturnDate(LocalDate.now());

        Integer currentAdminId = SecurityUtils.getCurrentUserId();
        User adminOrManager = userRepo.findById(currentAdminId).orElseThrow(() -> new ResourceNotFoundException("Authorized user not found! ID: " + currentAdminId));

        Asset asset = allocation.getAsset();
        asset.setStatus(AssetStatus.IN_STOCK);

        updateAssignments(allocation.getUser(), asset, AssignmentStatus.RETURNED, adminOrManager,LocalDate.now());
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

    @Transactional
    public void updateAssignments(User user, Asset asset, AssignmentStatus status, User assignedBy, LocalDate assignedDate){
        Assignment assignment = Assignment.builder()
                .user(user)
                .asset(asset)
                .status(status)
                .assignedBy(assignedBy)
                .assignedDate(assignedDate)
                .build();
        assignmentRepo.save(assignment);
    }
}
