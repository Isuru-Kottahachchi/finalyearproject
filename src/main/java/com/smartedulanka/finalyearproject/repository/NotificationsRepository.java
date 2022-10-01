package com.smartedulanka.finalyearproject.repository;

import com.smartedulanka.finalyearproject.datalayer.entity.Notifications;
import com.smartedulanka.finalyearproject.datalayer.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface NotificationsRepository extends JpaRepository<Notifications,Long> {


    @Query(value = "SELECT * FROM notifications n WHERE n.question_author_id = ?1 AND n.responded_user_name != ?2",nativeQuery = true)
    public List<Notifications> getNotificationsById(Long user_id,String userName);





    @Query(value = "SELECT DISTINCT question_id FROM notifications n WHERE n.responded_user_name = ?1 ",nativeQuery = true)
    public ArrayList<Long> getLoggedUserAnsweredQuestionIds(String userName);


    @Query(value = "SELECT notification_id FROM notifications n WHERE n.responded_user_name != ?1 AND n.question_id = ?2 AND n.response_submitted_time > ?3 ",nativeQuery = true)
    public List<Long> getNotificationIds(String userName,Long loggedUserAnsweredQuestionId,String firstAnswerSubmittedTime);




    @Query(value = "SELECT notification_id FROM notifications n WHERE n.responded_user_name != ?1 AND n.question_id = ?2 AND n.response_submitted_time > ?3 ",nativeQuery = true)
    public List<Long> getUnCheckedNotificationIDs(String userName,Long loggedUserAnsweredQuestionId,String firstAnswerSubmittedTime);

    @Query(value = "SELECT notification_id FROM notifications n WHERE n.responded_user_name != ?1 AND n.question_id = ?2 AND n.response_submitted_time > ?3 AND n.response_submitted_time > ?4 ",nativeQuery = true)
    public List<Long> getUnCheckedNotificationIds(String userName,Long loggedUserAnsweredQuestionId,String firstAnswerSubmittedTime,String recentNotificationCheckedTime);




    @Query(value = "SELECT * FROM notifications n WHERE n.responded_user_name != ?1 AND n.notification_id = ?2 ",nativeQuery = true)
    public Notifications getNotificationsByQuestionId(String userName,Long notificationId);



    @Query(value = "SELECT response_submitted_time FROM notifications n WHERE n.responded_user_name = ?1 AND n.question_id = ?2 ",nativeQuery = true)
    public List<String> getAnswersSubmittedTime(String userName,Long loggedUserAnsweredQuestionId);







    @Query(value = "SELECT COUNT(*) FROM notifications n WHERE n.question_author_id = ?1 AND n.response_submitted_time > ?2 AND N.responded_user_name != ?3 ",nativeQuery = true)
    public Long getNumberOfUncheckedNotifications(Long userId,String recentNotificationTime,String userName);

    @Query(value = "SELECT COUNT(*) FROM notifications n WHERE n.question_author_id = ?1 AND N.responded_user_name != ?2 ",nativeQuery = true)
    public Long getNumberOfUncheckedNotification(Long userId,String userName);






}
