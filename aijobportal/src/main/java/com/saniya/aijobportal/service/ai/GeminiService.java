package com.saniya.aijobportal.service.ai;

import com.google.genai.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    public String askGemini(String prompt) {

        Client client = Client.builder()
                .apiKey(apiKey)
                .build();

        return client.models.generateContent(
                "gemini-2.5-flash",
                prompt,
                null
        ).text();
    }

    public String extractSkills(String resumeText) {

        String prompt = """
                Extract only technical skills from the following resume.

                Return only a comma-separated list.

                Resume:
                """ + resumeText;

        return askGemini(prompt);
    }
}