package com.saniya.aijobportal.dto;

public class EmployerDashboardDTO {

    private long totalJobs;
    private long totalApplications;

    public EmployerDashboardDTO() {
    }

    public EmployerDashboardDTO(long totalJobs, long totalApplications) {
        this.totalJobs = totalJobs;
        this.totalApplications = totalApplications;
    }

    public long getTotalJobs() {
        return totalJobs;
    }

    public void setTotalJobs(long totalJobs) {
        this.totalJobs = totalJobs;
    }

    public long getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(long totalApplications) {
        this.totalApplications = totalApplications;
    }
}
