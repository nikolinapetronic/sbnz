package com.sbnz.model.events;

import java.util.Date;

public class ScoreSnapshotEvent {

    private String studentId;
    private Date timestamp;
    private int dayIndex;

    private int exhaustionScore;
    private int cynicismScore;
    private int efficacyScore;

    public ScoreSnapshotEvent() {
    }

    public ScoreSnapshotEvent(String studentId,
                              Date timestamp,
                              int dayIndex,
                              int exhaustionScore,
                              int cynicismScore,
                              int efficacyScore) {
        this.studentId = studentId;
        this.timestamp = timestamp;
        this.dayIndex = dayIndex;
        this.exhaustionScore = exhaustionScore;
        this.cynicismScore = cynicismScore;
        this.efficacyScore = efficacyScore;
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

    public int getExhaustionScore() {
        return exhaustionScore;
    }

    public void setExhaustionScore(int exhaustionScore) {
        this.exhaustionScore = exhaustionScore;
    }

    public int getCynicismScore() {
        return cynicismScore;
    }

    public void setCynicismScore(int cynicismScore) {
        this.cynicismScore = cynicismScore;
    }

    public int getEfficacyScore() {
        return efficacyScore;
    }

    public void setEfficacyScore(int efficacyScore) {
        this.efficacyScore = efficacyScore;
    }
}
