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

        // Check duplicate application
        if (jobApplicationRepository
                .findByJobIdAndStudentEmail(
                        application.getJobId(),
                        application.getStudentEmail()
                )
                .isPresent()) {

            throw new RuntimeException(
                    "You have already applied for this job"
            );
        }

        // Set default status
        application.setStatus("APPLIED");

        // Set application time
        application.setAppliedAt(LocalDateTime.now());

        return jobApplicationRepository.save(application);
    }


    // Get applications submitted by student
    public List<JobApplication> getApplicationsByStudent(
            String email) {

        return jobApplicationRepository
                .findByStudentEmail(email);
    }


    // Get applicants for a job
    public List<JobApplication> getApplicationsByJob(
            Long jobId) {

        return jobApplicationRepository
                .findByJobId(jobId);
    }


    // Update application status
    public JobApplication updateStatus(
            Long applicationId,
            String status) {

        JobApplication application =
                jobApplicationRepository.findById(applicationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Application not found"
                                )
                        );

        application.setStatus(status.toUpperCase());

        return jobApplicationRepository.save(application);
    }
}