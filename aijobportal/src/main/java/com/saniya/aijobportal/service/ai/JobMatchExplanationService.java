package com.saniya.aijobportal.service.ai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobMatchExplanationService {

    @Autowired
    private GeminiService geminiService;

    public String generateExplanation(String studentSkills,
                                      String jobTitle,
                                      String company,
                                      String requiredSkills) {

        String prompt = """
                You are an AI recruitment assistant.

                Student Skills:
                %s

                Company:
                %s

                Job Title:
                %s

                Required Skills:
                %s

                Explain in 2-3 short sentences:
                1. Why this candidate matches the job.
                2. Which skills matched.
                3. Mention missing skills (if any).
                Keep the response under 60 words.
                """
                .formatted(
                        studentSkills,
                        company,
                        jobTitle,
                        requiredSkills
                );

        
        return geminiService.askGemini(prompt);
    }
}