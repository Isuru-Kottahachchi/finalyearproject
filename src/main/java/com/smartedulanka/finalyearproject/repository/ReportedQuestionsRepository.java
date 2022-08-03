package com.smartedulanka.finalyearproject.repository;

import com.smartedulanka.finalyearproject.datalayer.entity.ReportedQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportedQuestionsRepository extends JpaRepository<ReportedQuestions,Long> {


    @Query(value = "SELECT * FROM ReportedQuestions r WHERE r.reported_or_not = 'REPORTED'",nativeQuery = true)
    public List<ReportedQuestions> retrieveAllReportedQuestions();


    @Query(value="SELECT reported_or_not FROM ReportedQuestions r WHERE r.reporter_id = ?1 AND r.question_id = ?2",nativeQuery = true)
    public String questionReportedOrNot(Long userId,Long questionId);


    @Query(value="SELECT * FROM ReportedQuestions r WHERE r.reporter_id = ?1 AND r.question_id = ?2",nativeQuery = true)
    public ReportedQuestions getQuestionReportObject(Long userId, Long questionId);

    @Query(value="SELECT COUNT(DISTINCT id_question) FROM ReportedQuestions r WHERE r.reported_or_not = 'REPORTED'  ",nativeQuery = true)
    public Long getNumberOfReportedQuestions();



    @Query(value="SELECT * FROM ReportedQuestions r WHERE r.reporter_id = ?1",nativeQuery = true)
    public List<ReportedQuestions> getQuestionReportObjects(Long questionId);



    @Query(value="SELECT reported_question_id FROM ReportedQuestions r WHERE r.question_id = ?1",nativeQuery = true)
    public Long getReportQuestionId(Long questionId);



    @Query(value="SELECT reported_or_not FROM ReportedQuestions r WHERE r.question_id = ?1",nativeQuery = true)
    public String getReportStatus(Long questionId);







}