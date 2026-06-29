package com.example.demo.dto;

import com.example.demo.domain.Member;

public class MemberResponse {
    private Long id;
    private String name;
    private String major;
    private int generation;
    private String part;
    private String roleName;
    private String studentId;
    private String position;

    public MemberResponse(Long id, String name, String major, int generation, String part, String roleName, String studentId, String position) {
        this.id = id;
        this.name = name;
        this.major = major;
        this.generation = generation;
        this.part = part;
        this.roleName = roleName;
        this.studentId = studentId;
        this.position = position;
    }

    // 지침서 요구사항: from(Member) 팩토리 메서드 구현
    public static MemberResponse from(Member member) {
        if (member == null) return null;

        // roleType이 null이면 "미정", 아니면 "아기사자"/"운영진" 출력
        String roleName = (member.getRoleType() != null) ? member.getRoleType().getDisplayName() : "미정";

        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getMajor(),
                member.getGeneration(),
                member.getPart(),
                roleName, // 안전하게 변환된 문자열 전달
                member.getStudentId(),
                member.getPosition()
        );
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getMajor() { return major; }
    public int getGeneration() { return generation; }
    public String getPart() { return part; }
    public String getRoleName() { return roleName; }
    public String getStudentId() { return studentId; }
    public String getPosition() { return position; }
}
