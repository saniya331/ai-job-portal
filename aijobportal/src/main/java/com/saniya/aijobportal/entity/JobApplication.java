package com.saniya.aijobportal.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "job_applications")
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long jobId;

    private String studentEmail;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    private LocalDateTime appliedAt;

    // Default constructor
    public JobApplication() {
    }

    // Get ID
    public Long getId() {
        return id;
    }

    // Get Job ID
    public Long getJobId() {
        return jobId;
    }

    // Set Job ID
    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    // Get Student Email
    public String getStudentEmail() {
        return studentEmail;
    }

    // Set Student Email
    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    // Get Status
    public ApplicationStatus getStatus() {
        return status;
    }

    // Set Status
    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    // Get Applied Time
    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    // Set Applied Time
    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }
}