package com.akif.assetguardian.repository;

import com.akif.assetguardian.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssignmentRepo extends JpaRepository<Assignment,Integer> {
    @Query("SELECT a FROM Assignment a WHERE a.asset.id = :id")
    Optional<Assignment> findByAssetId(int id);
}
