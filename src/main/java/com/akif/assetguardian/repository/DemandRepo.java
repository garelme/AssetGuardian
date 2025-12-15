package com.akif.assetguardian.repository;

import com.akif.assetguardian.model.Demand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandRepo extends JpaRepository<Demand,Integer> {
    @Query("SELECT a FROM Asset a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :filter, '%'))")
    List<Demand> searchAll(String filter);

    @Query("SELECT d FROM Demand d WHERE d.user.id = :userId")
    List<Demand> findByUserId(Integer userId);
}
