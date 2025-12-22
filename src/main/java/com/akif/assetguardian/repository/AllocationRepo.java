package com.akif.assetguardian.repository;

import com.akif.assetguardian.model.Allocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllocationRepo extends JpaRepository<Allocation, Integer> {
    List<Allocation> findByIsActiveTrue();
}
