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


    // =====================================
    // ADD JOB
    // =====================================

    public Job addJob(Job job, String recruiterEmail) {

        job.setPostedBy(recruiterEmail);

        job.setCreatedAt(
                LocalDateTime.now()
        );

        return jobRepository.save(job);
    }


    // =====================================
    // GET ALL JOBS
    // =====================================

    public List<Job> getAllJobs() {

        return jobRepository.findAll();

    }


    // =====================================
    // GET JOB BY ID
    // =====================================

    public Job getJobById(Long id) {

        return jobRepository
                .findById(id)
                .orElse(null);

    }


    // =====================================
// UPDATE JOB
// =====================================

public Job updateJob(Long id, Job updatedJob) {

    System.out.println("=================================");
    System.out.println("UPDATE JOB REQUEST");
    System.out.println("Job ID: " + id);

    Job existingJob =
            jobRepository.findById(id).orElse(null);

    if (existingJob == null) {

        System.out.println("JOB NOT FOUND: " + id);

        return null;
    }

    System.out.println("EXISTING JOB FOUND:");
    System.out.println("Old Title: " + existingJob.getTitle());

    System.out.println("NEW DATA:");
    System.out.println("Title: " + updatedJob.getTitle());
    System.out.println("Company: " + updatedJob.getCompany());
    System.out.println("Location: " + updatedJob.getLocation());
    System.out.println("Salary: " + updatedJob.getSalary());


    existingJob.setTitle(
            updatedJob.getTitle()
    );

    existingJob.setCompany(
            updatedJob.getCompany()
    );

    existingJob.setLocation(
            updatedJob.getLocation()
    );

    existingJob.setDescription(
            updatedJob.getDescription()
    );

    existingJob.setRequiredSkills(
            updatedJob.getRequiredSkills()
    );

    existingJob.setSalary(
            updatedJob.getSalary()
    );


    Job savedJob =
            jobRepository.save(existingJob);


    System.out.println("UPDATED JOB:");
    System.out.println("ID: " + savedJob.getId());
    System.out.println("Title: " + savedJob.getTitle());
    System.out.println("Company: " + savedJob.getCompany());


    return savedJob;
}

    // =====================================
    // DELETE JOB
    // =====================================

    public void deleteJob(Long id) {

        jobRepository.deleteById(id);

    }


    // =====================================
    // SEARCH BY COMPANY
    // =====================================

    public List<Job> getJobsByCompany(
            String company) {

        return jobRepository
                .findByCompanyContainingIgnoreCase(
                        company
                );

    }


    // =====================================
    // SEARCH BY LOCATION
    // =====================================

    public List<Job> getJobsByLocation(
            String location) {

        return jobRepository
                .findByLocationContainingIgnoreCase(
                        location
                );

    }


    // =====================================
    // SEARCH BY SKILL
    // =====================================

    public List<Job> getJobsBySkill(
            String skill) {

        return jobRepository
                .findByRequiredSkillsContaining(
                        skill
                );

    }


    // =====================================
    // GET JOBS BY RECRUITER
    // =====================================

    public List<Job> getJobsByEmployer(
            String email) {

        return jobRepository
                .findByPostedBy(email);

    }

}