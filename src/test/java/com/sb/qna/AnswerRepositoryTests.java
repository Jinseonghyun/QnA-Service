package com.sb.qna;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

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
        Answer a = answerRepository.findById(1).get(); // 1번 답변 가져와 -> (실제는 답변과 답변에 연관되어 있는 질문까지 가져온다.)
        assertThat(a.getContent()).isEqualTo("sbb는 질문답변 게시판입니다.");
    }

    @Test
    // 답변을 통해서 질문을 조회 (답변입장에서는 질문이 1개다 즉 단수)
    void 관련된_question_조회() {
        Answer a = answerRepository.findById(1).get(); // 1번 답변 가져와 -> (실제는 답변과 답변에 연관되어 있는 질문까지 가져온다.)
        Question q = a.getQuestion(); // 진짜 질문도 확실하게 가져오도록

        assertThat(q.getId()).isEqualTo(1);
    }

    @Test  // 질문 입장에서는 답변이 복수 
        // 하나의 질문에 여러개의 답변이 달릴 수 있다. (답변 조회)
    void question으로부터_관련된_답변들_조회() {
        // SELECT * FROM question WHERE id = 1;
        Question q = questionRepository.findById(1).get(); // 1번 질문을 조회 (데이터의 어떤 특정 쿼리 날림)

        // SELECT * FROM answer WHERE question_id = 1;
        List<Answer> answerList = q.getAnswerList(); // 질문에 해당하는 답변들을 담는다. (DB 연결이 끊김 -> Answer 타입을 EAGER로 바꾸어준다.)

        assertThat(answerList.size()).isEqualTo(2); // 1번 질문에 답변이 2개
        assertThat(answerList.get(0).getContent()).isEqualTo("sbb는 질문답변 게시판입니다."); // 1번 질문이 대한 0번째 답변이 제대로 맞는지 확인

    }
}
