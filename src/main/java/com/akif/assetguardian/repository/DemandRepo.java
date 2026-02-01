package com.akif.assetguardian.repository;

import com.akif.assetguardian.enums.DemandStatus;
import com.akif.assetguardian.model.Demand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandRepo extends JpaRepository<Demand,Integer> {

    List<Demand> findByUserId(Integer userId);

    List<Demand> findByStatus(DemandStatus status);

    List<Demand> findByUserIdAndStatus(Integer userId, DemandStatus demandStatus);
}
