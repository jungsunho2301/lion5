package com.example.demo.service;

import com.example.demo.repository.MemberRepository;
import com.example.demo.role.User;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    // 생성자가 1개이므로 과제 지침대로 @Autowired 생략 완료!
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public boolean registerMember(User user) {
        if (memberRepository.existsByName(user.getName())) {
            return false;
        }
        memberRepository.save(user);
        return true;
    }

    public List<User> getAllMembers() {
        return memberRepository.findAll();
    }

    public Optional<User> searchMemberByName(String name) {
        return memberRepository.findByName(name);
    }
}