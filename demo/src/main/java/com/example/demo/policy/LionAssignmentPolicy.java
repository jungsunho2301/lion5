package com.example.demo.policy;

public class LionAssignmentPolicy implements AssignmentPolicy {
    @Override
    public boolean canSubmit() {
        return true;
    }
}