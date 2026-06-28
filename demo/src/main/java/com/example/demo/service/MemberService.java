package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.domain.RoleType;
import com.example.demo.dto.*;
import com.example.demo.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 1. Lion 등록 (성공 시 생성 객체 반환 / 중복 시 null 반환 패턴 유지)
    @Transactional
    public Member createLion(LionCreateRequest request) {
        if (memberRepository.existsByName(request.getName())) {
            return null;
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
        // save()가 실행되면서 영속성 컨텍스트 및 DB IDENTITY에 의해 id가 자동으로 채워집니다.
        return memberRepository.save(lion);
    }

    // 2. Staff 등록
    @Transactional
    public Member createStaff(StaffCreateRequest request) {
        if (memberRepository.existsByName(request.getName())) {
            return null;
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

    // 3. ID 기반 단일 조회 (새로 추가된 지침 패턴)
    public Member findById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    // 4. Lion 수정 (id 기반으로 변경)
    @Transactional
    public Member updateLion(Long id, LionUpdateRequest request) {
        Member member = memberRepository.findById(id).orElse(null);
        // 대상이 없거나, 해당 id의 멤버가 LION이 아닐 경우 null 반환
        if (member == null || member.getRoleType() != RoleType.LION) {
            return null;
        }

        member.updateInfo(request.getMajor(), request.getGeneration(), request.getPart());
        member.updateStudentId(request.getStudentId());
        return memberRepository.save(member);
    }

    // 5. Staff 수정 (id 기반으로 변경)
    @Transactional
    public Member updateStaff(Long id, StaffUpdateRequest request) {
        Member member = memberRepository.findById(id).orElse(null);
        // 대상이 없거나, 해당 id의 멤버가 STAFF가 아닐 경우 null 반환
        if (member == null || member.getRoleType() != RoleType.STAFF) {
            return null;
        }

        member.updateInfo(request.getMajor(), request.getGeneration(), request.getPart());
        member.updatePosition(request.getPosition());
        return memberRepository.save(member);
    }

    // 6. 멤버 삭제 (id 기반으로 변경 및 7주차 에러 조건 유지)
    @Transactional
    public boolean deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            return false;
        }
        memberRepository.deleteById(id);
        return true;
    }
}