package com.sb.qna;

import jakarta.persistence.*;

import java.time.LocalDateTime;

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
}