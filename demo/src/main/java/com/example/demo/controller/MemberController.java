package com.example.demo.controller;

import com.example.demo.domain.Member;
import com.example.demo.dto.*;
import com.example.demo.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 1. Lion 등록 API (성공 201 / 중복 409)
    @PostMapping("/lions")
    public ResponseEntity<?> createLion(@RequestBody LionCreateRequest request) {
        Member created = memberService.createLion(request);
        if (created == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(MemberResponse.from(created));
    }

    // 2. Staff 등록 API (성공 201 / 중복 409)
    @PostMapping("/staffs")
    public ResponseEntity<?> createStaff(@RequestBody StaffCreateRequest request) {
        Member created = memberService.createStaff(request);
        if (created == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(MemberResponse.from(created));
    }

    // 3. ID 기반 단일 멤버 조회 API (성공 200 / 실패 404)
    @GetMapping("/{id}")
    public ResponseEntity<?> getMember(@PathVariable("id") Long id) {
        Member member = memberService.findById(id);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(MemberResponse.from(member));
    }

    // 4. Lion 정보 수정 API (성공 200 / 실패 404)
    @PutMapping("/lions/{id}")
    public ResponseEntity<?> updateLion(@PathVariable("id") Long id, @RequestBody LionUpdateRequest request) {
        Member updated = memberService.updateLion(id, request);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(MemberResponse.from(updated));
    }

    // 5. Staff 정보 수정 API (성공 200 / 실패 404)
    @PutMapping("/staffs/{id}")
    public ResponseEntity<?> updateStaff(@PathVariable("id") Long id, @RequestBody StaffUpdateRequest request) {
        Member updated = memberService.updateStaff(id, request);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(MemberResponse.from(updated));
    }

    // 6. 멤버 삭제 API (성공 204 / 실패 404)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable("id") Long id) {
        boolean isDeleted = memberService.deleteMember(id);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
