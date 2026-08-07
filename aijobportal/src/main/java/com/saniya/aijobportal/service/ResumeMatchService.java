package com.saniya.aijobportal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saniya.aijobportal.dto.ResumeMatchDTO;
import com.saniya.aijobportal.entity.User;
import com.saniya.aijobportal.repository.UserRepository;
import com.saniya.aijobportal.service.ai.ResumeMatchGeneratorService;
import com.saniya.aijobportal.util.PdfReaderUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResumeMatchService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResumeMatchGeneratorService resumeMatchGeneratorService;

    public ResumeMatchDTO analyzeResume(
            String email,
            String jobDescription
    ) throws Exception {

        // 1. Find user
        Optional<User> optionalUser =
                userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();

        // 2. Check resume
        if (user.getResume() == null ||
                user.getResume().isBlank()) {

            throw new RuntimeException(
                    "Resume not uploaded"
            );
        }

        // 3. Check job description
        if (jobDescription == null ||
                jobDescription.isBlank()) {

            throw new RuntimeException(
                    "Job description is required"
            );
        }

        // 4. Extract resume text
        String resumeText =
                PdfReaderUtil.extractText(
                        user.getResume()
                );

        // 5. Send resume + job description to Gemini
        String aiResponse =
                resumeMatchGeneratorService.analyzeResume(
                        resumeText,
                        jobDescription
                );

        System.out.println(
                "========== RAW GEMINI RESPONSE =========="
        );

        System.out.println(aiResponse);

        System.out.println(
                "========================================="
        );

        // 6. Clean Gemini response
        String cleanedResponse = aiResponse
                .replace("```json", "")
                .replace("```", "")
                .trim();

        // 7. Extract JSON object
        int start =
                cleanedResponse.indexOf("{");

        int end =
                cleanedResponse.lastIndexOf("}");

        if (start == -1 || end == -1) {

            throw new RuntimeException(
                    "Invalid response received from Gemini"
            );
        }

        cleanedResponse =
                cleanedResponse.substring(
                        start,
                        end + 1
                );

        System.out.println(
                "========== CLEANED JSON =========="
        );

        System.out.println(cleanedResponse);

        System.out.println(
                "=================================="
        );

        // 8. Convert JSON → DTO
        ObjectMapper mapper =
                new ObjectMapper();

        try {

            return mapper.readValue(
                    cleanedResponse,
                    ResumeMatchDTO.class
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to process Gemini response. Please try again."
            );
        }
    }
}