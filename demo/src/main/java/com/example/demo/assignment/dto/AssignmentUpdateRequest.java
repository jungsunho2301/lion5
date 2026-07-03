package com.example.demo.assignment.dto;

public class AssignmentUpdateRequest {
    private String title;
    private String description;

    public AssignmentUpdateRequest() {}

    public String getTitle() { return title; }
    public String getDescription() { return description; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
}