package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.role.Lion;
import com.example.demo.role.Staff;
import com.example.demo.role.User;
import com.example.demo.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

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
        User created = memberService.createLion(request);
        if (created == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(LionResponse.from((Lion) created));
    }

    // 2. Staff 등록 API (성공 201 / 중복 409)
    @PostMapping("/staffs")
    public ResponseEntity<?> createStaff(@RequestBody StaffCreateRequest request) {
        User created = memberService.createStaff(request);
        if (created == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(StaffResponse.from((Staff) created));
    }

    // 3. 이름으로 단일 멤버 조회 API (성공 200 / 실패 404)
    @GetMapping("/{name}")
    public ResponseEntity<?> getMember(@PathVariable("name") String name) {
        Optional<User> memberOpt = memberService.findByName(name);
        if (memberOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        User member = memberOpt.get();
        if (member instanceof Lion) {
            return ResponseEntity.ok(LionResponse.from((Lion) member));
        } else if (member instanceof Staff) {
            return ResponseEntity.ok(StaffResponse.from((Staff) member));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 4. Lion 정보 수정 API (성공 200 / 실패 404)
    @PutMapping("/lions/{name}")
    public ResponseEntity<?> updateLion(@PathVariable("name") String name, @RequestBody LionUpdateRequest request) {
        User updated = memberService.updateLion(name, request);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(LionResponse.from((Lion) updated));
    }

    // 5. Staff 정보 수정 API (성공 200 / 실패 404)
    @PutMapping("/staffs/{name}")
    public ResponseEntity<?> updateStaff(@PathVariable("name") String name, @RequestBody StaffUpdateRequest request) {
        User updated = memberService.updateStaff(name, request);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(StaffResponse.from((Staff) updated));
    }

    // 6. 멤버 삭제 API (성공 204 / 실패 404)
    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteMember(@PathVariable("name") String name) {
        boolean isDeleted = memberService.deleteMember(name);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}