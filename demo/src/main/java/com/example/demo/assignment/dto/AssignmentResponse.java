package com.example.demo.assignment.dto;

import com.example.demo.assignment.domain.Assignment;

public class AssignmentResponse {
    private Long id;
    private String title;
    private String description;
    private Long memberId;
    private String memberName;

    public AssignmentResponse(Long id, String title, String description, Long memberId, String memberName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.memberId = memberId;
        this.memberName = memberName;
    }

    public static AssignmentResponse from(Assignment assignment) {
        if (assignment == null) return null;

        return new AssignmentResponse(
                assignment.getId(),
                assignment.getTitle(),
                assignment.getDescription(),
                assignment.getMember() != null ? assignment.getMember().getId() : null,
                assignment.getMember() != null ? assignment.getMember().getName() : null
        );
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Long getMemberId() { return memberId; }
    public String getMemberName() { return memberName; }
}