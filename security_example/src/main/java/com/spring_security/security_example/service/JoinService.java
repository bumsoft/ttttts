package com.spring_security.security_example.service;

import com.spring_security.security_example.Entity.UserEntity;
import com.spring_security.security_example.dto.JoinDTO;
import com.spring_security.security_example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService { // 비즈니스 로직 처리  - db에 넣기 위한 관문

    @Autowired //userEntity table에 접근할 수 있는 userrepository 연결 시켜야 함
    private UserRepository userRepository; // 생성자 주입으로 바꾸기

    @Autowired // 암호화하는 class 필드 주입 
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    // 메소드 - 회원가입
    public void joinProcess(JoinDTO joinDTO){
        // db에 이미 동일한 username이 있는 회원이 있는지 검증해야함 - userrepository에 exixstby - 커스텀 메소드 작성
        boolean isUser = userRepository.existsByUsername(joinDTO.getUsername());
        if(isUser){
            return;
        }

        // dto를 entity로 반환 후 db에 연결
        UserEntity data = new UserEntity(); // Entity class 객체 하나 생성
        data.setUsername(joinDTO.getUsername());
        data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword())); // password는 암호화 해야지
        data.setRole("ROLE_USER");

        userRepository.save(data); // 회원정보 저장

    }
}
