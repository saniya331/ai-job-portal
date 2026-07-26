package com.saniya.aijobportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.saniya.aijobportal.entity.Job;
import com.saniya.aijobportal.service.JobService;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    // Add Job
    @PostMapping
    public Job addJob(@RequestBody Job job) {
        return jobService.addJob(job);
    }

    // Get All Jobs
    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }
    
    // Get Job By ID
    @GetMapping("/{id}")
    public Job getJobById(@PathVariable Long id) {
        return jobService.getJobById(id);
    }
    // Update Job
@PutMapping("/{id}")
public Job updateJob(@PathVariable Long id, @RequestBody Job job) {
    return jobService.updateJob(id, job);
}
// Delete Job
@DeleteMapping("/{id}")
public String deleteJob(@PathVariable Long id) {

    jobService.deleteJob(id);

    return "Job deleted successfully";
}
// Search by Company
@GetMapping("/company/{company}")
public List<Job> getJobsByCompany(@PathVariable String company) {
    return jobService.getJobsByCompany(company);
}

// Search by Location
@GetMapping("/location/{location}")
public List<Job> getJobsByLocation(@PathVariable String location) {
    return jobService.getJobsByLocation(location);
}

// Search by Skill
@GetMapping("/skill/{skill}")
public List<Job> getJobsBySkill(@PathVariable String skill) {
    return jobService.getJobsBySkill(skill);
}
// Jobs Posted By Employer
@GetMapping("/employer/{email}")
public List<Job> getJobsByEmployer(@PathVariable String email) {
    return jobService.getJobsByEmployer(email);
}
}