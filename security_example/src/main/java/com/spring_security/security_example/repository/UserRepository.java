package com.spring_security.security_example.repository;

import com.spring_security.security_example.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> { // Entity를 가져간다 - db로
// Entity, Entity의 ID값의 data type
    // Repositroy는 Entity랑 묶음

    // 회원 중복 검사를 위해서 메소드를 custom해서 만들어야함.
    boolean existsByUsername(String username);

    UserEntity findByUsername(String username);
}
