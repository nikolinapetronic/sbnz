package com.sbnz.model.scores;

public class CynicismScore extends AbstractScore {
    
    public CynicismScore() {
    }

    public CynicismScore(String studentId, int value) {
        super(studentId, value);
    }

    public CynicismScore(String studentId, int dayIndex, int value) {
        super(studentId, dayIndex, value);
    }
}
