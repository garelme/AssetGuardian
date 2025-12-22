package com.akif.assetguardian.DTO;

import com.akif.assetguardian.enums.AssetStatus;
import com.akif.assetguardian.enums.DemandStatus;
import com.akif.assetguardian.enums.Department;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AssetAllocationResponse(
        int allocationId,
        int assetId,
        String assetName,
        String serialNumber,

        int userId,
        String userName,
        Department department,

        LocalDate allocationDate,
        LocalDate returnDate,

        String notes
) { }
