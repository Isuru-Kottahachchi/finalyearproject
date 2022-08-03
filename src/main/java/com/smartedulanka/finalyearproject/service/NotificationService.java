package com.smartedulanka.finalyearproject.service;


import com.smartedulanka.finalyearproject.datalayer.entity.Notifications;
import com.smartedulanka.finalyearproject.datalayer.entity.Question;
import com.smartedulanka.finalyearproject.repository.NotificationsRepository;
import com.smartedulanka.finalyearproject.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {


    @Autowired
    private NotificationsRepository notificationsRepository;


    public List<Notifications> findNotificationsInId(Long user_id,String userName) {

        if (user_id != null) {
            return notificationsRepository.getNotificationsById(user_id,userName);
        }
        return notificationsRepository.findAll();
    }
}
