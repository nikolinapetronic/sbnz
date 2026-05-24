package com.sbnz.service.dto;

import com.sbnz.model.enums.AcademicPeriod;
import com.sbnz.model.enums.StudentProfile;
import com.sbnz.model.events.DailyCheckInEvent;

import java.util.ArrayList;
import java.util.List;

public class AssessmentRequest {

    private String studentId;
    private StudentProfile profileType = StudentProfile.DEFAULT;
    private AcademicPeriod academicPeriod = AcademicPeriod.REGULAR_CLASSES;

    private List<DailyCheckInEvent> dailyCheckIns = new ArrayList<>();

    public AssessmentRequest() {
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public StudentProfile getProfileType() {
        return profileType;
    }

    public void setProfileType(StudentProfile profileType) {
        this.profileType = profileType;
    }

    public AcademicPeriod getAcademicPeriod() {
        return academicPeriod;
    }

    public void setAcademicPeriod(AcademicPeriod academicPeriod) {
        this.academicPeriod = academicPeriod;
    }

    public List<DailyCheckInEvent> getDailyCheckIns() {
        return dailyCheckIns;
    }

    public void setDailyCheckIns(List<DailyCheckInEvent> dailyCheckIns) {
        this.dailyCheckIns = dailyCheckIns;
    }
}