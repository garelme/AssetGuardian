package com.akif.assetguardian.repository;

import com.akif.assetguardian.model.Asset;
import com.akif.assetguardian.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepo extends JpaRepository<Asset,Integer> {

    @Query("SELECT a FROM Asset a JOIN FETCH a.category WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :assetName, '%'))")
    List<Asset> findByNameContainingIgnoreCase(String assetName);

    @Query("SELECT a FROM Asset a JOIN FETCH a.category")
    List<Asset> findAllWithDetails();

}
