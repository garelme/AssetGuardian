package com.akif.assetguardian.controller;

import com.akif.assetguardian.DTO.AssignmentResponse;
import com.akif.assetguardian.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping
    public ResponseEntity<List<AssignmentResponse>> getAllAssignment (@RequestParam(required = false) String name){
        List<AssignmentResponse> response = assignmentService.getAllAssignments(name);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
