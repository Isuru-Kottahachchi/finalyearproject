package com.smartedulanka.finalyearproject.repository;

import com.smartedulanka.finalyearproject.datalayer.entity.UploadRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UploadRepository extends JpaRepository<UploadRecords, Long> {



  /*Retrieve all pending files which is review_status = PENDING */
  @Query("SELECT u FROM UploadRecords u WHERE u.reviewStatus='PENDING'")
  public List<UploadRecords> retrieveAllPendingFiles();

  /*Retrieve all ApprovedFiles which is review_status = ACCEPTED*/
  @Query("SELECT u FROM UploadRecords u WHERE u.reviewStatus='ACCEPTED'")
  public List<UploadRecords> retrieveAllApprovedFiles();

  /*Retrieve all RejectedFiles which is review_status = REJECTED */
  @Query("SELECT u FROM UploadRecords u WHERE u.reviewStatus='Rejected'")
  public List<UploadRecords> retrieveAllRejectedFiles();




  @Query("SELECT COUNT(*) FROM UploadRecords u WHERE u.reviewStatus='PENDING'")
  public Long getPendingFilesCount();


/*
  @Query("SELECT u FROM UploadRecord u WHERE CONCAT(u.UploadedFileName, u.subjectArea, u.fileName) LIKE %?1% AND u.reviewStatus='PENDING'")*/

  /* Search upload pending  records in file review.html searchBar*/
  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='PENDING'")
  public List<UploadRecords> searchRelevantPendingFiles(String keyword);

  /*Search AcceptedFiles records approvedFiles.html searchBar*/
  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords> searchRelevantApprovedFiles(String keyword);

  /*Search Rejected files records rejectedFiles.html searchBar*/
  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='REJECTED'")
  public List<UploadRecords> searchRelevantRejectedFiles(String keyword);









    /*Retrieve only combined maths files to the combined maths to  past paper section*/
    @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
    public List<UploadRecords> retrieveCombinedMaths(String keyword);

    /*Retrieve only Biology files to  past paper section*/
    @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'" )
    public List<UploadRecords> retrieveBiology(String keyword);

    /*Retrieve only Chemistry files to past paper section*/
    @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
    public List<UploadRecords> retrieveChemistry(String keyword);

    /*Retrieve only Physics files to  past paper section*/
    @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
    public List<UploadRecords> retrievePhysics(String keyword);

    /*Retrieve only InformationTechnology files to  past paper section*/
    @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
    public List<UploadRecords> retrieveInformationTechnology(String keyword);



  /*Retrieve only combined maths files to the Sinhala medium combined maths to  past paper section*/
    @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
    public List<UploadRecords> retrieveSMCombinedMaths(String keyword);

    @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'" )
    public List<UploadRecords> retrieveSMBiology(String keyword);

    /*Retrieve only Chemistry files to past paper section*/
    @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
    public List<UploadRecords> retrieveSMChemistry(String keyword);

    /*Retrieve only Physics files to  past paper section*/
    @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
    public List<UploadRecords> retrieveSMPhysics(String keyword);

    /*Retrieve only InformationTechnology files to  past paper section*/
    @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
    public List<UploadRecords> retrieveSMInformationTechnology(String keyword);

    @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
    public List<UploadRecords>  retrieveEMInformationTechnology(String keyword);

  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords> retrieveTMInformationTechnology(String keyword);





  /*Retrieve only combined maths files to the Tamil medium combined maths to  past paper section*/
    @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
    public List<UploadRecords> retrieveTMCombinedMaths(String keyword);

    @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'" )
    public List<UploadRecords> retrieveTMBiology(String keyword);

    /*Retrieve only Chemistry files to past paper section*/
    @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
    public List<UploadRecords> retrieveTMChemistry(String keyword);

    /*Retrieve only Physics files to  past paper section*/
    @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
    public List<UploadRecords> retrieveTMPhysics(String keyword);





  /*Retrieve only English medium Bio Olympiad past paper section*/
  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords> retrieveEmBioOlympiad(String keyword);

  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords> retrieveEmChemistryOlympiad(String keyword);

  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords> retrieveEmPhysicsOlympiad(String keyword);



  /*Retrieve only Sinhala medium Bio Olympiad past paper section*/
  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords> retrieveSmBioOlympiad(String keyword);

  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords> retrieveSmChemistryOlympiad(String keyword);

  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords> retrieveSmPhysicsOlympiad(String keyword);



  /*Retrieve only Tamil medium Bio Olympiad past paper section*/
  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords> retrieveTmBioOlympiad(String keyword);

  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords> retrieveTmChemistryOlympiad(String keyword);

  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords> retrieveTmPhysicsOlympiad(String keyword);



  /*Retrieve only SINHALA medium Ordinary LEVEL paper section*/
  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords> retrieveSmOrdinaryLevelMaths(String keyword);

  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords> retrieveSmOrdinaryLevelScience(String keyword);

  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords> retrieveSmOrdinaryLevelIt(String keyword);

  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords>  retrieveSmOrdinaryLevelBusinessS(String keyword);

  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords> retrieveSmOrdinaryLevelHistory(String keyword);




  /*Retrieve only English medium Ordinary LEVEL paper section*/
  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords> retrieveEmOrdinaryLevelMaths(String keyword);

  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords> retrieveEmOrdinaryLevelScience(String keyword);

  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords> retrieveEmOrdinaryLevelIt(String keyword);

  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords>  retrieveEmOrdinaryLevelBusinessS(String keyword);

  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords> retrieveEmOrdinaryLevelHistory(String keyword);




  /*Retrieve only Tamil medium Ordinary LEVEL paper section*/
  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords> retrieveTmOrdinaryLevelMaths(String keyword);

  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords> retrieveTmOrdinaryLevelScience(String keyword);




  /*Retrieve only English medium Advanced LEVEL commerce paper section*/
  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords> retrieveEmBStudiesAdvancedLevel(String keyword);

  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords> retrieveEmAccountingAdvancedLevel(String keyword);

  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords> retrieveEmEconAdvancedLevel(String keyword);



  /*Retrieve only Sinhala medium Advanced LEVEL Art paper section*/
  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords> retrieveSmGeographyAdvancedLevel(String keyword);

  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords>   retrieveSmHomeScienceAdvancedLevel(String keyword);


  /*Retrieve only Tamil medium Advanced LEVEL Art paper section*/
  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords> retrieveTmGeographyAdvancedLevel(String keyword);

  @Query("SELECT u FROM UploadRecords u WHERE u.subjectArea LIKE %?1% AND u.reviewStatus='ACCEPTED'")
  public List<UploadRecords>   retrieveTmHomeScienceAdvancedLevel(String keyword);





}
