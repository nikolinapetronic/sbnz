export interface DemoScenario {
  label: string;
  description: string;
  body: AssessmentRequest;
}

export interface AssessmentRequest {
  studentId: string;
  profileType: string;
  academicPeriod: string;
  dailyCheckIns: DailyCheckIn[];
}

export interface DailyCheckIn {
  dayIndex: number;
  timestamp: string;
  sleepHours: number;
  sleepQuality: string;
  fatigue: number;
  stress: number;
  overload: number;
  motivation: number;
  studyMeaning: number;
  concentration: number;
  efficacy: number;
  missedObligation: boolean;
  lowRecovery: boolean;
  supportScore: number;
  copingStrategy: string;
}

export interface RiskAssessment {
  studentId: string;
  riskLevel: string;
  dominantDimensions: string;
  activatedEvidence: string[];
  recommendations: string[];
  explanation: string;
  hasAcademicBurnoutPattern: boolean;
}

export const scenarios: Record<string, DemoScenario> = {
  lowRisk: {
    label: 'Low risk',
    description: 'Basic case without activated risk indicators.',
    body: {
      studentId: 'studentLow',
      profileType: 'DEFAULT',
      academicPeriod: 'REGULAR_CLASSES',
      dailyCheckIns: [
        {
          dayIndex: 1,
          timestamp: '2026-06-12T10:00:00.000+00:00',
          sleepHours: 8,
          sleepQuality: 'GOOD',
          fatigue: 2,
          stress: 3,
          overload: 3,
          motivation: 8,
          studyMeaning: 8,
          concentration: 8,
          efficacy: 8,
          missedObligation: false,
          lowRecovery: false,
          supportScore: 8,
          copingStrategy: 'PLANNING'
        }
      ]
    }
  },

  moderateExamRisk: {
    label: 'Moderate exam risk',
    description: 'Template threshold demo: EXAM_PERIOD makes efficacy = 5 risky.',
    body: {
      studentId: 'templateExam',
      profileType: 'DEFAULT',
      academicPeriod: 'EXAM_PERIOD',
      dailyCheckIns: [
        {
          dayIndex: 1,
          timestamp: '2026-06-12T10:00:00.000+00:00',
          sleepHours: 5,
          sleepQuality: 'POOR',
          fatigue: 8,
          stress: 8,
          overload: 8,
          motivation: 7,
          studyMeaning: 7,
          concentration: 4,
          efficacy: 5,
          missedObligation: false,
          lowRecovery: false,
          supportScore: 8,
          copingStrategy: 'PLANNING'
        }
      ]
    }
  },

  negativeTrend: {
    label: 'Negative trend',
    description: 'ScoreSnapshotEvent demo: efficacy decreases over three consecutive days.',
    body: {
      studentId: 'negativeTrendEfficacy',
      profileType: 'DEFAULT',
      academicPeriod: 'EXAM_PERIOD',
      dailyCheckIns: [
        {
          dayIndex: 1,
          timestamp: '2026-06-10T10:00:00.000+00:00',
          sleepHours: 5,
          sleepQuality: 'POOR',
          fatigue: 5,
          stress: 8,
          overload: 7,
          motivation: 7,
          studyMeaning: 7,
          concentration: 5,
          efficacy: 8,
          missedObligation: false,
          lowRecovery: false,
          supportScore: 6,
          copingStrategy: 'PLANNING'
        },
        {
          dayIndex: 2,
          timestamp: '2026-06-11T10:00:00.000+00:00',
          sleepHours: 5,
          sleepQuality: 'POOR',
          fatigue: 5,
          stress: 8,
          overload: 7,
          motivation: 7,
          studyMeaning: 7,
          concentration: 5,
          efficacy: 6,
          missedObligation: false,
          lowRecovery: false,
          supportScore: 6,
          copingStrategy: 'PLANNING'
        },
        {
          dayIndex: 3,
          timestamp: '2026-06-12T10:00:00.000+00:00',
          sleepHours: 5,
          sleepQuality: 'POOR',
          fatigue: 5,
          stress: 8,
          overload: 7,
          motivation: 7,
          studyMeaning: 7,
          concentration: 5,
          efficacy: 4,
          missedObligation: false,
          lowRecovery: false,
          supportScore: 6,
          copingStrategy: 'PLANNING'
        }
      ]
    }
  },

  severeBurnoutPattern: {
    label: 'Severe burnout pattern',
    description: 'Full demo: forward chaining, CEP patterns, recursive backward chaining.',
    body: {
      studentId: 'studentCep',
      profileType: 'DEFAULT',
      academicPeriod: 'EXAM_PERIOD',
      dailyCheckIns: [
        {
          dayIndex: 1,
          timestamp: '2026-06-09T10:00:00.000+00:00',
          sleepHours: 5,
          sleepQuality: 'POOR',
          fatigue: 8,
          stress: 8,
          overload: 8,
          motivation: 3,
          studyMeaning: 3,
          concentration: 3,
          efficacy: 5,
          missedObligation: false,
          lowRecovery: true,
          supportScore: 6,
          copingStrategy: 'AVOIDANCE'
        },
        {
          dayIndex: 2,
          timestamp: '2026-06-10T10:00:00.000+00:00',
          sleepHours: 5,
          sleepQuality: 'POOR',
          fatigue: 8,
          stress: 8,
          overload: 8,
          motivation: 3,
          studyMeaning: 3,
          concentration: 3,
          efficacy: 4,
          missedObligation: true,
          lowRecovery: true,
          supportScore: 6,
          copingStrategy: 'AVOIDANCE'
        },
        {
          dayIndex: 3,
          timestamp: '2026-06-11T10:00:00.000+00:00',
          sleepHours: 5,
          sleepQuality: 'POOR',
          fatigue: 9,
          stress: 9,
          overload: 9,
          motivation: 2,
          studyMeaning: 2,
          concentration: 2,
          efficacy: 3,
          missedObligation: false,
          lowRecovery: true,
          supportScore: 5,
          copingStrategy: 'PROCRASTINATION'
        },
        {
          dayIndex: 4,
          timestamp: '2026-06-12T10:00:00.000+00:00',
          sleepHours: 5,
          sleepQuality: 'POOR',
          fatigue: 9,
          stress: 9,
          overload: 9,
          motivation: 2,
          studyMeaning: 2,
          concentration: 2,
          efficacy: 2,
          missedObligation: true,
          lowRecovery: false,
          supportScore: 5,
          copingStrategy: 'PROCRASTINATION'
        }
      ]
    }
  }
};
