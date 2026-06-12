package com.sbnz.model.scores;

public class EfficacyScore extends AbstractScore {

    public EfficacyScore() {
    }

    public EfficacyScore(String studentId, int value) {
        super(studentId, value);
    }
    
    public EfficacyScore(String studentId, int dayIndex, int value) {
        super(studentId, dayIndex, value);
    }
}
