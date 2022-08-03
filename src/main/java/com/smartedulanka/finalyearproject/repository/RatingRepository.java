package com.smartedulanka.finalyearproject.repository;

import com.smartedulanka.finalyearproject.datalayer.entity.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingRepository extends JpaRepository<Ratings,Long> {

    @Query(value = "SELECT * FROM Ratings r WHERE r.user_id = ?1 AND r.answer_id = ?2",nativeQuery = true)
    public Ratings getPreviouslySavedRating(Long userId,Long answerId);



    @Query(value = "SELECT COUNT(*) FROM Ratings r WHERE r.answer_id = ?1",nativeQuery = true)
    public Long getFinalRatingValues(Long answer_id);

    @Query(value = "SELECT COUNT(*) FROM Ratings r WHERE  r.answer_id = ?1 AND r.rating_value=1 ",nativeQuery = true)
    public Long getFinalPositiveRatingValue(Long answer_id);

    @Query(value = "SELECT COUNT(*) FROM Ratings r WHERE  r.answer_id = ?1 AND r.rating_value=-1 ",nativeQuery = true)
    public Long getFinalNegativeRatingValue(Long answer_id);




    @Query(value = "SELECT COUNT(*) FROM Ratings r WHERE r.answer_id = ?1",nativeQuery = true)
    public Long getUpdatedRatingValue(Long answerId);

    @Query(value = "SELECT COUNT(*) FROM Ratings r WHERE  r.answer_id = ?1 AND r.rating_value=1 ",nativeQuery = true)
    public Long getUpdatedPositiveRatingValue(Long answerId);

    @Query(value = "SELECT COUNT(*) FROM Ratings r WHERE  r.answer_id = ?1 AND r.rating_value=-1 ",nativeQuery = true)
    public Long getUpdatedNegativeRatingValue(Long answerId);




    @Query(value = "SELECT rating_value FROM Ratings r WHERE  r.user_id = ?1 AND r.answer_id=?2 ",nativeQuery = true)
    public Long getAUsersRatingValue(Long user_id,Long answerId);



    @Query(value = "SELECT rating_value FROM Ratings r WHERE  r.user_id = ?1 AND r.answer_id=?2 ",nativeQuery = true)
    public Long  getCurrentRatingValue(Long user_id,Long answerId);


    @Query(value = "SELECT COUNT(*) FROM Ratings r WHERE  r.answer_author_id = ?1 AND r.rating_value=1 ",nativeQuery = true)
    public Long  getNumberOfUpVotes(Long user_id);

    @Query(value = "SELECT COUNT(*) FROM Ratings r WHERE  r.answer_author_id = ?1 AND r.rating_value=-1 ",nativeQuery = true)
    public Long  getNumberOfDownVotes(Long user_id);


    @Query(value = "SELECT * FROM Ratings r WHERE r.answer_id = ?1 ",nativeQuery = true)
    public List<Ratings> getAllRatings(Long answerId);


    @Query(value = "SELECT rating_id FROM Ratings r WHERE r.answer_id = ?1 ",nativeQuery = true)
    public List<Long>  getRatingsId(Long answerId);


    @Query(value = "SELECT * FROM Ratings r WHERE r.question_id = ?1 ",nativeQuery = true)
    public List<Ratings> getRatingsAccordingToQuestionId(Long questionId);






}
