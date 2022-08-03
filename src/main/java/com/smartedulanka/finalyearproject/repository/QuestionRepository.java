package com.smartedulanka.finalyearproject.repository;

import com.smartedulanka.finalyearproject.datalayer.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {


    @Query(value = "SELECT * FROM Questions q WHERE  q.question_id = ?1",nativeQuery = true)
    public Question getPreviouslySavedQuestion(Long question_id);
    /*Search upload pending  records*/




    @Query(value = "SELECT * FROM Questions q WHERE q.user_id = ?1 ",nativeQuery = true)
    public List<Question> getQuestionsWithId(Long userId);

    @Query(value = "SELECT * FROM Questions q WHERE q.question_status = 'SOLVED' ",nativeQuery = true)
    public List<Question> getSolvedQuestionsWithId();

    @Query(value = "SELECT * FROM Questions q WHERE q.question_status = 'UNSOLVED' ",nativeQuery = true)
    public List<Question> getUnSolvedQuestionsWithId();




    @Query(value = "SELECT * FROM Questions q WHERE q.question_subject_area LIKE '%Advanced level%' OR q.question_subject_area LIKE '%Advanced Level%' OR q.question_subject_area LIKE '%advanced level%' OR q.question_subject_area LIKE '%ADVANCED LEVEL%' OR q.question_subject_area LIKE '%උසස් පෙල%'",nativeQuery = true)
    public List<Question> getAdvancedLevelQuestions();

    @Query(value = "SELECT * FROM Questions q WHERE q.question_subject_area LIKE '%Advanced level BIOLOGY%' OR q.question_subject_area LIKE '%Advanced level biology%' OR q.question_subject_area LIKE '%advanced level Biology%' OR q.question_subject_area LIKE '%Advanced Level Biology%'",nativeQuery = true)
    public List<Question> getAdvancedLevelBiologyQuestions();

    @Query(value = "SELECT * FROM Questions q WHERE q.question_subject_area LIKE '%Advanced chemistry%' OR q.question_subject_area LIKE '%Advanced level chemistry%' OR q.question_subject_area LIKE '%advanced chemistry%' OR q.question_subject_area LIKE '%advanced CHEMISTRY%' OR q.question_subject_area LIKE '%advanced Chemistry%'",nativeQuery = true)
    public List<Question> getAdvancedLevelChemistryQuestions();

    @Query(value = "SELECT * FROM Questions q WHERE q.question_subject_area LIKE '%Ordinary level%' OR q.question_subject_area LIKE '%Ordinary Level%' OR q.question_subject_area LIKE '%සාමාන්ය පෙළ%' OR q.question_subject_area LIKE '%සාමාන්ය පෙල%'",nativeQuery = true)
    public List<Question> getOrdinaryLevelQuestions();







    @Query(value = "SELECT * FROM Questions q WHERE q.question_id = ?1 ",nativeQuery = true)
    public List<Question> getReportedQuestion(Long questionId);


    @Query(value = "SELECT question_id FROM Questions q WHERE q.user_id = ?1 ",nativeQuery = true)
    public ArrayList<Long> getQuestionIds(Long userId);








    @Query(value = "SELECT question_status FROM Questions q WHERE q.question_id = ?1 ",nativeQuery = true)
    public String getQuestionStatus(Long questionId);



    @Query(value = "SELECT user_id FROM Questions q WHERE q.question_id = ?1 ",nativeQuery = true)
    public Long getUserId(Long questionId);


    @Query(value = "SELECT * FROM Questions q WHERE q.question_id = ?1 ",nativeQuery = true)
    public List<Question> getRespondedQuestion(Long questionId);

    @Query(value = "SELECT * FROM Questions q WHERE q.question_id = ?1 ",nativeQuery = true)
    public Question getQuestionById(Long answerId);











    @Query(value = "SELECT COUNT(*) FROM Questions q WHERE q.user_id = ?1",nativeQuery = true)
    public Long getNumberOfQuestions(Long user_id);


    @Query(value = "SELECT COUNT(*) FROM Questions ",nativeQuery = true)
    public Long getNumberOfQuestions();





    @Query(value = "SELECT user_id FROM Questions q WHERE q.question_id = ?1",nativeQuery = true)
    public Long getQuestionAuthorId(Long question_id);



 /*   @Query(value = "SELECT user_id FROM Questions q WHERE q.user_id = ?1",nativeQuery = true)
    public Question getLoggedInUsersQuestions(Long user_id);*/


    @Query(value = "SELECT user_id FROM Questions q WHERE q.question_id = ?1",nativeQuery = true)
    public Long retreiveQuestionIds(Long questionId);



    @Query(value = "SELECT full_question FROM Questions q WHERE q.question_id = ?1",nativeQuery = true)
    public String getFullQuestion(Long questionId);

    @Query(value = "SELECT question_author_name FROM Questions q WHERE q.question_id = ?1",nativeQuery = true)
    public String getQuestionAuthorName(Long questionId);

    @Query(value = "SELECT question_author_email FROM Questions q WHERE q.question_id = ?1",nativeQuery = true)
    public String  getQuestionAuthorEmail(Long questionId);



    @Query(value = "SELECT * FROM Questions q WHERE q.question_id = ?1",nativeQuery = true)
    public Question getQuestionsFromId(Long questionId);



    /*@Query(value = "SELECT * FROM Questions q WHERE q.question_subject_area LIKE %?1% OR full_question LIKE %?1% ",nativeQuery = true)
    public List<Question> findRelevantQuestions(String questionText);*/

    @Query(value = "SELECT * FROM Questions WHERE "
            + "MATCH(question_subject_area,full_question)"
            + "AGAINST (?1)",
            nativeQuery = true)
    public List<Question> findRelevantQuestions(String questionText);



































}
