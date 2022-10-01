package com.smartedulanka.finalyearproject.datalayer.entity;



import javax.persistence.*;

@Entity
@Table(name = "ratings")
public class Ratings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rating_id;

    @Column(nullable = false, unique = false, length = 45)
    private Short ratingValue;

    @Column(nullable = false, unique = false, length = 45)
    private String rater_name;

    @Column(nullable = false, unique = false, length = 45)
    private Long answer_Author_id;




    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;



    public Long getRating_id() {
        return rating_id;
    }

    public void setRating_id(Long rating_id) {
        this.rating_id = rating_id;
    }

    public Short getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(Short ratingValue) {
        this.ratingValue = ratingValue;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRater_name() {
        return rater_name;
    }

    public void setRater_name(String rater_name) {
        this.rater_name = rater_name;
    }

    public Long getAnswer_Author_id() {
        return answer_Author_id;
    }

    public void setAnswer_Author_id(Long answer_Author_id) {
        this.answer_Author_id = answer_Author_id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
