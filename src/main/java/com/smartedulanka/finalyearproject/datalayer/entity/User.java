package com.smartedulanka.finalyearproject.datalayer.entity;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "userId")
    private Long userId;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @Column(name = "role", nullable = false, length = 20)
    private String role;

    @Column(name = "fullName", nullable = false, length = 20)
    private String fullName;

    @Column(nullable = true, unique = false, length = 100)
    private String registeredTime;

    @Column(nullable = true,name = "reset_password_token")
    private String resetPasswordToken;

    @Column(nullable = true, unique = false, length = 100)
    private String recentNotificationsCheckedTime;


    /* One to many mapping with uploadRecords*/
    @OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.REFRESH,CascadeType.REMOVE })
    private List<UploadRecords> uploadRecords;

    public List<UploadRecords> getUploadRecords() {
        return uploadRecords;
    }

    public void setUploadRecords(List<UploadRecords> uploadRecords) {
        this.uploadRecords = uploadRecords;
    }



    /* One to Many mapping with Question*/
    @OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.REFRESH,CascadeType.REMOVE })
    private List<Question> questions;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }



    /* One to Many mapping with Answer*/
    @OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.REFRESH,CascadeType.REMOVE })
    private List<Answer> answers;

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }


    /* One to Many mapping with reportQuestion*/
    /* One to Many mapping with Rating*/
    @OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.REFRESH })
    private List<Ratings> ratings;
    public List<Ratings> getRatings() {
        return ratings;
    }

    public void setRatings(List<Ratings> ratings) {
        this.ratings = ratings;
    }







    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRegisteredTime() {
        return registeredTime;
    }

    public void setRegisteredTime(String registeredTime) {
        this.registeredTime = registeredTime;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public String getRecentNotificationsCheckedTime() {
        return recentNotificationsCheckedTime;
    }

    public void setRecentNotificationsCheckedTime(String recentNotificationsCheckedTime) {
        this.recentNotificationsCheckedTime = recentNotificationsCheckedTime;
    }
}
