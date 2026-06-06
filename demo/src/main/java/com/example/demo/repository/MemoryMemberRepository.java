package com.example.demo.repository;

import com.example.demo.role.User;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemoryMemberRepository implements MemberRepository {
    private final List<User> store = new ArrayList<>();

    @Override
    public void save(User user) {
        store.add(user);
    }

    @Override
    public Optional<User> findByName(String name) {
        return store.stream()
                .filter(user -> user.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store);
    }

    @Override
    public boolean existsByName(String name) {
        return store.stream().anyMatch(user -> user.getName().equals(name));
    }

    // 1. 리스트를 순회하며 이름이 일치하면 해당 인덱스를 교체한다.
    @Override
    public void updateByName(String name, User member) {
        for (int i = 0; i < store.size(); i++) {
            if (store.get(i).getName().equals(name)) {
                store.set(i, member);
                return;
            }
        }
    }

    // 2. removeIf 를 사용해 이름이 일치하는 멤버를 제거한다.
    @Override
    public boolean deleteByName(String name) {
        return store.removeIf(user -> user.getName().equals(name));
    }
}