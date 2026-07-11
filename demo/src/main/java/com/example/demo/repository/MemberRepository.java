package com.example.demo.repository;

import com.example.demo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // 💡 이 메서드가 선언되어 있어야 MemberService에서 에러가 나지 않습니다!
    boolean existsByName(String name);
    List<Member> findByPart(String part);
}