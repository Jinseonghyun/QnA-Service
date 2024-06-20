package com.sb.qna;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest // 질문도 리셋 됐다가 답변도 같이 만들어 준다. (질문, 답벼 동시에 만들어줌)
public class AnswerRepositoryTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;
    private static int lastSampleDataId;

    @BeforeEach
        // 모든 테스트의 전에 계속 반복해서 실행됨
    void beforeEach() {
        clearData();  // 데이터 정리
        createSampleData(); // 매번 샘플 데이터를 만든다.
    }

    private void clearData() { // 객체화 하지 않고 메서드 사용하려면 clearData 가 static으로 설정되야함
        QuestionRepositoryTests.clearData(questionRepository); // 질문 삭제 후 재 생성 할 때 번호가 1번부터 시작할 수 있게 questionRepository 도 삭제

        answerRepository.disableForeignKeyChecks(); // questionRepository.disableForeignKeyChecks 질문에는 외래키가 없다. 키외래키 비활성화
        answerRepository.truncate(); // questionRepository.truncate() 이렇게 해버리면 답변이 질문을 삭제한다 -> (질문이 없는 상태가 됨)
        answerRepository.enableForeignKeyChecks(); // questionRepository.enableForeignKeyChecks() 질문에는 외래키가 없다.  외래키 활성화
    }

    private void createSampleData() {
        // question 을 만든다.
        QuestionRepositoryTests.createSampleData(questionRepository); // 객체화 하지 않고 메서드 사용하려면 createSapleData 가 static으로 설정되야함
    }

    @Test
    void 저장() {
        Question q = questionRepository.findById(2).get(); // 2번과 관련된 2번 질문을 가져온다.
        // 어떤 질문에 대한 답인지 알기 위해서 위의 코드를 작성한다.
        Answer a = new Answer();
        a.setContent("네 자동으로 생성됩니다.");
        a.setQuestion(q); // 어떤 질문의 답변인지 알기 위해서 Question 객체가 필요하다.
        a.setCreatedate(LocalDateTime.now());
        answerRepository.save(a);
    }
}
