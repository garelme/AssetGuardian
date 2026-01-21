package com.akif.assetguardian.service;

import com.akif.assetguardian.DTO.AssetDemandRequest;
import com.akif.assetguardian.DTO.AssetDemandResponse;
import com.akif.assetguardian.enums.DemandStatus;
import com.akif.assetguardian.model.Category;
import com.akif.assetguardian.model.Demand;
import com.akif.assetguardian.model.User;
import com.akif.assetguardian.repository.CategoryRepo;
import com.akif.assetguardian.repository.DemandRepo;
import com.akif.assetguardian.repository.UserRepo;
import com.akif.assetguardian.utils.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
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

    public List<AssetDemandResponse> getAllDemands(String filter) {
        List<Demand> demands;
        if (SecurityUtils.hasRole("ROLE_ADMIN")){
            if (filter != null && !filter.trim().isEmpty()) {
                demands = demandRepo.searchAll(filter);
            }
            else
                demands = demandRepo.findAll();
        }
        else{
            Integer userId = SecurityUtils.getCurrentUserId();
            demands = demandRepo.findByUserId(userId);
        }
        return demands.stream()
                .map(demand -> mapToResponse(demand))
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
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Integer userId = SecurityUtils.getCurrentUserId();
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Demand newDemand = new Demand();
        newDemand.setCategory(category);
        newDemand.setUser(user);
        newDemand.setDescription(assetDemandRequest.notes());

        Demand savedDemand = demandRepo.save(newDemand);

        return mapToResponse(savedDemand);
    }

    @Transactional
    public AssetDemandResponse updateDemand(AssetDemandRequest assetDemandRequest, int demandId) throws AccessDeniedException {
        Demand existingDemand = demandRepo.findById(demandId)
                .orElseThrow(() -> new EntityNotFoundException("Talep bulunamadı"));

        if (!SecurityUtils.isOwnerOrAdmin(existingDemand.getUser().getId())) {
            throw new AccessDeniedException("Bu talebi güncelleme yetkiniz yok!");
        }

        if (!existingDemand.getStatus().equals(DemandStatus.PENDING)){
            throw new IllegalStateException("Sadece beklemedeki talepler güncellenebilir!");
        }

        Category category = categoryRepo.findById(assetDemandRequest.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Kategori bulunamadı"));

        existingDemand.setCategory(category);
        existingDemand.setDescription(assetDemandRequest.notes());
        existingDemand.setUrgency(assetDemandRequest.urgency());

        demandRepo.save(existingDemand);
        return mapToResponse(demandRepo.save(existingDemand));

    }

    @Transactional
    public void deleteDemand(int demandId) {
        Demand existingDemand = demandRepo.findById(demandId)
                .orElseThrow(() -> new EntityNotFoundException("Talep bulunamadı"));

        if (!SecurityUtils.isOwnerOrAdmin(existingDemand.getUser().getId())) {
            throw new AccessDeniedException("Bu talebi silme yetkiniz yok!");
        }
        if (!existingDemand.getStatus().equals(DemandStatus.PENDING)){
            throw new IllegalStateException("Sadece beklemedeki talepler silinebilir!");
        }

        demandRepo.delete(existingDemand);

    }

    @Transactional
    public void approveDemand(int demandId) {
        Demand existingDemand = demandRepo.findById(demandId)
                .orElseThrow(() -> new EntityNotFoundException("Demand not found"));

        if (!existingDemand.getStatus().equals(DemandStatus.PENDING)){
            throw new IllegalStateException("Demand is not in PENDING status");
        }

        existingDemand.setStatus(DemandStatus.APPROVED);
        demandRepo.save(existingDemand);
    }

    @Transactional
    public void rejectDemand(int demandId) {
        Demand existingDemand = demandRepo.findById(demandId)
                .orElseThrow(() -> new EntityNotFoundException("Demand not found"));

        if (!existingDemand.getStatus().equals(DemandStatus.PENDING)){
            throw new IllegalStateException("Demand is not in PENDING status");
        }

        existingDemand.setStatus(DemandStatus.REJECTED);
        demandRepo.save(existingDemand);
    }
}
