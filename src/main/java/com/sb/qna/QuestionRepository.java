package com.sb.qna;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    // 제목이 필요해
    Question findBySubject(String subject); // findBySubject 간단하게 명시만 해주면 사용가능

    // 제목이랑 내용이 필요해
    Question findBySubjectAndContent(String subject, String content);

    // 조회할거야
    List<Question> findBySubjectLike(String subject);
}


