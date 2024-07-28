package com.spring_security.security_example.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Iterator;

@Controller
public class MainController {
    @GetMapping("/")
    public String mainP(Model model){
// 사용자가 로그인을 진행한 후 사용자 정보는 SecurityContextHolder에 의해 서버세션에 관리된다.


        // 세션 사용자 ID, Role 정보 - db 에서 가져오는 것이기 때문에 Model 객체 필요
        // 1. id 가져오기
        // 현재 인증된 사용자의 ID를 가져옵니다. SecurityContextHolder는 현재 스레드의 보안 컨텍스트를 제공합니다.
        // getName() 메서드는 현재 인증된 사용자의 이름(일반적으로 사용자 ID)를 반환합니다.
        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        // 2. role 가져오기
        // SecurityContextHolder.getContext().getAuthentication();: 현재 인증된 사용자의 Authentication 객체를 가져옵니다.
        // 이 객체에는 사용자에 대한 인증 정보가 포함되어 있습니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 사용자의 권한(역할) 정보를 GrantedAuthority 객체의 컬렉션으로 가져옵니다.
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        // 권한 컬렉션의 이터레이터를 생성합니다.
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        // 첫 번째 권한을 가져옵니다. (여기서는 사용자가 단일 역할을 가질 것으로 가정합니다.)
        GrantedAuthority auth = iter.next();
        // 권한의 이름(역할 이름)을 문자열로 가져옵니다.
        String role = auth.getAuthority();

        model.addAttribute("id", id);
        model.addAttribute("role", role);

        return "main";
    }
}
