package com.smartedulanka.finalyearproject.service;

import com.smartedulanka.finalyearproject.datalayer.entity.UploadRecords;
import com.smartedulanka.finalyearproject.datalayer.entity.User;
import com.smartedulanka.finalyearproject.repository.UploadRepository;
import com.smartedulanka.finalyearproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private UploadRepository uploadRepo;

    @Autowired
    private UserRepository userRepository;

    public List<UploadRecords> listAllSearchedPendingFiles(String keyword) {
        if (keyword != null) {
            return uploadRepo.searchRelevantPendingFiles(keyword);
        }
        return uploadRepo.findAll();
    }


    public List<UploadRecords> listAllSearchedApprovedFilesRecords(String keyword) {
        if (keyword != null) {
            return uploadRepo.searchRelevantApprovedFiles(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<UploadRecords> listAllSearchedRejectedFilesRecords(String keyword) {
        if (keyword != null) {
            return uploadRepo.searchRelevantRejectedFiles(keyword);
        }
        return uploadRepo.findAll();
    }

    public List<User> listAllSearchedUsers(String keyword) {
        if (keyword != null) {
            return userRepository.searchRelevantUsers(keyword);
        }
        return userRepository.findAll();
    }



}
