package com.sbnz.model.scores;

import com.sbnz.model.common.BaseFact;

public abstract class AbstractScore extends BaseFact {

    private int dayIndex;
    private int value;

    public AbstractScore() {
    }

    public AbstractScore(String studentId, int value) {
        super(studentId);
        this.dayIndex = -1;
        this.value = value;
    }

    public AbstractScore(String studentId, int dayIndex, int value) {
        super(studentId);
        this.dayIndex = dayIndex;
        this.value = value;
    }

    public int getDayIndex() {
        return dayIndex;
    }

    public void setDayIndex(int dayIndex) {
        this.dayIndex = dayIndex;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
