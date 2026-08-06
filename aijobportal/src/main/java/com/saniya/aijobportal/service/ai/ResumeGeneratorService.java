package com.saniya.aijobportal.service.ai;

import com.google.genai.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ResumeGeneratorService {

    @Value("${gemini.api.key}")
    private String apiKey;

    public String generateResume(String resume, String jobDescription) {

        String prompt = """
You are an expert ATS Resume Writer.

Your task is to rewrite the user's resume according to the given Job Description while remaining completely truthful.

STRICT INSTRUCTIONS:

1. Return ONLY a valid JSON object.
2. Do NOT use Markdown.
3. Do NOT use ``` or ```json.
4. Do NOT write any explanation.
5. Do NOT write any heading.
6. Do NOT write any extra text before or after the JSON.
7. The response MUST start with '{' and end with '}'.
8. Escape all newline characters inside strings using \\n.
9. Every JSON property name and value must use double quotes.

Return exactly this schema:

{
  "generatedResume": "Complete ATS optimized resume",
  "atsScore": 95,
  "summary": "Professional summary",
  "skills": "Java, Spring Boot, MySQL, REST APIs"
}

Rules:

- atsScore must be an integer from 0 to 100.
- generatedResume must be a complete ATS-friendly resume.
- summary should contain 2-3 sentences.
- skills must be a comma-separated string.
- Do NOT add any additional fields.

Resume:

%s

Job Description:

%s
""".formatted(resume, jobDescription);

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