package com.akif.assetguardian.service;

import com.akif.assetguardian.DTO.AssetDemandRequest;
import com.akif.assetguardian.DTO.AssetDemandResponse;
import com.akif.assetguardian.enums.DemandStatus;
import com.akif.assetguardian.exception.BadRequestException;
import com.akif.assetguardian.exception.ResourceNotFoundException;
import com.akif.assetguardian.model.Category;
import com.akif.assetguardian.model.Demand;
import com.akif.assetguardian.model.User;
import com.akif.assetguardian.repository.CategoryRepo;
import com.akif.assetguardian.repository.DemandRepo;
import com.akif.assetguardian.repository.UserRepo;
import com.akif.assetguardian.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DemandService {

    private final DemandRepo demandRepo;
    private final CategoryRepo categoryRepo;
    private final UserRepo userRepo;

    public List<AssetDemandResponse> getAllDemands(DemandStatus demandStatus) {
        List<Demand> demands;
        boolean hasFilter = demandStatus != null;
        if (SecurityUtils.hasRole("ROLE_ADMIN")){
            demands = hasFilter ? demandRepo.findByStatus(demandStatus) : demandRepo.findAll();
        }
        else{
            Integer userId = SecurityUtils.getCurrentUserId();
            demands = hasFilter ? demandRepo.findByUserIdAndStatus(userId, demandStatus) : demandRepo.findByUserId(userId);
        }
        return demands.stream()
                .map(this::mapToResponse)
                .toList();

    }

    private AssetDemandResponse mapToResponse(Demand demand) {
        String allocatedAssetName;

        if(demand.getStatus() == DemandStatus.APPROVED && demand.getAssignedAsset() != null){
            allocatedAssetName = demand.getAssignedAsset().getName() + " (" + demand.getAssignedAsset().getSerialNumber() + ")";
        }
        else if(demand.getStatus() == DemandStatus.REJECTED){
            allocatedAssetName = "Reddedildi!";
        }
        else{
            allocatedAssetName = "Henüz atanmadı.";
        }

        return new AssetDemandResponse(
                demand.getId(),
                demand.getUser().getId(),
                demand.getUser().getName(),
                demand.getCategory().getName(),
                allocatedAssetName,
                demand.getStatus(),
                demand.getCreatedAt()
        );

    }

    @Transactional
    public AssetDemandResponse createDemand(AssetDemandRequest assetDemandRequest) {
        Category category = categoryRepo.findById(assetDemandRequest.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Integer userId = SecurityUtils.getCurrentUserId();
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Demand newDemand = new Demand();
        newDemand.setCategory(category);
        newDemand.setUser(user);
        newDemand.setDescription(assetDemandRequest.notes());

        Demand savedDemand = demandRepo.save(newDemand);

        return mapToResponse(savedDemand);
    }

    @Transactional
    public AssetDemandResponse updateDemand(AssetDemandRequest assetDemandRequest, int demandId) throws AccessDeniedException {
        Demand existingDemand = getPendingDemandOrThrow(demandId);

        if (!SecurityUtils.isOwnerOrAdmin(existingDemand.getUser().getId())) {
            throw new AccessDeniedException("Unauthorized update attempt!");
        }

        Category category = categoryRepo.findById(assetDemandRequest.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));

        existingDemand.setCategory(category);
        existingDemand.setDescription(assetDemandRequest.notes());
        existingDemand.setUrgency(assetDemandRequest.urgency());

        return mapToResponse(existingDemand);
    }

    @Transactional
    public void deleteDemand(int demandId) {
        Demand existingDemand = getPendingDemandOrThrow(demandId);

        if (!SecurityUtils.isOwnerOrAdmin(existingDemand.getUser().getId())) {
            throw new AccessDeniedException("You are not authorized to delete this demand!");
        }

        demandRepo.delete(existingDemand);
    }

    @Transactional
    public void approveDemand(int demandId) {
        Demand existingDemand = getPendingDemandOrThrow(demandId);

        existingDemand.setStatus(DemandStatus.APPROVED);
    }

    @Transactional
    public void rejectDemand(int demandId) {
        Demand existingDemand = getPendingDemandOrThrow(demandId);

        existingDemand.setStatus(DemandStatus.REJECTED);
    }

    private Demand getPendingDemandOrThrow(int id) {
        Demand demand = demandRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Demand not found with id: " + id));

        if (!demand.getStatus().equals(DemandStatus.PENDING)) {
            throw new BadRequestException("Only pending demands can be processed!");
        }
        return demand;
    }
}
