package com.saniya.aijobportal.service;

import com.saniya.aijobportal.entity.User;
import com.saniya.aijobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class ResumeService {

    @Autowired
    private UserRepository userRepository;

    // Save files inside your project folder
    private static final String UPLOAD_DIR =
            System.getProperty("user.dir") + File.separator + "uploads";

    public void uploadResume(MultipartFile file, String email) throws IOException {

        // Create uploads folder if it doesn't exist
        File uploadFolder = new File(UPLOAD_DIR);

        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        // Create unique filename
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // Full destination path
        File destination = new File(uploadFolder, fileName);

        // Save file
        file.transferTo(destination);

        // Update database
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setResume(destination.getAbsolutePath());   // save full path
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }
}