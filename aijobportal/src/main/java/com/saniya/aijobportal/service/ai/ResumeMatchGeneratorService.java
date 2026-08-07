package com.saniya.aijobportal.service.ai;

import com.google.genai.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ResumeMatchGeneratorService {

    @Value("${gemini.api.key}")
    private String apiKey;

    public String analyzeResume(String resume, String jobDescription) {

        String prompt = """
You are an expert AI recruitment and ATS analysis system.

Analyze the candidate's resume against the provided job description.

Your task is to calculate how well the resume matches the job.

IMPORTANT RULES:

- Return ONLY valid JSON.
- Do NOT use markdown.
- Do NOT use ```json.
- Do NOT write explanations outside JSON.
- Do NOT write anything before the JSON.
- Do NOT write anything after the JSON.
- Return exactly one JSON object.

Return EXACTLY in this format:

{
  "matchScore": 85,
  "matchedSkills": "Java, Spring Boot, MySQL",
  "missingSkills": "Docker, AWS, Microservices",
  "recommendation": "Learn Docker, AWS and Microservices to improve the job match."
}

Rules:

- matchScore must be an integer between 0 and 100.
- matchedSkills must contain skills present in both the resume and job description.
- missingSkills must contain important skills required by the job description but missing from the resume.
- recommendation must provide practical advice to improve the candidate's match.
- Do not invent skills that are not present in the resume.
- Return ONLY JSON.

CANDIDATE RESUME:

"""
                + resume
                +
                """

JOB DESCRIPTION:

"""
                + jobDescription;

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
