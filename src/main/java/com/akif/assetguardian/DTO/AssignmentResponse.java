package com.akif.assetguardian.DTO;

import com.akif.assetguardian.enums.AssignmentStatus;

import java.time.LocalDate;

public record AssignmentResponse(
        int assignmentId,

        String assetName,
        String serialNumber,

        String userName,
        String departmentName,

        LocalDate assignmentDate,
        LocalDate updatedDate,

        AssignmentStatus status,
        int assignedById
) { }
