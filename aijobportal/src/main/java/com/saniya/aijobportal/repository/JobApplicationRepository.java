package com.saniya.aijobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saniya.aijobportal.entity.JobApplication;

public interface JobApplicationRepository
        extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByStudentEmail(String studentEmail);

    List<JobApplication> findByJobId(Long jobId);
}