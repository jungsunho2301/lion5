package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.domain.RoleType;
import com.example.demo.dto.*;
import com.example.demo.global.exception.DuplicateMemberException;
import com.example.demo.global.exception.MemberNotFoundException;
import com.example.demo.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public List<Member> findByPart(String part) {
        return memberRepository.findByPart(part);
    }

    @Transactional
    public Member createLion(LionCreateRequest request) {
        if (memberRepository.existsByName(request.getName())) {
            throw new DuplicateMemberException("이미 존재하는 멤버 이름입니다: " + request.getName());
        }
        Member lion = new Member(
                request.getName(),
                request.getMajor(),
                request.getGeneration(),
                request.getPart(),
                RoleType.LION,
                request.getStudentId(),
                null
        );
        return memberRepository.save(lion);
    }

    @Transactional
    public Member createStaff(StaffCreateRequest request) {
        if (memberRepository.existsByName(request.getName())) {
            throw new DuplicateMemberException("이미 존재하는 멤버 이름입니다: " + request.getName());
        }
        Member staff = new Member(
                request.getName(),
                request.getMajor(),
                request.getGeneration(),
                request.getPart(),
                RoleType.STAFF,
                null,
                request.getPosition()
        );
        return memberRepository.save(staff);
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("해당 ID의 멤버를 찾을 수 없습니다: " + id));
    }

    @Transactional
    public Member updateLion(Long id, LionUpdateRequest request) {
        Member member = findById(id);
        if (member.getRoleType() != RoleType.LION) {
            throw new MemberNotFoundException("해당 멤버는 LION이 아닙니다.");
        }
        member.updateInfo(request.getMajor(), request.getGeneration(), request.getPart());
        member.updateStudentId(request.getStudentId());
        return memberRepository.save(member);
    }

    @Transactional
    public Member updateStaff(Long id, StaffUpdateRequest request) {
        Member member = findById(id);
        if (member.getRoleType() != RoleType.STAFF) {
            throw new MemberNotFoundException("해당 멤버는 STAFF가 아닙니다.");
        }
        member.updateInfo(request.getMajor(), request.getGeneration(), request.getPart());
        member.updatePosition(request.getPosition());
        return memberRepository.save(member);
    }

    @Transactional
    public boolean deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new MemberNotFoundException("해당 ID의 멤버가 존재하지 않아 삭제할 수 없습니다: " + id);
        }
        memberRepository.deleteById(id);
        return true;
    }
}