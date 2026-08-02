package com.saniya.aijobportal.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saniya.aijobportal.entity.JobApplication;
import com.saniya.aijobportal.repository.JobApplicationRepository;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    // Apply for Job
    public JobApplication apply(JobApplication application) {

        application.setAppliedAt(LocalDateTime.now());
        application.setStatus("APPLIED");

        return jobApplicationRepository.save(application);
    }

    // Student's Applications
    public List<JobApplication> getApplicationsByStudent(String email) {
        return jobApplicationRepository.findByStudentEmail(email);
    }

    // Applications for a Job
    public List<JobApplication> getApplicationsByJob(Long jobId) {
        return jobApplicationRepository.findByJobId(jobId);
    }
}
