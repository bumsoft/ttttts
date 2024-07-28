package com.spring_security.security_example.dto;

import com.spring_security.security_example.Entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private UserEntity userEntity;

    // 생성자
    public CustomUserDetails(UserEntity userEntity){
        this.userEntity = userEntity;
    }

    @Override // 사용자의 특정한 권한 리턴해주는 메소드 ???????????????????????????????????
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();
         collection.add(new GrantedAuthority() {
             @Override
             public String getAuthority() {
                 return userEntity.getRole();
             }
         });
        return collection;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    /////////
    @Override // 사용자의 ID가 만료되었는지
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override // 사용자의 ID 잠겨있는지
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override //
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
