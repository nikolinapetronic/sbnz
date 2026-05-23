package com.sbnz.model.risks;

import com.sbnz.model.enums.RecommendationType;
import com.sbnz.model.enums.RiskLevel;

import java.util.ArrayList;
import java.util.List;

public class RiskAssessment extends AbstractRiskFact {

    private RiskLevel riskLevel;
    private String dominantDimensions;
    private List<String> activatedEvidence = new ArrayList<>();
    private List<RecommendationType> recommendations = new ArrayList<>();
    private String explanation;
    private boolean hasAcademicBurnoutPattern;

    public RiskAssessment() {
    }

    public RiskAssessment(String studentId, RiskLevel riskLevel) {
        super(studentId);
        this.riskLevel = riskLevel;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getDominantDimensions() {
        return dominantDimensions;
    }

    public void setDominantDimensions(String dominantDimensions) {
        this.dominantDimensions = dominantDimensions;
    }

    public List<String> getActivatedEvidence() {
        return activatedEvidence;
    }

    public void setActivatedEvidence(List<String> activatedEvidence) {
        this.activatedEvidence = activatedEvidence;
    }

    public List<RecommendationType> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<RecommendationType> recommendations) {
        this.recommendations = recommendations;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public boolean isHasAcademicBurnoutPattern() {
        return hasAcademicBurnoutPattern;
    }

    public void setHasAcademicBurnoutPattern(boolean hasAcademicBurnoutPattern) {
        this.hasAcademicBurnoutPattern = hasAcademicBurnoutPattern;
    }

    public void addEvidence(String evidence) {
        if (!this.activatedEvidence.contains(evidence)) {
            this.activatedEvidence.add(evidence);
        }
    }

    public void addRecommendation(RecommendationType recommendation) {
        if (!this.recommendations.contains(recommendation)) {
            this.recommendations.add(recommendation);
        }
    }
}
