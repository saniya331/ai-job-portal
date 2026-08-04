package com.saniya.aijobportal.controller;

import com.saniya.aijobportal.dto.JobMatchDTO;
import com.saniya.aijobportal.service.JobMatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobMatchingController {

    @Autowired
    private JobMatchingService jobMatchingService;

    @GetMapping("/match/{email}")
    public List<JobMatchDTO> getMatchingJobs(@PathVariable String email) {

        return jobMatchingService.getMatchingJobs(email);
    }
}