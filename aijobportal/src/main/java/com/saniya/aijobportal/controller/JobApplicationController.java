package com.saniya.aijobportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.saniya.aijobportal.entity.JobApplication;
import com.saniya.aijobportal.service.JobApplicationService;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;


    // Apply for Job
    @PostMapping
    public JobApplication apply(
            @RequestBody JobApplication application) {

        return jobApplicationService.apply(application);
    }


    // Student Applications
    @GetMapping("/student/{email}")
    public List<JobApplication> studentApplications(
            @PathVariable String email) {

        return jobApplicationService
                .getApplicationsByStudent(email);
    }


    // Job Applicants
    @GetMapping("/job/{jobId}")
    public List<JobApplication> jobApplications(
            @PathVariable Long jobId) {

        return jobApplicationService
                .getApplicationsByJob(jobId);
    }


    // Update Application Status
    @PutMapping("/{applicationId}/status")
    public JobApplication updateStatus(
            @PathVariable Long applicationId,
            @RequestParam String status) {

        return jobApplicationService
                .updateStatus(applicationId, status);
    }
}