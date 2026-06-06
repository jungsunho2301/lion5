package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.repository.MemberRepository;
import com.example.demo.role.Lion;
import com.example.demo.role.Staff;
import com.example.demo.role.User;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public User createLion(LionCreateRequest request) {
        if (memberRepository.existsByName(request.getName())) {
            return null;
        }
        Lion lion = new Lion(request.getName(), request.getMajor(), request.getGeneration(), request.getPart(), request.getStudentId());
        memberRepository.save(lion);
        return lion;
    }

    public User createStaff(StaffCreateRequest request) {
        if (memberRepository.existsByName(request.getName())) {
            return null;
        }
        // request.getPosition() 값을 Staff의 role 생성자 파라미터로 안전하게 넘겨줍니다.
        Staff staff = new Staff(request.getName(), request.getMajor(), request.getGeneration(), request.getPart(), request.getPosition());
        memberRepository.save(staff);
        return staff;
    }

    public User updateLion(String name, LionUpdateRequest request) {
        Optional<User> memberOpt = memberRepository.findByName(name);
        if (memberOpt.isEmpty() || !(memberOpt.get() instanceof Lion)) {
            return null;
        }
        Lion updatedLion = new Lion(name, request.getMajor(), request.getGeneration(), request.getPart(), request.getStudentId());
        memberRepository.updateByName(name, updatedLion);
        return updatedLion;
    }

    public User updateStaff(String name, StaffUpdateRequest request) {
        Optional<User> memberOpt = memberRepository.findByName(name);
        if (memberOpt.isEmpty() || !(memberOpt.get() instanceof Staff)) {
            return null;
        }
        // 수정 시에도 가이드라인 명칭(position)을 도메인 명칭(role)에 정확히 매핑합니다.
        Staff updatedStaff = new Staff(name, request.getMajor(), request.getGeneration(), request.getPart(), request.getPosition());
        memberRepository.updateByName(name, updatedStaff);
        return updatedStaff;
    }

    public boolean deleteMember(String name) {
        return memberRepository.deleteByName(name);
    }

    public Optional<User> findByName(String name) {
        return memberRepository.findByName(name);
    }
}