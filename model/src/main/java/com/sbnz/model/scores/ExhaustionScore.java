package com.sbnz.model.scores;

public class ExhaustionScore extends AbstractScore {
    
    public ExhaustionScore() {
    }

    public ExhaustionScore(String studentId, int value) {
        super(studentId, value);
    }

    public ExhaustionScore(String studentId, int dayIndex, int value) {
        super(studentId, dayIndex, value);
    }
}
