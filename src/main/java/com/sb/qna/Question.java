package com.sb.qna;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity // 아래 Question 클래스는 엔티티 클래스이다.
// 아래 클래스와 1:1 로 매칭되는 테이블이 DB에 없다면, 자동으로 생성되어야 한다.
public class Question {

    @Id // 해당 아이디 값을 프라이머리 키로 설정해줌
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Integer id;

    @Column(length = 200) // 애의 너비를 늘리고 싶다. varchar(200)
    private String subject;

    @Column(columnDefinition = "TEXT") // 본문이 많기 때문에 TEXT
    private String content;

    private LocalDateTime createDate; // 생성 날짜 Datetime

    // 하나의 질문에 여러개 답변                             // , fetch = FetchType.EAGER (타입을 강제적으로 LAZY 에서 EAGER로 바꿔준다. (지연로딩 -> 즉시 로딩))
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) // cascade 이용해서 질문이 지워지면 아래에 답변도 다 지워지도록
    private List<Answer> answerList = new ArrayList<>(); // 위의 cascade 때문에 answerList가 안생김  (빈 리스트가 들어간다.)
}