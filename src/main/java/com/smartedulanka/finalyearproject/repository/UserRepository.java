package com.smartedulanka.finalyearproject.repository;

import com.smartedulanka.finalyearproject.datalayer.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>{

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    public User findByEmail(String email);

    public User findByResetPasswordToken(String token);


    @Query(value = "SELECT user_id FROM Users u WHERE u.full_name = ?1 ",nativeQuery = true)
    public Long getAuthorId(String questionAuthorName);


    @Query(value = "SELECT role FROM Users u WHERE u.user_id = ?1 ",nativeQuery = true)
    public String getRole(Long user_id);



    @Query(value = "SELECT registered_time FROM Users u WHERE u.user_id = ?1 ",nativeQuery = true)
    public String getJoinedDate(Long user_id);


    @Query(value = "SELECT COUNT(*) FROM Users ",nativeQuery = true)
    public Long getNumberOfRegisteredUsers();



    @Query(value = "SELECT * FROM Users u WHERE u.email = ?1 ",nativeQuery = true)
    public List<User> searchRelevantUsers(String keyword);
















}
