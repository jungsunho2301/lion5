package com.example.demo.assignment.service;

import com.example.demo.assignment.domain.Assignment; // 💡 이 부분의 패키지 경로가 수정되어야 합니다!
import com.example.demo.domain.Member;               // 기존 Member는 루트 domain에 있으므로 유지
import com.example.demo.assignment.dto.AssignmentCreateRequest;  // 새로 분리된 dto 패키지 지정
import com.example.demo.assignment.dto.AssignmentUpdateRequest;  // 새로 분리된 dto 패키지 지정
import com.example.demo.global.exception.AssignmentNotFoundException;
import com.example.demo.global.exception.MemberNotFoundException;
import com.example.demo.assignment.repository.AssignmentRepository; // 새로 분리된 repository 지정
import com.example.demo.repository.MemberRepository; // 기존 MemberRepository는 루트에 있으므로 유지
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final MemberRepository memberRepository;

    public AssignmentService(AssignmentRepository assignmentRepository, MemberRepository memberRepository) {
        this.assignmentRepository = assignmentRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Assignment createAssignment(Long memberId, AssignmentCreateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("과제를 등록할 멤버를 찾을 수 없습니다. ID: " + memberId));

        Assignment assignment = new Assignment(
                request.getTitle(),
                request.getDescription(),
                member
        );
        return assignmentRepository.save(assignment);
    }

    public List<Assignment> findAll() {
        return assignmentRepository.findAll();
    }

    public List<Assignment> searchByTitle(String keyword) {
        return assignmentRepository.findByTitleContaining(keyword);
    }

    public List<Assignment> findByMemberId(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException("해당 멤버를 찾을 수 없습니다. ID: " + memberId);
        }
        return assignmentRepository.findByMemberId(memberId);
    }

    public Assignment findById(Long id) {
        return assignmentRepository.findById(id)
                .orElseThrow(() -> new AssignmentNotFoundException("해당 ID의 과제를 찾을 수 없습니다: " + id));
    }

    @Transactional
    public Assignment updateAssignment(Long id, AssignmentUpdateRequest request) {
        Assignment assignment = findById(id);
        assignment.updateInfo(request.getTitle(), request.getDescription());
        return assignmentRepository.save(assignment);
    }

    @Transactional
    public boolean deleteAssignment(Long id) {
        if (!assignmentRepository.existsById(id)) {
            throw new AssignmentNotFoundException("해당 ID의 과제가 존재하지 않아 삭제할 수 없습니다: " + id);
        }
        assignmentRepository.deleteById(id);
        return true;
    }
}