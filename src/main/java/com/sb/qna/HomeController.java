package com.sb.qna;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// @Controller : 스프링부트한테 해당 클래스는 컬트롤러 역할이라고 알려준다.
@Controller
public class HomeController {
    @RequestMapping("/qna")
    @ResponseBody
    public String showHome() {
        return """
                <h1>안녕하세요.</h1>
                <input type="text" placeholder="입력해주세요."/>
                """;
    }

    @RequestMapping("/test")
    @ResponseBody
    public String showTest() {
        return """
                <h1>안녕하세요.</h1>
                <input type="text" placeholder="입력해주세요."/>
                """;
    }

    @GetMapping("/page1")
    @ResponseBody
    public String showGet() {
        return """
                <form method="POST" action="/page2">
                    <input type="number" name="age" placeholder="나이를 입력하세요." />
                    <input type="submit" value="page2로 POST 방식으로 이동" />
                </form>
                """;
    }

    @GetMapping("/page2")
    @ResponseBody
    public String showPage2Get(@RequestParam(defaultValue = "0") int age) {
        return """
                <h1>입력된 나이 : %d</h1>
                <h1>안녕하세요. GET 방식으로 오신걸 환영합니다.</h1>
                """.formatted(age);
    }

    @PostMapping("/page2")
    @ResponseBody
    public String showPage2Post(@RequestParam(defaultValue = "0") int age) {
        return """
                <h1>입력된 나이 : %d</h1>
                <h1>안녕하세요. POST 방식으로 오신걸 환영합니다.</h1>
                """.formatted(age);
    }
}
