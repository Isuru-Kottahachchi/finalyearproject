package com.smartedulanka.finalyearproject.repository;

import com.smartedulanka.finalyearproject.datalayer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer,Long> {

    @Query(value = "SELECT * FROM Answers a WHERE a.answer_id = ?1",nativeQuery = true)
    public Answer getPreviouslySavedAnswer(Long answerId);


    @Query(value = "SELECT question_id FROM Answers a WHERE a.answer_id = ?1",nativeQuery = true)
    public Long getReportedAnswersQuestionId(Long answerId);


    @Query(value = "SELECT COUNT(*) FROM Answers a WHERE a.user_id = ?1",nativeQuery = true)
    public Long getNumberOfAnswers(Long user_id);


    @Query(value = "SELECT COUNT(*) FROM Answers a WHERE a.user_id = ?1 AND answer_status='Answer Accepted'",nativeQuery = true)
    public Long getNumberOfAcceptedAnswers(Long user_id);


    @Query(value = "SELECT answer_author_id FROM Answers a WHERE a.answer_id = ?1 ",nativeQuery = true)
    public Long getAnswerAuthorUserId(Long answer_id);


    @Query(value = "SELECT question_id FROM Answers a WHERE a.answer_id = ?1",nativeQuery = true)
    public Long getQuestionId(Long answer_id);

    @Query(value = "SELECT answer_status FROM Answers a WHERE a.answer_id = ?1",nativeQuery = true)
    public String getAnswerStatus(Long answer_id);




    @Query(value = "SELECT answer_author_name FROM Answers a WHERE a.answer_id = ?1",nativeQuery = true)
    public String  getAnswerAuthorName(Long answer_id);


    @Query(value = "SELECT answer_submitted_time FROM Answers a WHERE a.answer_id = ?1",nativeQuery = true)
    public String  getAnswerSubmittedTime(Long answer_id);


    @Query(value = "SELECT answer_author_id FROM Answers a WHERE a.answer_id = ?1",nativeQuery = true)
    public Long  getAnswerAuthorId(Long answer_id);



    @Query(value = "SELECT DISTINCT question_id FROM Answers a WHERE a.answer_author_id = ?1",nativeQuery = true)
    public ArrayList<Long> getQuestionIds(Long userId);



    @Query(value = "SELECT answer_author_email FROM Answers a WHERE a.answer_id = ?1",nativeQuery = true)
    public String  getAnswerAuthorEmail(Long answerId);



    @Query(value = "SELECT answer_id FROM Answers a WHERE a.question_id = ?1",nativeQuery = true)
    public List<Long> getAllAnswerIds(Long questionId);


    @Query(value = "SELECT question_id FROM Answers a WHERE a.answer_id = ?1",nativeQuery = true)
    public Long getQuestionsId(Long answerId);


    @Query(value = "SELECT answer_id FROM Answers a WHERE a.user_id = ?1",nativeQuery = true)
    public ArrayList<Long> getAnswerIds(Long userId);










    /*@Query(value = "SELECT answer_id FROM Answers a WHERE a.question_id = ?1 AND answer_status='Answer Accepted'",nativeQuery = true)
    public Long getAcceptedAnswerId(Long question_id);*/



}
