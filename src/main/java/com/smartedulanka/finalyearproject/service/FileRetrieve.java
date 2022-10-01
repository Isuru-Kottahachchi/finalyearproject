package com.smartedulanka.finalyearproject.service;

import com.smartedulanka.finalyearproject.datalayer.entity.UploadRecords;
import com.smartedulanka.finalyearproject.repository.UploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileRetrieve {


    @Autowired
    private UploadRepository uploadRepo;

    public List<UploadRecords> findPendingFiles(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveAllPendingFiles();
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecords> retrieveApprovedFiles(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveAllApprovedFiles();
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecords> retrieveRejectedFiles(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveAllRejectedFiles();
        }
        return uploadRepo.findAll();
    }









   /*English medium science*/
    public List<UploadRecords> listCombinedMaths(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveCombinedMaths(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecords> listBiology(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveBiology(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecords> listChemistry(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveChemistry(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecords> listPhysics(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrievePhysics(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecords> listInformationTechnology(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveInformationTechnology(keyword);
        }
        return uploadRepo.findAll();
    }



    /*Sinhala medium science*/

    public List<UploadRecords> listSmCombinedMaths(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveSMCombinedMaths(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecords> listSmBiology(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveSMBiology(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecords> listSmChemistry(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveSMChemistry(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecords> listSmPhysics(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveSMPhysics(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecords> listSmInformationTechnology(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveSMInformationTechnology(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecords>  listEmInformationTechnology(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveEMInformationTechnology(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecords> listTmInformationTechnology(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveTMInformationTechnology(keyword);
        }
        return uploadRepo.findAll();
    }





    /*Tamil medium science*/
    public List<UploadRecords> listTmCombinedMaths(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveTMCombinedMaths(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecords> listTmBiology(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveTMBiology(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecords> listTmChemistry(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveTMChemistry(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecords> listTmPhysics(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveTMPhysics(keyword);
        }
        return uploadRepo.findAll();
    }





      /*English medium Olympiad*/
    public List<UploadRecords> listEmBioOlympiad(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveEmBioOlympiad(keyword);
        }
        return uploadRepo.findAll();
    }
    public List<UploadRecords> listEmChemistryOlympiad(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveEmChemistryOlympiad(keyword);
        }
        return uploadRepo.findAll();
    }
    public List<UploadRecords> listEmPhysicsOlympiad(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveEmPhysicsOlympiad(keyword);
        }
        return uploadRepo.findAll();
    }



    /*Sinhala medium Olympiad*/
    public List<UploadRecords> listSmBioOlympiad(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveSmBioOlympiad(keyword);
        }
        return uploadRepo.findAll();
    }
    public List<UploadRecords> listSmChemistryOlympiad(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveSmChemistryOlympiad(keyword);
        }
        return uploadRepo.findAll();
    }
    public List<UploadRecords> listSmPhysicsOlympiad(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveSmPhysicsOlympiad(keyword);
        }
        return uploadRepo.findAll();
    }


    /*Tamil medium Olympiad*/
    public List<UploadRecords> listTmBioOlympiad(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveTmBioOlympiad(keyword);
        }
        return uploadRepo.findAll();
    }
    public List<UploadRecords> listTmChemistryOlympiad(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveTmChemistryOlympiad(keyword);
        }
        return uploadRepo.findAll();
    }
    public List<UploadRecords> listTmPhysicsOlympiad(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveTmPhysicsOlympiad(keyword);
        }
        return uploadRepo.findAll();
    }




    /*Sinhala medium Ordinary level*/
    public List<UploadRecords> listSmOrdinaryLevelMaths(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveSmOrdinaryLevelMaths(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecords> listSmOrdinaryLevelScience(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveSmOrdinaryLevelScience(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecords> listSmOrdinaryLevelIT(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveSmOrdinaryLevelIt(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecords> listSmOrdinaryLevelBusinessS(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveSmOrdinaryLevelBusinessS(keyword);
        }
        return uploadRepo.findAll();
    }
    public List<UploadRecords> listSmOrdinaryLevelHistory(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveSmOrdinaryLevelHistory(keyword);
        }
        return uploadRepo.findAll();
    }





    /*English medium Ordinary level*/
    public List<UploadRecords> listEmOrdinaryLevelMaths(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveEmOrdinaryLevelMaths(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecords> listEmOrdinaryLevelScience(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveEmOrdinaryLevelScience(keyword);
        }
        return uploadRepo.findAll();
    }
    public List<UploadRecords> listEmOrdinaryLevelIT(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveEmOrdinaryLevelIt(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecords> listEmOrdinaryLevelBusinessS(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveEmOrdinaryLevelBusinessS(keyword);
        }
        return uploadRepo.findAll();
    }
    public List<UploadRecords> listEmOrdinaryLevelHistory(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveEmOrdinaryLevelHistory(keyword);
        }
        return uploadRepo.findAll();
    }






    /*Tamil medium Ordinary level*/
    public List<UploadRecords> listTmOrdinaryLevelMaths(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveTmOrdinaryLevelMaths(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecords> listTmOrdinaryLevelScience(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveTmOrdinaryLevelScience(keyword);
        }
        return uploadRepo.findAll();
    }





    /*English medium Commerce*/
    public List<UploadRecords> listEmBStudiesAdvancedLevel(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveEmBStudiesAdvancedLevel(keyword);
        }
        return uploadRepo.findAll();
    }


    public List<UploadRecords> listEmAccountingAdvancedLevel(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveEmAccountingAdvancedLevel(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecords> listEmEconAdvancedLevel(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveEmEconAdvancedLevel(keyword);
        }
        return uploadRepo.findAll();
    }

    /*Sinhala medium arts*/

    public List<UploadRecords> listSmGeographyAdvancedLevel(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveSmGeographyAdvancedLevel(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecords> listSmHomeScienceAdvancedLevel(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveSmHomeScienceAdvancedLevel(keyword);
        }
        return uploadRepo.findAll();
    }


    /*Tamil medium arts*/

    public List<UploadRecords> listTmGeographyAdvancedLevel(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveTmGeographyAdvancedLevel(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecords> listTmHomeScienceAdvancedLevel(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveTmHomeScienceAdvancedLevel(keyword);
        }
        return uploadRepo.findAll();
    }



    /*Sinhala medium Commerce*/
    public List<UploadRecords> listSmBStudiesAdvancedLevel(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveSmOrdinaryLevelMaths(keyword);
        }
        return uploadRepo.findAll();
    }


    public List<UploadRecords> listSmAccountingAdvancedLevel(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveEmOrdinaryLevelMaths(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecords> listSmEconAdvancedLevel(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveEmOrdinaryLevelMaths(keyword);
        }
        return uploadRepo.findAll();
    }




    /*Tamil medium Commerce*/
    public List<UploadRecords>   listTmBStudiesAdvancedLevel(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveEmOrdinaryLevelMaths(keyword);
        }
        return uploadRepo.findAll();
    }


    public List<UploadRecords> listTmAccountingAdvancedLevel(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveEmOrdinaryLevelMaths(keyword);
        }
        return uploadRepo.findAll();
    }


    public List<UploadRecords> listTmEconAdvancedLevel(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveEmOrdinaryLevelMaths(keyword);
        }
        return uploadRepo.findAll();
    }













}
