package com.sb.qna;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SbbApplicationTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testJpa() {
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);  // 첫번째 질문 저장

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q2);  // 두번째 질문 저장

        // id는 최소 0번보다 크다.
        assertThat(q1.getId()).isGreaterThan(0);
        // 두 번째 id는 첫 번째 id보다 크다.
        assertThat(q2.getId()).isGreaterThan(q1.getId());
    }

    @Test
    void testJpa2() {
        // findAll() : SELECT * FROM question;
        List<Question> all = questionRepository.findAll();
        assertEquals(2, all.size());

        Question q = all.get(0); // 위에서 받은 것 중 첫 번째 받은거
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }

    @Test
    void testJpa3() { // 쿼리문에서 where 뒤에 조건에 "sbb가 무엇인가요?" 넣는 것과 같다. JPA 가 해준다. (내용 자체를 객체에 리턴)
        Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
        assertEquals(1, q.getId()); // findBySubject로 제목을 한개만 가져오겠다.
    }
        // 쿼리문의 where 절에 조건에 대해서
    @Test // findBySubjectAndContent (여러 칼럼을 AND 로 검색), findBySubjectOrContent (여러 칼럼을 OR로 검색)
    void testJpa4() { // 쿼리문에서 where 뒤에 조건에 "sbb가 무엇인가요?" 넣는 것과 같다. JPA 가 해준다. (내용 자체를 객체에 리턴)
        Question q = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "id는 자동으로 생성되나요?");
        assertEquals(1, q.getId()); // findBySubject로 제목을 한개만 가져오겠다.
    }

    @Test
    void testJpa5() {
        List<Question> qList = questionRepository.findBySubjectLike("sbb%"); // sbb로 시작하는 애들 다 가져와
        Question q = qList.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }
}
