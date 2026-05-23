package com.sbnz.model.scores;

import com.sbnz.model.common.BaseFact;

public abstract class AbstractScore extends BaseFact {

    private int value;

    public AbstractScore() {
    }

    public AbstractScore(String studentId, int value) {
        super(studentId);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
