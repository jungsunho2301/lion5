package com.example.demo.controller;

import com.example.demo.domain.Member;
import com.example.demo.dto.*;
import com.example.demo.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getMembers(@RequestParam(value = "part", required = false) String part) {
        List<Member> members;
        if (part != null && !part.trim().isEmpty()) {
            members = memberService.findByPart(part);
        } else {
            members = memberService.findAll();
        }

        List<MemberResponse> response = members.stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/lions")
    public ResponseEntity<MemberResponse> createLion(@RequestBody LionCreateRequest request) {
        Member created = memberService.createLion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(MemberResponse.from(created));
    }

    @PostMapping("/staffs")
    public ResponseEntity<MemberResponse> createStaff(@RequestBody StaffCreateRequest request) {
        Member created = memberService.createStaff(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(MemberResponse.from(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable("id") Long id) {
        Member member = memberService.findById(id);
        return ResponseEntity.ok(MemberResponse.from(member));
    }

    @PutMapping("/lions/{id}")
    public ResponseEntity<MemberResponse> updateLion(@PathVariable("id") Long id, @RequestBody LionUpdateRequest request) {
        Member updated = memberService.updateLion(id, request);
        return ResponseEntity.ok(MemberResponse.from(updated));
    }

    @PutMapping("/staffs/{id}")
    public ResponseEntity<MemberResponse> updateStaff(@PathVariable("id") Long id, @RequestBody StaffUpdateRequest request) {
        Member updated = memberService.updateStaff(id, request);
        return ResponseEntity.ok(MemberResponse.from(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable("id") Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}