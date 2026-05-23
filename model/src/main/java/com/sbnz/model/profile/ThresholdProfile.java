package com.sbnz.model.profile;

import com.sbnz.model.common.BaseFact;
import com.sbnz.model.enums.AcademicPeriod;
import com.sbnz.model.enums.StudentProfile;

public class ThresholdProfile extends BaseFact {

    private StudentProfile profileType;
    private AcademicPeriod academicPeriod;

    private double minSleepHours;
    private int highStressThreshold;
    private int lowEfficacyThreshold;
    private int maxMissedObligations;

    public ThresholdProfile() {
    }

    public ThresholdProfile(String studentId,
                            StudentProfile profileType,
                            AcademicPeriod academicPeriod,
                            double minSleepHours,
                            int highStressThreshold,
                            int lowEfficacyThreshold,
                            int maxMissedObligations) {
        super(studentId);
        this.profileType = profileType;
        this.academicPeriod = academicPeriod;
        this.minSleepHours = minSleepHours;
        this.highStressThreshold = highStressThreshold;
        this.lowEfficacyThreshold = lowEfficacyThreshold;
        this.maxMissedObligations = maxMissedObligations;
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

    public double getMinSleepHours() {
        return minSleepHours;
    }

    public void setMinSleepHours(double minSleepHours) {
        this.minSleepHours = minSleepHours;
    }

    public int getHighStressThreshold() {
        return highStressThreshold;
    }

    public void setHighStressThreshold(int highStressThreshold) {
        this.highStressThreshold = highStressThreshold;
    }

    public int getLowEfficacyThreshold() {
        return lowEfficacyThreshold;
    }

    public void setLowEfficacyThreshold(int lowEfficacyThreshold) {
        this.lowEfficacyThreshold = lowEfficacyThreshold;
    }

    public int getMaxMissedObligations() {
        return maxMissedObligations;
    }

    public void setMaxMissedObligations(int maxMissedObligations) {
        this.maxMissedObligations = maxMissedObligations;
    }
}
