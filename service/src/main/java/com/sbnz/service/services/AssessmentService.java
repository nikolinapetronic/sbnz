package com.sbnz.service.services;

import com.sbnz.model.enums.AcademicPeriod;
import com.sbnz.model.enums.RiskLevel;
import com.sbnz.model.enums.StudentProfile;
import com.sbnz.model.events.DailyCheckInEvent;
import com.sbnz.model.profile.ThresholdProfile;
import com.sbnz.model.risks.RiskAssessment;
import com.sbnz.service.dto.AssessmentRequest;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class AssessmentService {

    private final KieContainer kieContainer;

    public AssessmentService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    public RiskAssessment assess(AssessmentRequest request) {
        KieSession ksession = kieContainer.newKieSession("studyguardSession");

        String studentId = request.getStudentId();

        ThresholdProfile thresholds = createThresholdProfile(
                studentId,
                request.getProfileType(),
                request.getAcademicPeriod()
        );

        ksession.insert(thresholds);

        List<DailyCheckInEvent> events = request.getDailyCheckIns();
        events.sort(Comparator.comparing(DailyCheckInEvent::getDayIndex));

        for (DailyCheckInEvent event : events) {
            if (event.getStudentId() == null || event.getStudentId().isBlank()) {
                event.setStudentId(studentId);
            }

            if (event.getTimestamp() == null) {
                event.setTimestamp(new Date());
            }

            ksession.insert(event);

        }

        int firedRules = ksession.fireAllRules(1000);

        if (firedRules >= 1000) {
            ksession.dispose();
            throw new IllegalStateException("Drools je aktivirao 1000 pravila. Moguca beskonacna petlja u pravilima.");
        }


        RiskAssessment assessment = findRiskAssessment(ksession, studentId);

        if (assessment == null) {
            assessment = new RiskAssessment(studentId, RiskLevel.LOW_RISK);
            assessment.setDominantDimensions("nema izrazenog obrasca");
            assessment.setExplanation("Nije detektovan dovoljan broj indikatora i vremenskih obrazaca za povisen rizik.");
        }

        boolean hasAcademicBurnoutPattern = hasAcademicBurnoutPattern(ksession, studentId);
        assessment.setHasAcademicBurnoutPattern(hasAcademicBurnoutPattern);

        ksession.dispose();

        return assessment;
    }

    private RiskAssessment findRiskAssessment(KieSession ksession, String studentId) {
        RiskAssessment best = null;

        for (Object object : ksession.getObjects()) {
            if (object instanceof RiskAssessment) {
                RiskAssessment assessment = (RiskAssessment) object;
                if (studentId.equals(assessment.getStudentId())) {
                    if (best == null || isHigherRisk(assessment, best)) {
                        best = assessment;
                    }
                }
            }
        }

        return best;
    }

    private boolean isHigherRisk(RiskAssessment candidate, RiskAssessment current) {
        return candidate.getRiskLevel().ordinal() > current.getRiskLevel().ordinal();
    }

    private ThresholdProfile createThresholdProfile(String studentId,
                                                    StudentProfile profileType,
                                                    AcademicPeriod academicPeriod) {
        if (academicPeriod == AcademicPeriod.EXAM_PERIOD) {
            return new ThresholdProfile(studentId, profileType, academicPeriod, 6, 7, 5, 2, 6, 5);
        }

        if (profileType == StudentProfile.WORKING_STUDENT) {
            return new ThresholdProfile(studentId, profileType, academicPeriod, 5, 8, 4, 3, 7, 4);
        }

        if (profileType == StudentProfile.FINAL_YEAR) {
            return new ThresholdProfile(studentId, profileType, academicPeriod, 6, 7, 4, 2, 5, 4);
        }

        if (profileType == StudentProfile.FRESHMAN) {
            return new ThresholdProfile(studentId, profileType, academicPeriod, 6, 7, 4, 2, 6, 5);
        }

        return new ThresholdProfile(studentId, profileType, academicPeriod, 6, 7, 4, 2, 5, 5);
    }

    private boolean hasAcademicBurnoutPattern(KieSession ksession, String studentId) {
        QueryResults results = ksession.getQueryResults("hasAcademicBurnoutPattern", studentId);
        return results.size() > 0;
    }

}
