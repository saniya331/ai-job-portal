package com.saniya.aijobportal.controller;

import com.saniya.aijobportal.dto.ResumeFeedbackDTO;
import com.saniya.aijobportal.service.ResumeImprovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resume")
public class ResumeImprovementController {

    @Autowired
    private ResumeImprovementService resumeImprovementService;

    @GetMapping("/improve/{email}")
    public ResumeFeedbackDTO improveResume(
            @PathVariable String email) throws Exception {

        return resumeImprovementService.improveResume(email);
    }
}
