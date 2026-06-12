package com.sbnz.model.context;

import com.sbnz.model.common.BaseFact;
import com.sbnz.model.enums.AcademicPeriod;
import com.sbnz.model.enums.StudentProfile;

public class StudentContext extends BaseFact {

    private StudentProfile profileType;
    private AcademicPeriod academicPeriod;

    public StudentContext() {
    }

    public StudentContext(String studentId, StudentProfile profileType, AcademicPeriod academicPeriod) {
        super(studentId);
        this.profileType = profileType;
        this.academicPeriod = academicPeriod;
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
}
