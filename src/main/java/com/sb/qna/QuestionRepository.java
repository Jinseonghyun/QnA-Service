package com.sb.qna;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer>, RepositcoryUtil{

    @Transactional // 삭제하는 거기에 붙이는게 좋다.
    @Modifying // 스프링 부트한테 수정에 관련된 거라는 걸 알려준다.
    @Query(value = "ALTER TABLE question AUTO_INCREMENT = 1", nativeQuery = true) // 생성되는 게시물 번호를 1로 해라
    void truncate(); //데이터가 삭제되고 이후에 데이터가 추가되면 데이터를 1번부터 나오게 할려고

    // 제목이 필요해
    Question findBySubject(String subject); // findBySubject 간단하게 명시만 해주면 사용가능

    // 제목이랑 내용이 필요해
    Question findBySubjectAndContent(String subject, String content);

    // 조회할거야
    List<Question> findBySubjectLike(String subject);

}


