package com.example.demo.assignment.controller;

import com.example.demo.assignment.domain.Assignment;
import com.example.demo.assignment.dto.AssignmentCreateRequest;
import com.example.demo.assignment.dto.AssignmentResponse;
import com.example.demo.assignment.dto.AssignmentUpdateRequest;
import com.example.demo.assignment.service.AssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping("/members/{memberId}/assignments")
    public ResponseEntity<?> createAssignment(
            @PathVariable("memberId") Long memberId,
            @RequestBody AssignmentCreateRequest request) {

        Assignment created = assignmentService.createAssignment(memberId, request);
        if (created == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(AssignmentResponse.from(created));
    }

    @GetMapping("/members/{memberId}/assignments")
    public ResponseEntity<List<AssignmentResponse>> getAssignmentsByMember(@PathVariable("memberId") Long memberId) {
        List<Assignment> assignments = assignmentService.findByMemberId(memberId);

        List<AssignmentResponse> response = assignments.stream()
                .map(AssignmentResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/assignments/{id}")
    public ResponseEntity<?> getAssignment(@PathVariable("id") Long id) {
        Assignment assignment = assignmentService.findById(id);
        if (assignment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(AssignmentResponse.from(assignment));
    }

    @PutMapping("/assignments/{id}")
    public ResponseEntity<?> updateAssignment(
            @PathVariable("id") Long id,
            @RequestBody AssignmentUpdateRequest request) {

        Assignment updated = assignmentService.updateAssignment(id, request);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(AssignmentResponse.from(updated));
    }

    @DeleteMapping("/assignments/{id}")
    public ResponseEntity<?> deleteAssignment(@PathVariable("id") Long id) {
        boolean isDeleted = assignmentService.deleteAssignment(id);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}