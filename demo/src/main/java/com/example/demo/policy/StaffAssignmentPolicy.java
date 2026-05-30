package com.example.demo.policy;

public class StaffAssignmentPolicy implements AssignmentPolicy {
    @Override
    public boolean canSubmit() {
        return false;
    }
}