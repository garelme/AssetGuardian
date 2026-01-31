package com.akif.assetguardian.repository;

import com.akif.assetguardian.enums.AssignmentStatus;
import com.akif.assetguardian.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepo extends JpaRepository<Assignment,Integer> {
    @Query("SELECT a FROM Assignment a JOIN FETCH a.asset JOIN FETCH a.user WHERE LOWER(a.asset.name) LIKE LOWER(CONCAT('%', :name, '%')) OR " + "LOWER(a.user.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Assignment> findByAssetNameOrUserNameContainingIgnoreCase(String name);

    @Query("SELECT a FROM Assignment a JOIN FETCH a.user WHERE a.asset.id = :id AND a.status = :status")
    Optional<Assignment> findByAssetIdAndStatus(int id, AssignmentStatus status);

    @Query("SELECT a FROM Assignment a JOIN FETCH a.asset JOIN FETCH a.user")
    List<Assignment> findAllWithDetails();
}

