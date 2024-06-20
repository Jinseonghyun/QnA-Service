package com.sb.qna;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest // 질문도 리셋 됐다가 답변도 같이 만들어 준다. (질문, 답벼 동시에 만들어줌)
public class AnswerRepositoryTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @BeforeEach
        // 모든 테스트의 전에 계속 반복해서 실행됨
    void beforeEach() {
        clearData();  // 데이터 정리
        createSampleData(); // 매번 샘플 데이터를 만든다.
    }

    private void clearData() { // 객체화 하지 않고 메서드 사용하려면 clearData 가 static으로 설정되야함
        QuestionRepositoryTests.clearData(questionRepository);
        answerRepository.deleteAll(); // 질문 삭제 후 재 생성 할 때 번호가 1번부터 시작할 수 있게 questionRepository 도 삭제
        answerRepository.truncateTable();
    }

    private void createSampleData() {
        // question 을 만든다.
        QuestionRepositoryTests.createSampleData(questionRepository); // 객체화 하지 않고 메서드 사용하려면 createSapleData 가 static으로 설정되야함

        Question q = questionRepository.findById(1).get(); // 1번과 관련된 질문을 가져온다.
        // 어떤 질문에 대한 답인지 알기 위해서 위의 코드를 작성한다.
        Answer a1 = new Answer();
        a1.setContent("sbb는 질문답변 게시판입니다.");
        a1.setQuestion(q); // 어떤 질문의 답변인지 알기 위해서 Question 객체가 필요하다.
        a1.setCreatedate(LocalDateTime.now());
        answerRepository.save(a1);

        Answer a2 = new Answer();
        a2.setContent("sbb에서는 주로 스프링관련 내용을 다룹니다.");
        a2.setQuestion(q); // 어떤 질문의 답변인지 알기 위해서 Question 객체가 필요하다.
        a2.setCreatedate(LocalDateTime.now());
        answerRepository.save(a2);
    }

    @Test
    void 저장() {
        Question q = questionRepository.findById(1).get(); // 1번과 관련된 질문을 가져온다.
        // 어떤 질문에 대한 답인지 알기 위해서 위의 코드를 작성한다.
        Answer a1 = new Answer();
        a1.setContent("sbb는 질문답변 게시판입니다.");
        a1.setQuestion(q); // 어떤 질문의 답변인지 알기 위해서 Question 객체가 필요하다.
        a1.setCreatedate(LocalDateTime.now());
        answerRepository.save(a1);

        Answer a2 = new Answer();
        a2.setContent("sbb에서는 주로 스프링관련 내용을 다룹니다.");
        a2.setQuestion(q); // 어떤 질문의 답변인지 알기 위해서 Question 객체가 필요하다.
        a2.setCreatedate(LocalDateTime.now());
        answerRepository.save(a2);
    }

    @Test
    void 조회() {
        Answer a = answerRepository.findById(1).get();

        assertThat(a.getContent()).isEqualTo("sbb는 질문답변 게시판입니다.");
    }
}
