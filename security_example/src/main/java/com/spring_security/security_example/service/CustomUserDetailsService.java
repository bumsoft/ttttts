package com.spring_security.security_example.service;

import com.spring_security.security_example.Entity.UserEntity;
import com.spring_security.security_example.dto.CustomUserDetails;
import com.spring_security.security_example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
// spring에서 기본 제공하는 UserDetailsService 상속 받아서 사용
    @Autowired  // DB에서 정보 가져오려면 필요함 (db 기반 로그인 검증을 위해서)
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // username을 받아 db에서 조회 - 특정 method 필요(UserRepository에서 작성 후 가져와서 사용)
        UserEntity userdata = userRepository.findByUsername(username);
        if(userdata != null){ // 로그인 진행
            return new CustomUserDetails(userdata);
        }
        return null;
    }
}
