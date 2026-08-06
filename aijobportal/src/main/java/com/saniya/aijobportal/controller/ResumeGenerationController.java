package com.saniya.aijobportal.controller;

import com.saniya.aijobportal.dto.ResumeGenerationDTO;
import com.saniya.aijobportal.dto.ResumeGenerationRequest;
import com.saniya.aijobportal.service.ResumeGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resume")
public class ResumeGenerationController {

    @Autowired
    private ResumeGenerationService resumeGenerationService;

    @PostMapping("/generate/{email}")
    public ResumeGenerationDTO generateResume(

            @PathVariable String email,

            @RequestBody ResumeGenerationRequest request

    ) throws Exception {

        return resumeGenerationService.generateResume(
                email,
                request.getJobDescription()
        );
    }
}
