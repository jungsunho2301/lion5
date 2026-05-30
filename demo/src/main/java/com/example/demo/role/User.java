package com.example.demo.role;

import com.example.demo.policy.AssignmentPolicy;

public abstract class User {
    private String name;
    private String major;
    private int generation;
    private String part;

    public User(String name, String major, int generation, String part) {
        this.name = name;
        this.major = major;
        this.generation = generation;
        this.part = part;
    }

    public String getName() { return name; }
    public String getMajor() { return major; }
    public int getGeneration() { return generation; }
    public String getPart() { return part; }

    public abstract AssignmentPolicy getAssignmentPolicy();
    public abstract String getDetails();
    public abstract String getRoleName();

    public void printInfo() {
        System.out.println("🎭 역할: " + getRoleName());
        System.out.println(getDetails());
        boolean canSubmit = getAssignmentPolicy().canSubmit();
        System.out.println("📝 과제 제출 가능 여부: " + (canSubmit ? "✅ 가능" : "❌ 불가"));
        System.out.println("--------------------------------------------------");
    }
}