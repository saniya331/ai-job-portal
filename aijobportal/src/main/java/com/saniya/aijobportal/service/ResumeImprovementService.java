package com.saniya.aijobportal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saniya.aijobportal.dto.ResumeFeedbackDTO;
import com.saniya.aijobportal.entity.User;
import com.saniya.aijobportal.repository.UserRepository;
import com.saniya.aijobportal.service.ai.ResumeFeedbackService;
import com.saniya.aijobportal.util.PdfReaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResumeImprovementService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResumeFeedbackService resumeFeedbackService;

    public ResumeFeedbackDTO improveResume(String email) throws Exception {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();

        if (user.getResume() == null || user.getResume().isBlank()) {
            throw new RuntimeException("Resume not uploaded");
        }

        // Read Resume PDF
        String resumeText = PdfReaderUtil.extractText(user.getResume());

        // Gemini Response
        String aiResponse = resumeFeedbackService.analyzeResume(resumeText);

        System.out.println(aiResponse);

        return parseResponse(aiResponse);
    }

    private ResumeFeedbackDTO parseResponse(String response) {

        try {

            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(
                    response,
                    ResumeFeedbackDTO.class
            );

        } catch (Exception e) {

            throw new RuntimeException("Invalid Gemini JSON Response", e);
        }
    }
}