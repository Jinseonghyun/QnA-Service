package com.sb.qna;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Question findBySubject(String subject); // findBySubject 간단하게 명시만 해주면 사용가능

    Question findBySubjectAndContent(String subject, String content);

    List<Question> findBySubjectLike(String subject);
}


