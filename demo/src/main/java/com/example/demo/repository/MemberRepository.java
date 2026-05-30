package com.example.demo.repository;

import com.example.demo.role.User;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    void save(User user);
    Optional<User> findByName(String name);
    List<User> findAll();
    boolean existsByName(String name);
}