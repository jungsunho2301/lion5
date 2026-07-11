package com.example.demo.assignment.controller; // 💡 패키지 경로 분리 지침 반영

import com.example.demo.assignment.domain.Assignment; // 💡 assignment 하위 domain 참조
import com.example.demo.assignment.dto.AssignmentCreateRequest; // 💡 assignment 하위 dto 참조
import com.example.demo.assignment.dto.AssignmentResponse;      // 💡 assignment 하위 dto 참조
import com.example.demo.assignment.dto.AssignmentUpdateRequest;   // 💡 assignment 하위 dto 참조
import com.example.demo.assignment.service.AssignmentService;   // 💡 assignment 하위 service 참조
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
    public ResponseEntity<AssignmentResponse> createAssignment(
            @PathVariable("memberId") Long memberId,
            @RequestBody AssignmentCreateRequest request) {
        Assignment created = assignmentService.createAssignment(memberId, request);
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

    @GetMapping("/assignments")
    public ResponseEntity<List<AssignmentResponse>> getAllAssignments() {
        List<Assignment> assignments = assignmentService.findAll();
        List<AssignmentResponse> response = assignments.stream()
                .map(AssignmentResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/assignments/search")
    public ResponseEntity<List<AssignmentResponse>> searchAssignments(@RequestParam("keyword") String keyword) {
        List<Assignment> assignments = assignmentService.searchByTitle(keyword);
        List<AssignmentResponse> response = assignments.stream()
                .map(AssignmentResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/assignments/{id}")
    public ResponseEntity<AssignmentResponse> getAssignment(@PathVariable("id") Long id) {
        Assignment assignment = assignmentService.findById(id);
        return ResponseEntity.ok(AssignmentResponse.from(assignment));
    }

    @PutMapping("/assignments/{id}")
    public ResponseEntity<AssignmentResponse> updateAssignment(
            @PathVariable("id") Long id,
            @RequestBody AssignmentUpdateRequest request) {
        Assignment updated = assignmentService.updateAssignment(id, request);
        return ResponseEntity.ok(AssignmentResponse.from(updated));
    }

    @DeleteMapping("/assignments/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable("id") Long id) {
        assignmentService.deleteAssignment(id);
        return ResponseEntity.noContent().build();
    }
}