package com.example.demo.dto;

public class StaffCreateRequest {
    private String name;
    private String major;
    private int generation;
    private String part;
    private String position; // 지침서 스펙 명시

    public StaffCreateRequest() {}

    public String getName() { return name; }
    public String getMajor() { return major; }
    public int getGeneration() { return generation; }
    public String getPart() { return part; }
    public String getPosition() { return position; }
}