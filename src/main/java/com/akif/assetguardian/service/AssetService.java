package com.akif.assetguardian.service;

import com.akif.assetguardian.DTO.AssetRequest;
import com.akif.assetguardian.DTO.AssetResponse;
import com.akif.assetguardian.enums.AssetStatus;
import com.akif.assetguardian.enums.AssignmentStatus;
import com.akif.assetguardian.exception.BadRequestException;
import com.akif.assetguardian.exception.ResourceNotFoundException;
import com.akif.assetguardian.model.Asset;
import com.akif.assetguardian.model.Assignment;
import com.akif.assetguardian.model.Category;
import com.akif.assetguardian.model.User;
import com.akif.assetguardian.repository.AssetRepo;
import com.akif.assetguardian.repository.AssignmentRepo;
import com.akif.assetguardian.repository.CategoryRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AssetService {

    private final CategoryRepo categoryRepo;
    private final AssetRepo assetRepo;
    private final AssignmentRepo assignmentRepo;

    @Transactional
    public AssetResponse saveAsset(AssetRequest item) {
        Category category = categoryRepo.findById(item.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + item.categoryId()));

        Asset savedAsset = new Asset();
        savedAsset.setName(item.name());
        savedAsset.setCategory(category);
        savedAsset.setPrice(item.price());
        savedAsset.setSerialNumber(item.serialNumber());

        Asset dbAsset = assetRepo.save(savedAsset);
        return mapToResponse(dbAsset);
    }

    public List<AssetResponse> getAllAssets(String name) {
        List<Asset> assets;

        if(name != null && !name.trim().isEmpty())
            assets = assetRepo.findByNameContainingIgnoreCase(name);
        else
            assets = assetRepo.findAllWithDetails();

        return assets.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private AssetResponse mapToResponse(Asset asset) {
        String assignedInfo = "Unassigned";

        if (asset.getStatus() != AssetStatus.IN_STOCK) {
            Optional<Assignment> activeAssignment = assignmentRepo
                    .findByAssetIdAndStatus(asset.getId(), AssignmentStatus.ACTIVE);

            if (activeAssignment.isPresent()) {
                User user = activeAssignment.get().getUser();
                assignedInfo = user.getName() + " - " + user.getDepartment();
            }
        }

        return new AssetResponse(
                asset.getId(),
                asset.getName(),
                asset.getSerialNumber(),
                asset.getPrice(),
                asset.getCategory().getName(),
                asset.getStatus(),
                assignedInfo
        );
    }

    @Transactional
    public List<AssetResponse> deleteAssets(List<Integer> ids) {

        List<Asset> assetsToDelete = assetRepo.findAllById(ids);

        if (assetsToDelete.stream().anyMatch(a -> a.getStatus() != AssetStatus.IN_STOCK)) {
            throw new BadRequestException("Cannot delete assets that are currently assigned to a user.");
        }

        List<AssetResponse> response = assetsToDelete.stream()
                .map(this::mapToResponse)
                .toList();

        assetRepo.deleteAll(assetsToDelete);
        return response;
    }

    @Transactional
    public AssetResponse deleteAsset(int id) {
        Asset asset = assetRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with ID: " + id));

        if (asset.getStatus() != AssetStatus.IN_STOCK) {
            throw new BadRequestException("Cannot delete an asset that is currently assigned to a user. Return the asset first.");
        }

        AssetResponse response = mapToResponse(asset);
        assetRepo.delete(asset);

        return response;
    }

    @Transactional
    public AssetResponse updateAsset(int id, AssetRequest asset) {
        Asset existingAsset = assetRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with ID: " + id));

        Category category = categoryRepo.findById(asset.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + asset.categoryId()));

        existingAsset.setName(asset.name());
        existingAsset.setSerialNumber(asset.serialNumber());
        existingAsset.setPrice(asset.price());
        existingAsset.setCategory(category);

        return mapToResponse(existingAsset);
    }
}





