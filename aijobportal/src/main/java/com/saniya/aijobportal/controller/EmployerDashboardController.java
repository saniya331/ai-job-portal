package com.saniya.aijobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.saniya.aijobportal.dto.EmployerDashboardDTO;
import com.saniya.aijobportal.service.EmployerDashboardService;

@RestController
@RequestMapping("/api/employer")
public class EmployerDashboardController {

    @Autowired
    private EmployerDashboardService employerDashboardService;

    @GetMapping("/dashboard")
    public EmployerDashboardDTO getDashboard(
            @RequestParam String employerEmail) {

        return employerDashboardService.getDashboard(employerEmail);
    }
}
