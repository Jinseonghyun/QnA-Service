package com.sb.qna;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// @Controller : 스프링부트한테 해당 클래스는 컬트롤러 역할이라고 알려준다.
@Controller
public class HomeController {
    @RequestMapping("/qna")
    // @ResponseBody : 함수 실행 결과를 body에 그려준다.
    @ResponseBody
    public String showHome() {
        return "첫 시작";
    }
}
