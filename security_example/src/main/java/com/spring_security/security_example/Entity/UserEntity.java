package com.spring_security.security_example.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //

    @Column(unique = true)  // username 중복 불가능하게
    private String username;
    private String password;
    private String role; // 사용자의 권한 나누는데 사용 - admin, 일반 user

}
