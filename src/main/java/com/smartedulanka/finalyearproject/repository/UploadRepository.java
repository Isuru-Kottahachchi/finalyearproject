package com.smartedulanka.finalyearproject.repository;

import com.smartedulanka.finalyearproject.datalayer.entity.UploadRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UploadRepository extends JpaRepository<UploadRecord, Long> {



  /*Retrieve pending files which is review_status = 0 */
  @Query("SELECT u FROM UploadRecord u WHERE u.reviewStatus='PENDING'")
  public List<UploadRecord> retrievePendingFiles(String keyword);

  /*Retrieve all ApprovedFiles which is review_status = 1 */
  @Query("SELECT u FROM UploadRecord u WHERE u.reviewStatus='ACCEPTED'")
  public List<UploadRecord> retrieveAllApprovedFiles(String keyword);

  /*Retrieve all RejectedFiles which is review_status = 1 */
  @Query("SELECT u FROM UploadRecord u WHERE u.reviewStatus='Rejected'")
  public List<UploadRecord> retrieveAllRejectedFiles(String keyword);






  /*Search upload pending  records*/
    @Query("SELECT u FROM UploadRecord u WHERE CONCAT(u.UploadedFileName, u.subjectArea, u.fileName) LIKE %?1% AND u.reviewStatus='PENDING'")
    public List<UploadRecord> search(String keyword);

  /*Retrieve AcceptedFiles*/
  @Query("SELECT u FROM UploadRecord u WHERE CONCAT(u.UploadedFileName, u.subjectArea, u.fileName) LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecord> retrieveApprovedFiles(String keyword);






  /*Search Rejected  records*/
  @Query("SELECT u FROM UploadRecord u WHERE CONCAT(u.UploadedFileName, u.subjectArea, u.fileName) LIKE %?1% AND u.reviewStatus='REJECTED'")
  public List<UploadRecord> searchRejectedFiles(String keyword);

/*  *//*Retrieve RejectedFiles*//*
  @Query("SELECT u FROM UploadRecord u WHERE CONCAT(u.UploadedFileName, u.subjectArea, u.fileName) LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecord> retrieveRejectedFiles(String keyword);*/








    /*Retrieve only combined maths files*/
    @Query("SELECT u FROM UploadRecord u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
    public List<UploadRecord> searchCombinedMaths(String keyword);

    /*Retrieve only Biology files*/
    @Query("SELECT u FROM UploadRecord u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'" )
    public List<UploadRecord> searchBiology(String keyword);

    /*Retrieve only Chemistry files*/
    @Query("SELECT u FROM UploadRecord u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
    public List<UploadRecord> searchChemistry(String keyword);

    /*Retrieve only Physics files*/
    @Query("SELECT u FROM UploadRecord u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
    public List<UploadRecord> searchPhysics(String keyword);

    /*Retrieve only InformationTechnology files*/
    @Query("SELECT u FROM UploadRecord u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
    public List<UploadRecord> searchInformationTechnology(String keyword);




}
