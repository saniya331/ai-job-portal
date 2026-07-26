package com.saniya.aijobportal.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saniya.aijobportal.entity.Job;
import com.saniya.aijobportal.repository.JobRepository;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    // Add Job
    public Job addJob(Job job) {
        job.setCreatedAt(LocalDateTime.now());
        return jobRepository.save(job);
    }

    // Get All Jobs
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // Get Job By ID
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }
    // Update Job
public Job updateJob(Long id, Job updatedJob) {

    Job existingJob = jobRepository.findById(id).orElse(null);

    if (existingJob == null) {
        return null;
    }

    existingJob.setTitle(updatedJob.getTitle());
    existingJob.setCompany(updatedJob.getCompany());
    existingJob.setLocation(updatedJob.getLocation());
    existingJob.setSalary(updatedJob.getSalary());
    existingJob.setDescription(updatedJob.getDescription());
    existingJob.setRequiredSkills(updatedJob.getRequiredSkills());

    return jobRepository.save(existingJob);
}
// Delete Job
public void deleteJob(Long id) {
    jobRepository.deleteById(id);
}
// Search by Company
public List<Job> getJobsByCompany(String company) {
    return jobRepository.findByCompany(company);
}

// Search by Location
public List<Job> getJobsByLocation(String location) {
    return jobRepository.findByLocation(location);
}

// Search by Skill
public List<Job> getJobsBySkill(String skill) {
    return jobRepository.findByRequiredSkillsContaining(skill);
}
// Get Jobs Posted By Employer
public List<Job> getJobsByEmployer(String email) {
    return jobRepository.findByPostedBy(email);
}
}