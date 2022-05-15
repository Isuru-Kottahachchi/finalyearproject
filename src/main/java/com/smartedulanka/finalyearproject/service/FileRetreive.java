package com.smartedulanka.finalyearproject.service;

import com.smartedulanka.finalyearproject.datalayer.entity.UploadRecord;
import com.smartedulanka.finalyearproject.repository.UploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileRetreive {

    /*Retrieve only combined maths files*/
    @Autowired
    private UploadRepository uploadRepo;

    public List<UploadRecord> findPendingFiles(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrievePendingFiles(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecord> retrieveApprovedFiles(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveApprovedFiles(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecord> retrieveRejectedFiles(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveApprovedFiles(keyword);
        }
        return uploadRepo.findAll();
    }










    public List<UploadRecord> listCombinedMaths(String keyword) {
        if (keyword != null) {
            return uploadRepo.searchCombinedMaths(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecord> listBiology(String keyword) {
        if (keyword != null) {
            return uploadRepo.searchBiology(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecord> listChemistry(String keyword) {
        if (keyword != null) {
            return uploadRepo.searchChemistry(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecord> listPhysics(String keyword) {
        if (keyword != null) {
            return uploadRepo.searchPhysics(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecord> listInformationTechnology(String keyword) {
        if (keyword != null) {
            return uploadRepo.searchInformationTechnology(keyword);
        }
        return uploadRepo.findAll();
    }
}
