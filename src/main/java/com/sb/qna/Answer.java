package com.sb.qna;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Answer { // Answer 테이블은 해당 질문을 참조
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdate;

    @ManyToOne // 질문 하나당 답변이 여러개 (해당 질문에 대해서 특정 답변 존재)
    // 나중에 JOIN 할 때 필요하다.
    //@JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)) // 외래키 제거
    private Question question; // 질문이 있으면 그 질문에 맞는 답변주기 위해 (해당 질문 참조하는 녀석)
}
