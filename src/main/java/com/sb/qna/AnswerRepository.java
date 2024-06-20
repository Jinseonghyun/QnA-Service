package com.sb.qna;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AnswerRepository extends JpaRepository<Answer, Integer>, RepositcoryUtil {

    @Transactional // 삭제하는 거기에 붙이는게 좋다.
    @Modifying // 스프링 부트한테 수정에 관련된 거라는 걸 알려준다.
    @Query(value = "ALTER TABLE answer AUTO_INCREMENT = 1", nativeQuery = true) // 생성되는 게시물의 번호를 1로 해라
    void truncate(); // 이거 지우면 안됨, truncateTable 하면 자동으로 이 코드가 실행!!

}
