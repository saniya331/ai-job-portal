package com.saniya.aijobportal.controller;

import com.saniya.aijobportal.dto.ResumeMatchDTO;
import com.saniya.aijobportal.dto.ResumeMatchRequest;
import com.saniya.aijobportal.service.ResumeMatchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resume-match")
public class ResumeMatchController {

    @Autowired
    private ResumeMatchService resumeMatchService;

    @PostMapping("/analyze/{email}")
    public ResumeMatchDTO analyzeResume(

            @PathVariable String email,

            @RequestBody ResumeMatchRequest request

    ) throws Exception {

        return resumeMatchService.analyzeResume(
                email,
                request.getJobDescription()
        );
    }
}