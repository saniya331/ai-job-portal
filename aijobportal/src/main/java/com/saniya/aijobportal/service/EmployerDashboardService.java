package com.saniya.aijobportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saniya.aijobportal.dto.EmployerDashboardDTO;
import com.saniya.aijobportal.repository.JobApplicationRepository;
import com.saniya.aijobportal.repository.JobRepository;

@Service
public class EmployerDashboardService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    public EmployerDashboardDTO getDashboard(String employerEmail) {

        long totalJobs = jobRepository.countByPostedBy(employerEmail);

        long totalApplications = jobApplicationRepository.count();

        return new EmployerDashboardDTO(
                totalJobs,
                totalApplications
        );
    }
}
