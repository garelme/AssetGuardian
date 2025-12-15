package com.akif.assetguardian.DTO;

public record AssignmentResponse(
        int assignmentId,

        String assetName,
        String serialNumber,

        String userName,
        String departmentName
) { }
