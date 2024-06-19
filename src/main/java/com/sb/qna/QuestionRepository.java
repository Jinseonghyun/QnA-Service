package com.sb.qna;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Transactional // 삭제하는 거기에 붙이는게 좋다.
    @Modifying // 스프링 부트한테 수정에 관련된 거라는 걸 알려준다.
    @Query(value = "SET FOREIGN_KEY_CHECKS = 0", nativeQuery = true) // SET FOREIGN_KEY_CHECKS = 0 외래키 비활성화
    void disableForeignKeyChecks();

    @Transactional // 삭제하는 거기에 붙이는게 좋다.
    @Modifying // 스프링 부트한테 수정에 관련된 거라는 걸 알려준다.
    @Query(value = "SET FOREIGN_KEY_CHECKS = 1", nativeQuery = true)  // SET FOREIGN_KEY_CHECKS = 1 외래키 활성화
    void enableForeignKeyChecks();

    @Transactional // 삭제하는 거기에 붙이는게 좋다.
    @Modifying // 스프링 부트한테 수정에 관련된 거라는 걸 알려준다.
    @Query(value = "truncate question", nativeQuery = true)
    void truncate();

    // 제목이 필요해
    Question findBySubject(String subject); // findBySubject 간단하게 명시만 해주면 사용가능

    // 제목이랑 내용이 필요해
    Question findBySubjectAndContent(String subject, String content);

    // 조회할거야
    List<Question> findBySubjectLike(String subject);

}


