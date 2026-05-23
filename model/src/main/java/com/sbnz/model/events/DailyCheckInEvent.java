package com.sbnz.model.events;

import java.util.Date;

import com.sbnz.model.enums.CopingStrategy;

public class DailyCheckInEvent {

    private String studentId;
    private Date timestamp;

    private int dayIndex;

    private double sleepHours;
    private String sleepQuality;

    private int fatigue;
    private int stress;
    private int motivation;
    private int concentration;
    private int efficacy;

    private boolean missedObligation;
    private boolean lowRecovery;

    private int supportScore;
    private CopingStrategy copingStrategy;

    public DailyCheckInEvent() {
    }

    public DailyCheckInEvent(String studentId,
                             Date timestamp,
                             int dayIndex,
                             double sleepHours,
                             String sleepQuality,
                             int fatigue,
                             int stress,
                             int motivation,
                             int concentration,
                             int efficacy,
                             boolean missedObligation,
                             boolean lowRecovery,
                             int supportScore,
                             CopingStrategy copingStrategy) {
        this.studentId = studentId;
        this.timestamp = timestamp;
        this.dayIndex = dayIndex;
        this.sleepHours = sleepHours;
        this.sleepQuality = sleepQuality;
        this.fatigue = fatigue;
        this.stress = stress;
        this.motivation = motivation;
        this.concentration = concentration;
        this.efficacy = efficacy;
        this.missedObligation = missedObligation;
        this.lowRecovery = lowRecovery;
        this.supportScore = supportScore;
        this.copingStrategy = copingStrategy;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getDayIndex() {
        return dayIndex;
    }

    public void setDayIndex(int dayIndex) {
        this.dayIndex = dayIndex;
    }

    public double getSleepHours() {
        return sleepHours;
    }

    public void setSleepHours(double sleepHours) {
        this.sleepHours = sleepHours;
    }

    public String getSleepQuality() {
        return sleepQuality;
    }

    public void setSleepQuality(String sleepQuality) {
        this.sleepQuality = sleepQuality;
    }

    public int getFatigue() {
        return fatigue;
    }

    public void setFatigue(int fatigue) {
        this.fatigue = fatigue;
    }

    public int getStress() {
        return stress;
    }

    public void setStress(int stress) {
        this.stress = stress;
    }

    public int getMotivation() {
        return motivation;
    }

    public void setMotivation(int motivation) {
        this.motivation = motivation;
    }

    public int getConcentration() {
        return concentration;
    }

    public void setConcentration(int concentration) {
        this.concentration = concentration;
    }

    public int getEfficacy() {
        return efficacy;
    }

    public void setEfficacy(int efficacy) {
        this.efficacy = efficacy;
    }

    public boolean isMissedObligation() {
        return missedObligation;
    }

    public void setMissedObligation(boolean missedObligation) {
        this.missedObligation = missedObligation;
    }

    public boolean isLowRecovery() {
        return lowRecovery;
    }

    public void setLowRecovery(boolean lowRecovery) {
        this.lowRecovery = lowRecovery;
    }

    public int getSupportScore() {
        return supportScore;
    }

    public void setSupportScore(int supportScore) {
        this.supportScore = supportScore;
    }

    public CopingStrategy getCopingStrategy() {
        return copingStrategy;
    }

    public void setCopingStrategy(CopingStrategy copingStrategy) {
        this.copingStrategy = copingStrategy;
    }
}