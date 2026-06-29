package com.sbnz.service.services;

import com.sbnz.model.context.StudentContext;
import com.sbnz.model.enums.RiskLevel;
import com.sbnz.model.events.DailyCheckInEvent;
import com.sbnz.model.risks.RiskAssessment;
import com.sbnz.service.dto.AssessmentRequest;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class AssessmentService {

    private static final String SESSION_NAME = "studyguardSession";
    private static final int MAX_FIRED_RULES = 1000;

    private final KieContainer kieContainer;

    public AssessmentService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    public RiskAssessment assess(AssessmentRequest request) {
        KieSession ksession = kieContainer.newKieSession(SESSION_NAME);

        try {
            String studentId = request.getStudentId();

            StudentContext studentContext = new StudentContext(
                    studentId,
                    request.getProfileType(),
                    request.getAcademicPeriod()
            );

            ksession.insert(studentContext);

            List<DailyCheckInEvent> events = prepareEvents(request, studentId);

            for (DailyCheckInEvent event : events) {
                ksession.insert(event);
            }

            int firedRules = ksession.fireAllRules(MAX_FIRED_RULES);

            if (firedRules >= MAX_FIRED_RULES) {
                throw new IllegalStateException(
                        "Drools je aktivirao " + MAX_FIRED_RULES +
                        " pravila. Moguca beskonacna petlja u pravilima."
                );
            }

            RiskAssessment assessment = findRiskAssessment(ksession, studentId);

            if (assessment == null) {
                assessment = createLowRiskAssessment(studentId);
            }

            boolean hasAcademicBurnoutPattern = hasAcademicBurnoutPattern(ksession, studentId);
            assessment.setHasAcademicBurnoutPattern(hasAcademicBurnoutPattern);

            return assessment;
        } finally {
            ksession.dispose();
        }
    }

    private List<DailyCheckInEvent> prepareEvents(AssessmentRequest request, String studentId) {
        List<DailyCheckInEvent> events = request.getDailyCheckIns() == null
                ? new ArrayList<>()
                : new ArrayList<>(request.getDailyCheckIns());

        events.sort(Comparator.comparing(DailyCheckInEvent::getDayIndex));

        for (DailyCheckInEvent event : events) {
            event.setStudentId(studentId);

            if (event.getTimestamp() == null) {
                event.setTimestamp(new Date());
            }
        }

        return events;
    }

    private RiskAssessment createLowRiskAssessment(String studentId) {
        RiskAssessment assessment = new RiskAssessment(studentId, RiskLevel.LOW_RISK);
        assessment.setDominantDimensions("nema izrazenog obrasca");
        assessment.setExplanation("Nije detektovan dovoljan broj indikatora i vremenskih obrazaca za povisen rizik.");
        return assessment;
    }

    private RiskAssessment findRiskAssessment(KieSession ksession, String studentId) {
        RiskAssessment best = null;

        for (Object object : ksession.getObjects()) {
            if (object instanceof RiskAssessment assessment
                    && studentId.equals(assessment.getStudentId())) {
                if (best == null || isHigherRisk(assessment, best)) {
                    best = assessment;
                }
            }
        }

        return best;
    }

    private boolean isHigherRisk(RiskAssessment candidate, RiskAssessment current) {
        return candidate.getRiskLevel().ordinal() > current.getRiskLevel().ordinal();
    }

    private boolean hasAcademicBurnoutPattern(KieSession ksession, String studentId) {
        QueryResults results = ksession.getQueryResults("hasAcademicBurnoutPattern", studentId);
        return results.size() > 0;
    }

}
