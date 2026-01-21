package com.akif.assetguardian.service;


import com.akif.assetguardian.DTO.AssignmentResponse;
import com.akif.assetguardian.model.Assignment;
import com.akif.assetguardian.repository.AssignmentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepo assignmentRepo;

    public List<AssignmentResponse> getAllAssignments(String name) {
        List<Assignment> assignments;

        if(name != null && !name.trim().isEmpty())
            assignments = assignmentRepo.findByAssetNameOrUserNameContainingIgnoreCase(name);
        else
            assignments = assignmentRepo.findAll();

        return assignments.stream()
                .map(asset -> mapToResponse(asset))
                .toList();
    }

    public AssignmentResponse mapToResponse(Assignment assignment){
        return new AssignmentResponse(
                assignment.getId(),
                assignment.getAsset().getName(),
                assignment.getAsset().getSerialNumber(),
                assignment.getUser().getName(),
                assignment.getUser().getDepartment().name()
        );
    }


}
