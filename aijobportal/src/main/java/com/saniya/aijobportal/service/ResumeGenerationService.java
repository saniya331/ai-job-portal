package com.saniya.aijobportal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saniya.aijobportal.dto.ResumeGenerationDTO;
import com.saniya.aijobportal.entity.User;
import com.saniya.aijobportal.repository.UserRepository;
import com.saniya.aijobportal.service.ai.ResumeGeneratorService;
import com.saniya.aijobportal.util.PdfReaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResumeGenerationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResumeGeneratorService resumeGeneratorService;

    public ResumeGenerationDTO generateResume(String email, String jobDescription) throws Exception {

        // Find User
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();

        // Check Resume
        if (user.getResume() == null || user.getResume().isBlank()) {
            throw new RuntimeException("Resume not uploaded");
        }

        // Read Resume PDF
        String resumeText = PdfReaderUtil.extractText(user.getResume());

        // Generate Resume using Gemini
        String aiResponse = resumeGeneratorService.generateResume(
                resumeText,
                jobDescription
        );

        System.out.println("========== RAW GEMINI RESPONSE ==========");
        System.out.println(aiResponse);
        System.out.println("=========================================");

        // Remove markdown
        aiResponse = aiResponse
                .replace("```json", "")
                .replace("```", "")
                .trim();

        // Keep only JSON
        int start = aiResponse.indexOf("{");
        int end = aiResponse.lastIndexOf("}");

        if (start != -1 && end != -1) {
            aiResponse = aiResponse.substring(start, end + 1);
        }

        System.out.println("========== CLEANED JSON ==========");
        System.out.println(aiResponse);
        System.out.println("==================================");

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(aiResponse, ResumeGenerationDTO.class);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Gemini service is currently unavailable. Please try again in a few minutes."
            );
        }
    }
}