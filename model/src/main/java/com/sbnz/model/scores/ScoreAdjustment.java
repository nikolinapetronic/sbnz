package com.sbnz.model.scores;

import com.sbnz.model.common.BaseFact;

public class ScoreAdjustment extends BaseFact {

    private int dayIndex;
    private String dimension;
    private String reason;
    private int value;

    public ScoreAdjustment() {
    }

    public ScoreAdjustment(String studentId, int dayIndex, String dimension, String reason, int value) {
        super(studentId);
        this.dayIndex = dayIndex;
        this.dimension = dimension;
        this.reason = reason;
        this.value = value;
    }

    public int getDayIndex() {
        return dayIndex;
    }

    public void setDayIndex(int dayIndex) {
        this.dayIndex = dayIndex;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
