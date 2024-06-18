package com.sb.qna;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Question findBySubject(String subject); // findBySubject 간단하게 명시만 해주면 사용가능
}

