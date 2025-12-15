package com.akif.assetguardian.service;

import com.akif.assetguardian.DTO.AssetRequest;
import com.akif.assetguardian.DTO.AssetResponse;
import com.akif.assetguardian.enums.AssetStatus;
import com.akif.assetguardian.model.Asset;
import com.akif.assetguardian.model.Assignment;
import com.akif.assetguardian.model.Category;
import com.akif.assetguardian.model.User;
import com.akif.assetguardian.repository.AssetRepo;
import com.akif.assetguardian.repository.AssignmentRepo;
import com.akif.assetguardian.repository.CategoryRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AssetService {
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    AssetRepo assetRepo;
    @Autowired
    AssignmentRepo assignmentRepo;


    @Transactional
    public AssetResponse saveAsset(AssetRequest item) {
        Category category = categoryRepo.findById(item.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Asset savedAsset = new Asset();
        savedAsset.setName(item.name());
        savedAsset.setCategory(category);
        savedAsset.setPrice(item.price());
        savedAsset.setSerialNumber(item.serialNumber());

        Asset dbAsset = assetRepo.save(savedAsset);

        return new AssetResponse(
                dbAsset.getId(),
                dbAsset.getName(),
                dbAsset.getSerialNumber(),
                dbAsset.getPrice(),
                dbAsset.getCategory().getName(),
                dbAsset.getStatus(),
                null
        );
    }

    public List<AssetResponse> getAllAssets(String name) {
        List<Asset> assets;

        if(name != null && !name.isEmpty())
            assets = assetRepo.findByNameContainingIgnoreCase(name);
        else
            assets = assetRepo.findAll();

        return assets.stream()
                .map(asset -> mapToResponse(asset))
                .toList();
    }

    private AssetResponse mapToResponse(Asset asset) {

        String assignedInfo = "Unassigned";


        if (asset.getStatus() != AssetStatus.IN_STOCK) {
            Optional<Assignment> activeAssignment = assignmentRepo
                    .findByAssetId(asset.getId());

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

        boolean hasAssignedAssets = assetsToDelete.stream()
                .anyMatch(asset -> asset.getStatus() != AssetStatus.IN_STOCK);

        if (hasAssignedAssets) {
            throw new RuntimeException("Cannot delete assets that are currently assigned.");
        }

        List<AssetResponse> response = assetsToDelete.stream()
                .map(asset -> mapToResponse(asset))
                .toList();

        assetRepo.deleteAll(assetsToDelete);

        return response;
    }

    @Transactional
    public AssetResponse deleteAsset(int id) {
        Asset asset = assetRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        if (asset.getStatus() != AssetStatus.IN_STOCK) {
            throw new RuntimeException("Cannot delete an asset that is currently assigned.");
        }

        AssetResponse response = mapToResponse(asset);

        assetRepo.delete(asset);

        return response;
    }

    @Transactional
    public AssetResponse updateAsset(int id, AssetRequest asset) {
        Asset existingAsset = assetRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        Category category = categoryRepo.findById(asset.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existingAsset.setName(asset.name());
        existingAsset.setSerialNumber(asset.serialNumber());
        existingAsset.setPrice(asset.price());
        existingAsset.setCategory(category);

        Asset updatedAsset = assetRepo.save(existingAsset);

        return mapToResponse(updatedAsset);
    }
}





