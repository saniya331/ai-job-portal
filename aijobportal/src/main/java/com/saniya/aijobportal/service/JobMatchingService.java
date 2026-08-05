package com.saniya.aijobportal.service;

import com.saniya.aijobportal.dto.JobMatchDTO;
import com.saniya.aijobportal.entity.Job;
import com.saniya.aijobportal.entity.User;
import com.saniya.aijobportal.repository.JobRepository;
import com.saniya.aijobportal.repository.UserRepository;
import com.saniya.aijobportal.service.ai.JobMatchExplanationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JobMatchingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobMatchExplanationService explanationService;

    public List<JobMatchDTO> getMatchingJobs(String email) {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();

        String studentSkills = user.getSkills();

        if (studentSkills == null || studentSkills.isBlank()) {
            throw new RuntimeException("No skills found for this user");
        }

        List<Job> jobs = jobRepository.findAll();

        List<JobMatchDTO> matchedJobs = new ArrayList<>();

        for (Job job : jobs) {

            int match = calculateMatch(
                    studentSkills,
                    job.getRequiredSkills()
            );

            JobMatchDTO dto = new JobMatchDTO();

            dto.setJobId(job.getId());
            dto.setCompanyName(job.getCompany());
            dto.setJobTitle(job.getTitle());
            dto.setLocation(job.getLocation());
            dto.setMatchPercentage(match);

            // Generate AI explanation
            String reason = explanationService.generateExplanation(
                    studentSkills,
                    job.getTitle(),
                    job.getCompany(),
                    job.getRequiredSkills()
            );

            dto.setReason(reason);

            matchedJobs.add(dto);
        }

        // Sort by highest match percentage
        matchedJobs.sort((a, b) ->
                Integer.compare(
                        b.getMatchPercentage(),
                        a.getMatchPercentage()
                ));

        return matchedJobs;
    }

    private int calculateMatch(String studentSkills,
                               String requiredSkills) {

        if (requiredSkills == null || requiredSkills.isBlank()) {
            return 0;
        }

        Set<String> studentSkillSet = new HashSet<>();

        for (String skill : studentSkills.split(",")) {
            studentSkillSet.add(skill.trim().toLowerCase());
        }

        String[] requiredSkillArray = requiredSkills.split(",");

        int matched = 0;

        for (String skill : requiredSkillArray) {

            if (studentSkillSet.contains(skill.trim().toLowerCase())) {
                matched++;
            }
        }

        return (matched * 100) / requiredSkillArray.length;
    }
}