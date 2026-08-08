package com.saniya.aijobportal.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.saniya.aijobportal.entity.Job;
import com.saniya.aijobportal.service.JobService;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;


    // =====================================
    // ADD JOB
    // =====================================

    @PostMapping
    public Job addJob(
            @RequestBody Job job,
            Authentication authentication) {

        String recruiterEmail =
                authentication.getName();


        return jobService.addJob(
                job,
                recruiterEmail
        );
    }


    // =====================================
    // GET ALL JOBS
    // =====================================

    @GetMapping
    public List<Job> getAllJobs() {

        return jobService.getAllJobs();

    }


    // =====================================
    // GET JOB BY ID
    // =====================================

    @GetMapping("/{id}")
    public Job getJobById(
            @PathVariable Long id) {

        return jobService.getJobById(id);

    }


    // =====================================
    // UPDATE JOB
    // =====================================

    @PutMapping("/{id}")
public ResponseEntity<?> updateJob(
        @PathVariable Long id,
        @RequestBody Job job) {

    System.out.println("=================================");
    System.out.println("UPDATE REQUEST RECEIVED");
    System.out.println("Job ID: " + id);
    System.out.println("Title: " + job.getTitle());
    System.out.println("Company: " + job.getCompany());
    System.out.println("Location: " + job.getLocation());
    System.out.println("Salary: " + job.getSalary());

    Job updatedJob =
            jobService.updateJob(id, job);

    if (updatedJob == null) {

        return ResponseEntity
                .status(404)
                .body("Job with ID " + id + " not found");

    }

    return ResponseEntity.ok(updatedJob);
}

    // =====================================
    // DELETE JOB
    // =====================================

    @DeleteMapping("/{id}")
    public String deleteJob(
            @PathVariable Long id) {

        jobService.deleteJob(id);

        return "Job deleted successfully";

    }


    // =====================================
    // SEARCH BY COMPANY
    // =====================================

    @GetMapping("/company/{company}")
    public List<Job> getJobsByCompany(
            @PathVariable String company) {

        return jobService
                .getJobsByCompany(company);

    }


    // =====================================
    // SEARCH BY LOCATION
    // =====================================

    @GetMapping("/location/{location}")
    public List<Job> getJobsByLocation(
            @PathVariable String location) {

        return jobService
                .getJobsByLocation(location);

    }


    // =====================================
    // SEARCH BY SKILL
    // =====================================

    @GetMapping("/skill/{skill}")
    public List<Job> getJobsBySkill(
            @PathVariable String skill) {

        return jobService
                .getJobsBySkill(skill);

    }


    // =====================================
    // JOBS POSTED BY EMPLOYER
    // =====================================

    @GetMapping("/employer/{email}")
    public List<Job> getJobsByEmployer(
            @PathVariable String email) {

        return jobService
                .getJobsByEmployer(email);

    }

}