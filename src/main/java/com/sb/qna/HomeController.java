package com.sb.qna;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// @Controller : 스프링부트한테 해당 클래스는 컬트롤러 역할이라고 알려준다.
@Controller
public class HomeController {
    private int increaseNo = 0;
    private List<Article> articles;

    public HomeController() {
        increaseNo = -1;
        articles = new ArrayList<>() {{
            add(new Article("제목1", "내용1"));
            add(new Article("제목2", "내용2"));
        }};
    }

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

    @GetMapping("/plus")
    @ResponseBody
    public int showplus(@RequestParam(defaultValue = "0") int a, @RequestParam(defaultValue = "0") int b) {
        return a + b;
    }

    @GetMapping("/plus2")
    @ResponseBody
    public int showplus2(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int a = Integer.parseInt(req.getParameter("a"));
        int b = Integer.parseInt(req.getParameter("b"));
        return a + b;
    }

    @GetMapping("/minus")
    @ResponseBody
    public int showminus(@RequestParam(defaultValue = "0") int a, @RequestParam(defaultValue = "0") int b) {
        return a - b;
    }

    @GetMapping("/increase")
    @ResponseBody
    public int showIncrease() {
        increaseNo++;
        return increaseNo;
    }

    @GetMapping("/gugudan")
    @ResponseBody
    // Integer 는 객체로 null 값을 허용한다.
    public String showGugudan(Integer dan, Integer limit) {
        if (dan == null) {
            dan = 9;
        }

        if (limit == null) {
            limit = 9;
        }

        String gugudanFormat = "";

        for (int i = 1; i <= 9; i++) {
            gugudanFormat += "%d * %d = %d<br>".formatted(dan, i, dan * i);
        }

        return gugudanFormat;
    }

    @GetMapping("/gugudan2")
    @ResponseBody
    // Integer 는 객체로 null 값을 허용한다.
    public String showGugudan2(Integer dan, Integer limit) {
        if (dan == null) {
            dan = 9;
        }

        if (limit == null) {
            limit = 9;
        }

        String gugudanFormat = "";

        for (int i = 1; i <= 9; i++) {
            gugudanFormat += "%d * %d = %d<br>".formatted(dan, i, dan * i);
        }

        // final 수식어가 붙으면 해당 변수는 상수처리 된다.
        final Integer finalDan = dan;
        return IntStream.rangeClosed(1, limit)
                .mapToObj(i -> "%d * %d = %d".formatted(finalDan, i, finalDan * i))
                .collect(Collectors.joining("<br>"));
    }

    @GetMapping("/mbti/{name}")
    @ResponseBody
    // URL 을 적고 그 다음 ? 을 기준으로 파라미터 작성
    // @PathVariable 를 활용해 파라미터값을 / 다음에 바로 주어도 읽는다.
    // http://localhost:8080/mbti?name=홍길동
    // http://localhost:8080/mbti/홍길동   (이렇게 쓸 수 있음)
    public String showMbti(@PathVariable String name) {
        return switch (name) {
            case "홍길동" -> {
                char j = 'J';
                yield "INF" + j;
            }
            case "홍길순" -> "ENFP";
            case "임꺽정", "신짱구" -> "ESFJ";
            default -> "모름";
        };
    }

    @GetMapping("/saveSessionAge/{name}/{value}")
    @ResponseBody
    public String saveSession(@PathVariable String name, @PathVariable String value, HttpServletRequest req) {
        HttpSession session = req.getSession();

        // req => 쿠키 => JSESSIONID => 섹션을 얻을 수 있다.
        session.setAttribute(name, value);

        return "세션변수의 %s의 값이 %s(으)로 설정되었습니다.".formatted(name, value);
    }

    @GetMapping("/getSession/{name}")
    @ResponseBody
    public String getSession(@PathVariable String name, HttpSession session) {
        String value = (String) session.getAttribute(name);

        return "세션변수의 %s의 값이 %s입니다.".formatted(name, value);
    }

    @GetMapping("/home/returnBoolean")
    @ResponseBody
    public boolean showReturnBoolean() {
        return false;
    }

    @GetMapping("/home/returnInArr")
    @ResponseBody
    public int[] showreturnInArr() {
        int[] arr = new int[] {10, 20, 30};
        return arr;
    }

    @GetMapping("/home/returnStringList")
    @ResponseBody
    public List<String> showreturnStringList() {{
        List<String> list = new ArrayList<>();
        list.add("안녕");
        list.add("반가워");
        list.add("어서와");
/*
// 위와 같은 코드
        List<String> list2 = new ArrayList<>();
        list.add("안녕");
        list.add("반가워");
        list.add("어서와");
*/
        return list;
    }};

    @GetMapping("/home/returnMap")
    @ResponseBody
    public Map<String, Object> showreturnMap() {
        Map<String, Object> map = new LinkedHashMap<>() {{
            put("id", 1);
            put("age", 5);
            put("name", "푸바오");

            put("related", new ArrayList<>() {{
                add(2);
                add(3);
                add(4);
            }});
        }};

        return map;
    }

    @GetMapping("/home/returnAnimal")
    @ResponseBody
    public Animal showreturnAnimal() {
        Animal animal = new Animal(1, 3, "포비", new ArrayList<>() {{
            add(2);
            add(3);
            add(4);
        }});

        System.out.println("animal = " + animal);
        return animal;
    }

    @GetMapping("/home/returnAnimal2")
    @ResponseBody
    public AnimalV2 showreturnAnimal2() {
        AnimalV2 animal = new AnimalV2(1, 3, "포비", new ArrayList<>() {{
            add(2);
            add(3);
            add(4);
        }});
        animal.setName(animal.getName() + "V2");

        return animal;
    }

    @GetMapping("/home/returnAnimalMapList")
    @ResponseBody
    public List<Map<String, Object>> showreturnAnimalMapList() {

        Map<String, Object> animalMap1 = new LinkedHashMap<>() {{
            put("id", 1);
            put("age", 5);
            put("name", "푸바오");

            put("related", new ArrayList<>() {{
                add(2);
                add(3);
                add(4);
            }});
        }};

        Map<String, Object> animalMap2 = new LinkedHashMap<>() {{
            put("id", 2);
            put("age", 8);
            put("name", "포비");

            put("related", new ArrayList<>() {{
                add(5);
                add(6);
                add(7);
            }});
        }};

        List<Map<String, Object>> list = new ArrayList<>();

        list.add(animalMap1);
        list.add(animalMap2);

        return list;
    }

    @GetMapping("/home/returnAnimalList")
    @ResponseBody
    public List<AnimalV2> showreturnnimalList() {
        AnimalV2 animal1 = new AnimalV2(1, 3, "포비", new ArrayList<>() {{
            add(2);
            add(3);
            add(4);
        }});

        AnimalV2 animal2 = new AnimalV2(2, 6, "푸바오", new ArrayList<>() {{
            add(5);
            add(6);
            add(7);
        }});

        List<AnimalV2> list = new ArrayList<>();
        list.add(animal1);
        list.add(animal2);

        return list;
    }

    @GetMapping("/addArticle")
    @ResponseBody
    public String addArticle(String title, String body) {
        int id = 1;
        Article article = new Article(title, body);

        System.out.println(article);

        articles.add(article);

        return "%d번 게시물이 추가되었습니다.".formatted(article.getId());
    }

    @GetMapping("/article/list")
    @ResponseBody
    public List<Article> getArticles() {
        return articles;
    }

    @GetMapping("/article/detail/{id}")
    @ResponseBody
    public Object getArticles(@PathVariable int id) {
        Article article = articles.stream()
                .filter(a -> a.getId() == id) // 게시물 id 와 내가 입력한 id가 일치한지 확인
                .findFirst()
                .orElse(null); // 입력한 번호의 게시물이 없으면 null 반환

        if (article == null) {
            return "d번 게시물이 존재하지 않습니다.".formatted(id);
        }

        return article;
    }

    @GetMapping("/article/modify/{id}")
    @ResponseBody
    public String modifyArticle(@PathVariable int id, String title, String body) {
        Article article = articles.stream()
                .filter(a -> a.getId() == id) // 게시물 id 와 내가 입력한 id가 일치한지 확인
                .findFirst()
                .orElse(null); // 입력한 번호의 게시물이 없으면 null 반환

        if (article == null) {
            return "d번 게시물이 존재하지 않습니다.".formatted(id);
        }

        // 제목과 내용을 입력하지 않으면 입력해달라는 요청을 보냄
        if(title == null) {
            return "제목을 입력해주세요.";
        }

        if(body == null) {
            return "내용을 입력해주세요.";
        }

        article.setTitle(title);
        article.setBody(body);

        return "%d번 게시물을 수정하였습니다.".formatted(article.getId());
    }

    @GetMapping("/article/delete/{id}")
    @ResponseBody
    public String deleteArticle(@PathVariable int id) {
        Article article = articles.stream()
                .filter(a -> a.getId() == id) // 게시물 id 와 내가 입력한 id가 일치한지 확인
                .findFirst()
                .orElse(null); // 입력한 번호의 게시물이 없으면 null 반환

        if (article == null) {
            return "d번 게시물이 존재하지 않습니다.".formatted(id);
        }

        articles.remove(article);

        return "%d번 게시물을 삭제하였습니다.".formatted(id);
    }

    @GetMapping("/addPersonOldWay")
    @ResponseBody
    public Person addPersonOldWay(int id, int age, String name) {
        Person p = new Person(id, age, name);

        return p;
    }

    @GetMapping("/addPerson")
    @ResponseBody
    public Person addPerson(Person p) {
        return p;
    }
}

class Animal {
    private final int id;
    private final int age;
    private final String name;
    private final List<Integer> related;

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getRelated() {
        return related;
    }

    public Animal(int id, int age, String name, List<Integer> related) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.related = related;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", related=" + related +
                '}';
    }
}

@AllArgsConstructor // 생성자 생성
//@NoArgsConstructor  // 비어 있는 생성자 생성 우리는 final 이기에 생성자 있어야함
@Data
class AnimalV2 {
    private final int id;
    private final int age;
    private  String name;
    private final List<Integer> related;
}

@AllArgsConstructor
//@Getter
//@ToString
//@Setter  // 아래 Data 로 한번에
@Data
class Article {
    private static int lastId;
    private final int id;  // 파이널이기에 롬복이 세터 안만듬
    private String title; // 띄어쓰기 제대로 하세요
    private String body; // 이 기에가 아니라 이기에 입니다

    static {
        lastId = 0;
    }

    public Article(String title, String body) {
        this(++lastId, title, body); // 메서드 오버로딩
    }
// AllArgsConstructor 으로 대체
//    public Article(int id, String title, String body) {
//        this.id = id;
//        this.title = title;
//        this.body = body;
//    }
}

@AllArgsConstructor
@Getter
class Person {
    private int id;
    private int age;
    private String name;
}
