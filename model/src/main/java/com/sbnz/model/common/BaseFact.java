package com.sbnz.model.common;

import java.io.Serializable;

public class BaseFact implements Serializable {

    private String studentId;

    public BaseFact() {
    }

    public BaseFact(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}