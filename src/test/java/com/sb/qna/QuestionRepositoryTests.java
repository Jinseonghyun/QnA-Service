package com.sb.qna;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest // TDD 코드들이 독릭접으로 정상 작동을 해야한다.
public class QuestionRepositoryTests {

    @Autowired
    private QuestionRepository questionRepository; // 아래에서 이 변수를 사용하는 메서드가 static 이기에 변수도 static 붙히는게 좋지만 관례를 보기 위해 static 안함
    private static int lastSampleDataId; // 마지막 테스트 데이터 id 가져오기 위해


    @Test
    void contextLoads() {
    }

    @BeforeEach // 모든 테스트의 전에 계속 반복해서 실행됨
    void beforeEach() {
        clearData();  // 데이터 정리
        createSampleData(); // 매번 샘플 데이터를 만든다.
    }

    // @BeforeEach 안에서 질문 리셋 할 수 있도록
    private void clearData() {
        questionRepository.disableForeignKeyChecks(); // 외래키 비활성화
        questionRepository.truncate(); // truncate 를 활용해 데이터 삭제해준다.
        questionRepository.enableForeignKeyChecks(); // 외래키 활성화
    }

    // 외부에서 호출 (밖에서 사용할 수 있게 public) (질문 리셋)
    public static void clearData(QuestionRepository questionRepository) { // 변수 questionRepository가 static 아니기에 직접 매개변수로 전달
        questionRepository.deleteAll();   // 지워도 흔적이 남은 (데이터 재생성 하면 번호가 밀림)
        questionRepository.truncateTable(); // 똑같이 지우는 건데 흔적이 안남는다.

    }

    // 외부에서 호출  (질문 2개 만들고) (샘플 데이터 2개 제작)
    public static int createSampleData(QuestionRepository questionRepository) {  // 1, 2번 게시물 생성
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        questionRepository.save(q1);  // 첫번째 질문 저장

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        questionRepository.save(q2);  // 두번째 질문 저장

        return q2.getId(); // 2개에 대해서 id를 담고 있기에 항상 2개이다.
    }

    // BeforeEach 내부에서 활용
    public void createSampleData() {  // 1, 2번 게시물 생성
        lastSampleDataId = createSampleData(questionRepository); // 2개에 대해서 id를 담고 있기에 항상 2개이다.
    }

    @Test
    void 저장() { // 위에서 게시물 1, 2번 만들고 여기서 3, 4 번 만듬
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        questionRepository.save(q1);  // 첫번째 질문 저장

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        questionRepository.save(q2);  // 두번째 질문 저장

        assertThat(q1.getId()).isEqualTo(lastSampleDataId + 1); // 3번 게시물 확인
        assertThat(q2.getId()).isEqualTo(lastSampleDataId + 2); // 4번 게시물 확인

    }

    @Test
    void 수정() {
        assertThat(questionRepository.count()).isEqualTo(lastSampleDataId); // 전체 게시물의 수를 센다. 그게 마지막 아이디와 같은지

        Question q = questionRepository.findById(1).get(); // 값을 찾고
        q.setSubject("수정된 제목"); // 가져온 1번 게시물의 제목을 수정하고
        questionRepository.save(q); // 저장한다.

        q = questionRepository.findById(1).get();
        assertThat(q.getSubject()).isEqualTo("수정된 제목"); // 수정이 잘 동작했는지 확인

    }

    @Test
    void 삭제() {  // 테스트 코드 실행전 무조건 BeforeEach가 실행되기에 questionRepository.count() 은 무조건 2개다.
        assertThat(questionRepository.count()).isEqualTo(lastSampleDataId); // 전체 게시물의 수를 센다. 그게 마지막 아이디와 같은지

        Question q = questionRepository.findById(1).get(); // 위의 코드 특성상 일관성을 믿고 1번 게시물은 무조건 존재하겠구나
        questionRepository.delete(q); // 위에서 가져왔으니까 리포지토리에서 삭제 -> 질문의 개수가 1개 줄어든다.

        assertThat(questionRepository.count()).isEqualTo(lastSampleDataId - 1); // 삭제 후 샘플 데이터는 게시물의 개수가 줄어들기에 1개 적다.
    }

    @Test
    void findAll() {
        // findAll() : SELECT * FROM question;
        List<Question> all = questionRepository.findAll();
        assertThat(all.size()).isEqualTo(lastSampleDataId); // 리스트의 크기 확인

        Question q = all.get(0); // 첫번째 게시글의 제목 값 확인
        assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?"); // 테스트 실행 전 BeforeEach 가 먼저 실행되어 데이터 리셋 되기에 수정 전으로 돌아가 초기 데이터 상대라 참이다.
    }

    @Test
    void findBySubject() { // 쿼리문에서 where 뒤에 조건에 "sbb가 무엇인가요?" 넣는 것과 같다. JPA 가 해준다. (내용 자체를 객체에 리턴)
        Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
        assertThat(q.getId()).isEqualTo(1);
    }
    // 쿼리문의 where 절에 조건에 대해서
    @Test // findBySubjectAndContent (여러 칼럼을 AND 로 검색), findBySubjectOrContent (여러 칼럼을 OR로 검색)
    void findBySubjectAndContent() { // 쿼리문에서 where 뒤에 조건에 "sbb가 무엇인가요?" 넣는 것과 같다. JPA 가 해준다. (내용 자체를 객체에 리턴)
        Question q = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
        assertThat(q.getId()).isEqualTo(1);
    }

    @Test
    void findBySubjectLike() {
        List<Question> qList = questionRepository.findBySubjectLike("sbb%"); // sbb로 시작하는 애들 다 가져와
        Question q = qList.get(0); // sbb 로 시작하는 애들 중에  중 첫번 쨰

        assertThat(q.getSubject()).isEqualTo("sbb가 무엇인가요?");
    }

    /*
    @Test
    void testJpa1() { // 데이터 생성
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        questionRepository.save(q1);  // 첫번째 질문 저장

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        questionRepository.save(q2);  // 두번째 질문 저장

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
        Question q = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
        assertEquals(1, q.getId()); // findBySubject로 제목을 한개만 가져오겠다.
    }

    @Test
    void testJpa5() {
        List<Question> qList = questionRepository.findBySubjectLike("sbb%"); // sbb로 시작하는 애들 다 가져와
        Question q = qList.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }

    @Test             // null  값을 안전하게 처리하기 위한 방식
    void testJpa6() { // Optional 은 null 값을 가지고 감싸고 있는 래퍼클래스
        Optional<Question> oq = questionRepository.findById(1); // jpa가 제공하는 메서드 findById
//        Question q = oq.orElse(null);  이 코드를 아래 isPresent로 해결
        assertTrue(oq.isPresent()); //  isPresent는 ! = null   과 같다.
        Question q = oq.get();
        q.setSubject("수정된 제목");
        questionRepository.save(q);
        // 기존 id가 존재하면 해당 코드는 update를 실행한다.

        // findById(1) 해당 녀석이 있으면 위에서 업데이트가 일어나고 아래에서 또 수정이 일어난다.
        Question q2 = questionRepository.findById(1).get();
        q2.setSubject("Hi");
        questionRepository.save(q2); // insert
    }

    @Test
    void testJpa7() {
        assertEquals(2, questionRepository.count()); // 현재 질문이 2개있다.
        Optional<Question> oq = questionRepository.findById(1); // 1번 게시물을 찾아서 0oq 에 값 반환
        assertTrue(oq.isPresent()); // 값이 null 인지 확인
        Question q = oq.get(); // 아니라면 값 가져와서 참조값에 넣는다.
        questionRepository.delete(q); // 위에서 가져왔으니까 리포지토리에서 삭제 -> 질문의 개수가 1개 줄어든다.
        assertEquals(1, questionRepository.count()); // 질문의 개수 2개에서 1개가 됨

        *//*  지금까지 위의 코드 문제점
        - 기존 테스트 코드는 유연나지 않음
        - 처음 데이터 생성 id는 차례대로 1번, 2번
        - 그 다음 또 테스트 생성시 3, 4번 id 가 생김
        - 1, 2번 데이터가 삭제 되게 되면 수정이나 삭제 코드를 사용할 수 없는 문제가 발생!
        *//*
    }
    */
}
