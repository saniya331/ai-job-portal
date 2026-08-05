package com.saniya.aijobportal.service.ai;

import com.google.genai.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ResumeFeedbackService {

    @Value("${gemini.api.key}")
    private String apiKey;

    public String analyzeResume(String resumeText) {

        String prompt = """
You are an expert ATS Resume Reviewer.

Analyze the following resume.

Return ONLY valid JSON.

Do NOT add explanations.
Do NOT use markdown.
Do NOT use ```json.
Do NOT write anything outside the JSON object.

Return exactly in this format:

{
  "overallScore": 90,
  "feedback": "One short paragraph",
  "suggestions": [
    "Suggestion 1",
    "Suggestion 2",
    "Suggestion 3",
    "Suggestion 4"
  ],
  "atsFriendly": true
}

Rules:
- overallScore must be an integer between 0 and 100.
- atsFriendly must be true or false.
- suggestions must contain exactly 4 items.
- feedback should be concise.
- Return ONLY the JSON.

Resume:

""" + resumeText;

        Client client = Client.builder()
                .apiKey(apiKey)
                .build();

        return client.models.generateContent(
                "gemini-2.5-flash",
                prompt,
                null
        ).text();
    }
}