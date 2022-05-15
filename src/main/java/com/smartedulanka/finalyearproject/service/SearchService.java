package com.smartedulanka.finalyearproject.service;

import com.smartedulanka.finalyearproject.datalayer.entity.UploadRecord;
import com.smartedulanka.finalyearproject.repository.UploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private UploadRepository uploadRepo;

    public List<UploadRecord> listAll(String keyword) {
        if (keyword != null) {
            return uploadRepo.search(keyword);
        }
        return uploadRepo.findAll();
    }


    public List<UploadRecord> listSubmittedFilesRecords(String keyword) {
        if (keyword != null) {
            return uploadRepo.retrieveApprovedFiles(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecord> listRejectedFilesRecords(String keyword) {
        if (keyword != null) {
            return uploadRepo. searchRejectedFiles(keyword);
        }
        return uploadRepo.findAll();
    }



}
