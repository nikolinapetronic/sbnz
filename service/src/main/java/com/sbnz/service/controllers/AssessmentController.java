package com.sbnz.service.controllers;

import com.sbnz.model.risks.RiskAssessment;
import com.sbnz.service.dto.AssessmentRequest;
import com.sbnz.service.services.AssessmentService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assessment")
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @PostMapping(
        consumes = "application/json",
        produces = "application/json;charset=UTF-8"
    )
    public RiskAssessment assess(@RequestBody AssessmentRequest request) {
        return assessmentService.assess(request);
    }
}
