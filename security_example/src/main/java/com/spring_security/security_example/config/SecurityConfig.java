package com.spring_security.security_example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// 인가 설정을 진행하는 클래스 등록 annotation
@Configuration // filter가 걸러줄 수 있음
@EnableWebSecurity //security 설정
public class SecurityConfig {

    // BCrypt 암호화 메서드 - Bean 등록 (생성자로_)
    // 스프링 시큐리티는 사용자 인증(로그인)시 비밀번호에 대해 단방향 해시 암호화를 진행하여 저장되어 있는 비밀번호와 대조한다.
    // 따라서 회원가입시 비밀번호 항목에 대해서 암호화를 진행해야 한다.
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 특정 메소드를 작성하여 bean에 등록 -> 자동으로 filter에 security 설정 custom 가능
    // return type은 SecurityFilterChain이라는 interface, 인자는 HttpSecurity 객체로 받기
    // 예외처리까지

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        // 특정한 경로 설정
        // 특정한 경로로 요청이 왔을 때 이 경로는 모든 사용자에게 open
        // 특정한 admin 요청은 admin 사용자에게만 open
        // 다른 경로는 login을 진행 해야만 접근할 수 있도록
        // 람다식으로 작성

        http
                .authorizeHttpRequests((auth) -> auth
                        // 지정한 경로에 대해서는 모두에게 접근을 허용
                        .requestMatchers("/", "login", "/loginProc", "/join", "/joinProc").permitAll()
                        // 지정한 경로에 대해서 "ADMIN"이라는 역할을 가진 사람에게만 접근 허용
                        .requestMatchers("/admin").hasRole("ADMIN")
                        // 지정한 경로에 대해서 "ADMIN", "USER" 라는 역할을 가진 사람에게만 접근 허용
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                        // 나머지는 로그인한 사용자만 접근할 수 있도록
                        .anyRequest().authenticated()
                );

        // 폼 기반 로그인 설정 - formLogin
        /*
        http
                .formLogin((auth) -> auth.loginPage("/login")
                        // 로그인 폼의 sction 경로 지정 - 이 경로로 post 요청이 오면 스프링 시큐리티가 인증 처리
                        .loginProcessingUrl("/loginProc") // html form태그에서 post 방식으로 설정된 경로
                        // 로그인 관련 페이지는 모두 접근 허용
                        .permitAll()
                );
                */

        // http 기반 로그인 방식
        http
                .httpBasic(Customizer.withDefaults());

        //http
                //.csrf((auth) -> auth.disable());

        // 로그아웃 관리
        http
                .logout((auth)->auth.logoutUrl("/logout")
                        .logoutSuccessUrl(("/")));

        // 계층권한 : Role Hierarchy
        // 권한 A < B < C 계층 존재 시 설정 / 권한 별 접근 (메소드화 시켜서 할 수 있음)
        http
                .authorizeHttpRequests((auth)->auth
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/").hasAnyRole("A", "B", "C")
                        .requestMatchers("/manager").hasAnyRole("B", "C")
                        .requestMatchers("/admin").hasRole("C")
                        .anyRequest().authenticated());

        // 세션 관리 설정 추가
        http
                .sessionManagement((session) -> session
                        .maximumSessions(-1)  // -1로 설정하면 다중 로그인 허용
                        .maxSessionsPreventsLogin(false)  // 새로운 로그인을 허용하고, 기존 세션을 유지
                );

        return http.build(); // build해야지! -> 리턴 
    }
}
