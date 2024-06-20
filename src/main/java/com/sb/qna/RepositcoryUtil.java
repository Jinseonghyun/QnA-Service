package com.sb.qna;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RepositcoryUtil {
    @Transactional // 삭제하는 거기에 붙이는게 좋다.
    @Modifying // 스프링 부트한테 수정에 관련된 거라는 걸 알려준다.
    @Query(value = "SET FOREIGN_KEY_CHECKS = 0", nativeQuery = true) // SET FOREIGN_KEY_CHECKS = 0 외래키 비활성화
    void disableForeignKeyChecks();

    @Transactional // 삭제하는 거기에 붙이는게 좋다.
    @Modifying // 스프링 부트한테 수정에 관련된 거라는 걸 알려준다.
    @Query(value = "SET FOREIGN_KEY_CHECKS = 1", nativeQuery = true)  // SET FOREIGN_KEY_CHECKS = 1 외래키 활성화
    void enableForeignKeyChecks();

    // default 메서드를 구현하면 인터페이스 내에 구상 메서드를 정의하는게 가능하다.
    default void truncateTable()  { // default 로 메서드 만든다. truncate 중복 되있다. 없애주기 위해
        disableForeignKeyChecks();
        truncate();
        enableForeignKeyChecks();
    }

    void truncate();
}
