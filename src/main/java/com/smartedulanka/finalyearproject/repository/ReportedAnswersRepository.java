package com.smartedulanka.finalyearproject.repository;

import com.smartedulanka.finalyearproject.datalayer.entity.ReportedAnswers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportedAnswersRepository extends JpaRepository<ReportedAnswers,Long> {

    @Query(value = "SELECT * FROM ReportedAnswers r WHERE r.reported_or_not = 'REPORTED'",nativeQuery = true)
    public List<ReportedAnswers> retrieveAllReportedAnswers();



    @Query(value="SELECT reported_or_not FROM ReportedAnswers a WHERE a.reporter_id = ?1 AND a.id_answer = ?2",nativeQuery = true)
    public String answerReportedOrNot(Long userId,Long answerId);

    @Query(value="SELECT * FROM ReportedAnswers r WHERE r.reporter_id = ?1 AND r.id_answer = ?2",nativeQuery = true)
    public ReportedAnswers getAnswerReportObject(Long userId, Long answerId);


    @Query(value="SELECT COUNT(DISTINCT id_answer) FROM ReportedAnswers r WHERE r.reported_or_not = 'REPORTED'  ",nativeQuery = true)
    public Long getNumberOfReportedAnswers();



    @Query(value="SELECT reported_or_not FROM ReportedAnswers r WHERE r.answer_id = ?1",nativeQuery = true)
    public String getReportStatus(Long answerId);


    @Query(value="SELECT report_answer_id FROM ReportedAnswers r WHERE r.answer_id = ?1",nativeQuery = true)
    public Long getReportAnswerId(Long answerId);


}
