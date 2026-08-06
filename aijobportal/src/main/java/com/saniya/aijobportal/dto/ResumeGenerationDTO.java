package com.saniya.aijobportal.dto;

public class ResumeGenerationDTO {

    private int atsScore;
    private String generatedResume;
    private String summary;
    private String skills;

    public ResumeGenerationDTO() {
    }

    public int getAtsScore() {
        return atsScore;
    }

    public void setAtsScore(int atsScore) {
        this.atsScore = atsScore;
    }

    public String getGeneratedResume() {
        return generatedResume;
    }

    public void setGeneratedResume(String generatedResume) {
        this.generatedResume = generatedResume;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
}