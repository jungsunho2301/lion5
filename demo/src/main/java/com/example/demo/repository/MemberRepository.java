package com.example.demo.repository;

import com.example.demo.role.User;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    void save(User user);
    Optional<User> findByName(String name);
    List<User> findAll();
    boolean existsByName(String name);

    // 7주차 추가 인터페이스 지침 스펙 적용
    void updateByName(String name, User member);
    boolean deleteByName(String name);
}