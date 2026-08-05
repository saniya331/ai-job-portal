package com.saniya.aijobportal.dto;

import java.util.List;

public class ResumeFeedbackDTO {

    private int overallScore;
    private String feedback;
    private List<String> suggestions;
    private boolean atsFriendly;

    public ResumeFeedbackDTO() {
    }

    public int getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(int overallScore) {
        this.overallScore = overallScore;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    public boolean isAtsFriendly() {
        return atsFriendly;
    }

    public void setAtsFriendly(boolean atsFriendly) {
        this.atsFriendly = atsFriendly;
    }
}