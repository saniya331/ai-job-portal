package com.saniya.aijobportal.service;

import com.saniya.aijobportal.entity.User;
import com.saniya.aijobportal.repository.UserRepository;
import com.saniya.aijobportal.service.ai.GeminiService;
import com.saniya.aijobportal.util.PdfReaderUtil;

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

    @Autowired
    private GeminiService geminiService;

    // Save uploaded resumes inside the project folder
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

        // Destination file
        File destination = new File(uploadFolder, fileName);

        // Save uploaded file
        file.transferTo(destination);

        // Read resume text using PDFBox
        String resumeText = PdfReaderUtil.extractText(destination.getAbsolutePath());

        System.out.println("========== Resume Text ==========");
        System.out.println(resumeText);
        System.out.println("=================================");

        // Extract skills using Gemini AI
        String extractedSkills = geminiService.extractSkills(resumeText);

        System.out.println("========== Extracted Skills ==========");
        System.out.println(extractedSkills);
        System.out.println("======================================");

        // Update database
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();

            // Save resume path
            user.setResume(destination.getAbsolutePath());

            // Save extracted skills
            user.setSkills(extractedSkills);

            userRepository.save(user);

        } else {
            throw new RuntimeException("User not found");
        }
    }
}