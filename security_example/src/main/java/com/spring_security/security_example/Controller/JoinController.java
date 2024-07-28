package com.spring_security.security_example.Controller;

import com.spring_security.security_example.dto.JoinDTO;
import com.spring_security.security_example.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JoinController { // 회원가입

    // 의존성 주입 - 필드주입/ 생성자 주입
    @Autowired
    private JoinService joinService;

    @GetMapping("/join")
    public String joinP(){

        return "join";
    }

    // 위의 getmapping을 통해 join html이 실행되고, /joinproc 경로로 post방식으로 data 받아야함(html 코드 참고)
    // (추후에) 회원가입 실패하면 다시 회원가입 페이지로 돌아가는 로작 작성하기
    @PostMapping("/joinProc")
    public String joinProcess(JoinDTO joinDTO){ // 이 class에서 data 받기
        System.out.println(joinDTO.getUsername());
        joinService.joinProcess(joinDTO); // 회원정보 db에 넣어주는 메소드 (service에 작성하고 가져다 쓰기)
        return "redirect:/login";
    }
}
