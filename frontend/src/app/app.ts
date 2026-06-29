import { ChangeDetectorRef, Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { JsonPipe, NgClass, NgFor, NgIf } from '@angular/common';

import { AssessmentService } from './assessment.service';
import { AssessmentRequest, DailyCheckIn, RiskAssessment, scenarios } from './scenarios';

import { LucideAngularModule } from 'lucide-angular';
import {
  UserIcon,
  GraduationCapIcon,
  BookOpenIcon,
  CalendarIcon,
  MoonIcon,
  FlameIcon,
  BoxIcon,
  RocketIcon,
  LightbulbIcon,
  TargetIcon,
  TrendingUpIcon,
  HandshakeIcon,
  CompassIcon,
  ClipboardCheckIcon,
  BatteryLowIcon,
  FlaskConicalIcon,
  FileJsonIcon,
  ZapIcon,
  PlusIcon,
  Trash2Icon,
  RefreshCwIcon,
  PlayIcon,
  SparklesIcon,
  BrainIcon
} from 'lucide-angular';

type ViewMode = 'manual' | 'demo';

interface ManualDailyCheckIn {
  date: string;
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

interface ManualAssessmentForm {
  studentId: string;
  profileType: string;
  academicPeriod: string;
  dailyCheckIns: ManualDailyCheckIn[];
}

@Component({
  selector: 'app-root',
  imports: [FormsModule, NgIf, NgFor, NgClass, JsonPipe, LucideAngularModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {

  readonly UserIcon = UserIcon;
  readonly GraduationCapIcon = GraduationCapIcon;
  readonly BookOpenIcon = BookOpenIcon;
  readonly CalendarIcon = CalendarIcon;
  readonly MoonIcon = MoonIcon;
  readonly FlameIcon = FlameIcon;
  readonly BoxIcon = BoxIcon;
  readonly RocketIcon = RocketIcon;
  readonly LightbulbIcon = LightbulbIcon;
  readonly TargetIcon = TargetIcon;
  readonly TrendingUpIcon = TrendingUpIcon;
  readonly HandshakeIcon = HandshakeIcon;
  readonly CompassIcon = CompassIcon;
  readonly ClipboardCheckIcon = ClipboardCheckIcon;
  readonly BatteryLowIcon = BatteryLowIcon;
  readonly FlaskConicalIcon = FlaskConicalIcon;
  readonly FileJsonIcon = FileJsonIcon;
  readonly ZapIcon = ZapIcon;
  readonly PlusIcon = PlusIcon;
  readonly Trash2Icon = Trash2Icon;
  readonly RefreshCwIcon = RefreshCwIcon;
  readonly PlayIcon = PlayIcon;
  readonly SparklesIcon = SparklesIcon;
  readonly BrainIcon = BrainIcon;

  scenarioKeys = Object.keys(scenarios);
  scenarios = scenarios;

  selectedView: ViewMode = 'manual';

  selectedScenario = 'severeBurnoutPattern';
  requestBody = this.formatJson(scenarios[this.selectedScenario].body);

  selectedManualScenario = '';

  assessment: RiskAssessment | null = null;
  submittedRequest: AssessmentRequest | null = null;
  errorMessage = '';
  loading = false;

    private readonly riskLevelLabels: Record<string, string> = {
    LOW_RISK: 'Low risk',
    MODERATE_RISK: 'Moderate risk',
    HIGH_RISK: 'High risk',
    SEVERE_RISK: 'Severe risk'
  };

  private readonly recommendationLabels: Record<string, string> = {
    MONITOR_NEXT_7_DAYS: 'Monitor for the next 7 days',
    REST_AND_RECOVERY: 'Rest and recovery',
    STUDY_PLAN_RESTRUCTURING: 'Study plan restructuring',
    SEEK_ACADEMIC_SUPPORT: 'Seek academic support',
    SEEK_COUNSELING_SUPPORT: 'Seek counseling support'
  };

  private readonly evidenceLabels: Record<string, string> = {
    ExhaustionIndicator: 'Academic exhaustion indicator',
    CynicismIndicator: 'Cynicism/detachment indicator',
    ReducedEfficacyIndicator: 'Reduced academic efficacy indicator',

    ModerateRiskPattern: 'Moderate risk pattern',
    ModerateRiskWithProtectiveFactorsPattern: 'Moderate risk with protective factors',
    HighRiskPattern: 'High risk pattern',
    HighRiskWithSupportResourcesPattern: 'High risk with support resources',
    SevereRiskPattern: 'Severe risk pattern',

    SustainedStressPattern: 'Sustained stress pattern',
    SustainedPoorSleepPattern: 'Sustained poor sleep pattern',
    LowRecoveryPattern: 'Low recovery pattern',
    AvoidancePattern: 'Avoidance pattern',
    RepeatedLowMotivationPattern: 'Repeated low motivation pattern',
    AcademicGoalDeviationPattern: 'Academic goal deviation pattern',
    BurnoutDevelopmentPattern: 'Burnout development pattern',
    NegativeTrendPattern: 'Negative trend pattern',

    ProtectiveSupportFactor: 'Protective support factor',
    MaladaptiveCopingFactor: 'Maladaptive coping factor',
    LowSupportFactor: 'Low support factor'
  };

  profileTypes = [
    { value: 'DEFAULT', label: 'Default student' },
    { value: 'FRESHMAN', label: 'Freshman' },
    { value: 'WORKING_STUDENT', label: 'Working student' },
    { value: 'FINAL_YEAR', label: 'Final year student' }
  ];

  academicPeriods = [
    { value: 'REGULAR_CLASSES', label: 'Regular classes' },
    { value: 'MIDTERM_WEEK', label: 'Midterm week' },
    { value: 'EXAM_PERIOD', label: 'Exam period' },
    { value: 'AFTER_EXAM_PERIOD', label: 'After exam period' }
  ];

  sleepQualities = [
    { value: 'GOOD', label: 'Good' },
    { value: 'MEDIUM', label: 'Medium' },
    { value: 'POOR', label: 'Poor' }
  ];

  copingStrategies = [
    { value: 'PLANNING', label: 'Planning' },
    { value: 'ACTIVE_PROBLEM_SOLVING', label: 'Active problem solving' },
    { value: 'SEEKING_SUPPORT', label: 'Seeking support' },
    { value: 'AVOIDANCE', label: 'Avoidance' },
    { value: 'DENIAL', label: 'Denial' },
    { value: 'PROCRASTINATION', label: 'Procrastination' }
  ];

  manualForm: ManualAssessmentForm = {
    studentId: 'studentManual',
    profileType: 'DEFAULT',
    academicPeriod: 'REGULAR_CLASSES',
    dailyCheckIns: [this.createDefaultManualDay()]
  };

  constructor(
    private readonly assessmentService: AssessmentService,
    private readonly changeDetectorRef: ChangeDetectorRef
  ) {}

  selectView(view: ViewMode): void {
    this.selectedView = view;
    this.assessment = null;
    this.submittedRequest = null;
    this.errorMessage = '';
  }

  onScenarioChange(): void {
    this.requestBody = this.formatJson(scenarios[this.selectedScenario].body);
    this.assessment = null;
    this.submittedRequest = null;
    this.errorMessage = '';
  }

loadSelectedManualScenario(): void {
  if (!this.selectedManualScenario) {
    return;
  }

  const scenario = scenarios[this.selectedManualScenario as keyof typeof scenarios];

  this.manualForm = this.toManualForm(scenario.body);
  this.requestBody = this.formatJson(scenario.body);

  this.assessment = null;
  this.submittedRequest = null;
  this.errorMessage = '';
}

  runDemoAssessment(): void {
    let parsedBody: AssessmentRequest;

    try {
      parsedBody = JSON.parse(this.requestBody);
    } catch {
      this.errorMessage = 'Request body is not valid JSON.';
      this.loading = false;
      this.changeDetectorRef.detectChanges();
      return;
    }

    this.submitAssessment(parsedBody);
  }

  runManualAssessment(): void {
    const request = this.buildManualRequest();
    this.requestBody = this.formatJson(request);
    this.submitAssessment(request);
  }

  addManualDay(): void {
    const lastDate = this.manualForm.dailyCheckIns.length
      ? this.manualForm.dailyCheckIns[this.manualForm.dailyCheckIns.length - 1].date
      : this.getTodayDate();

    this.manualForm.dailyCheckIns.push(this.createDefaultManualDay(this.getNextDate(lastDate)));
    this.assessment = null;
    this.submittedRequest = null;
    this.errorMessage = '';
  }

  removeManualDay(index: number): void {
    if (this.manualForm.dailyCheckIns.length === 1) {
      return;
    }

    this.manualForm.dailyCheckIns.splice(index, 1);
    this.assessment = null;
    this.submittedRequest = null;
    this.errorMessage = '';
  }

  resetManualForm(): void {
    this.manualForm = {
      studentId: 'studentManual',
      profileType: 'DEFAULT',
      academicPeriod: 'REGULAR_CLASSES',
      dailyCheckIns: [this.createDefaultManualDay()]
    };

    this.assessment = null;
    this.submittedRequest = null;
    this.errorMessage = '';
  }

  getRiskClass(riskLevel: string | undefined): string {
    if (!riskLevel) {
      return '';
    }

    return riskLevel.toLowerCase().replaceAll('_', '-');
  }

    formatRiskLevel(riskLevel: string | undefined): string {
    if (!riskLevel) {
      return '';
    }

    return this.riskLevelLabels[riskLevel] ?? this.formatEnumLabel(riskLevel);
  }

  formatRecommendation(recommendation: string): string {
    return this.recommendationLabels[recommendation] ?? this.formatEnumLabel(recommendation);
  }

  formatEvidence(evidence: string): string {
    return this.evidenceLabels[evidence] ?? this.formatCamelOrEnumLabel(evidence);
  }

  private formatEnumLabel(value: string): string {
    return value
      .toLowerCase()
      .split('_')
      .map((word) => this.capitalize(word))
      .join(' ');
  }

  private formatCamelOrEnumLabel(value: string): string {
    if (value.includes('_')) {
      return this.formatEnumLabel(value);
    }

    return value
      .replace(/([a-z])([A-Z])/g, '$1 $2')
      .replace(/Pattern$/, ' pattern')
      .replace(/Indicator$/, ' indicator')
      .replace(/Factor$/, ' factor')
      .split(' ')
      .filter(Boolean)
      .map((word) => this.capitalize(word))
      .join(' ');
  }

  private capitalize(value: string): string {
    return value.charAt(0).toUpperCase() + value.slice(1);
  }

  formatJson(value: unknown): string {
    return JSON.stringify(value, null, 2);
  }

  private submitAssessment(request: AssessmentRequest): void {
    this.loading = true;
    this.assessment = null;
    this.submittedRequest = request;
    this.errorMessage = '';

    this.assessmentService.assess(request).subscribe({
      next: (result) => {
        this.assessment = result;
        this.loading = false;
        this.changeDetectorRef.detectChanges();
      },
      error: (error) => {
        this.errorMessage =
          error?.error?.message ||
          error?.error ||
          error?.message ||
          'Assessment request failed.';
        this.loading = false;
        this.changeDetectorRef.detectChanges();
      }
    });
  }

  private toManualForm(request: AssessmentRequest): ManualAssessmentForm {
  return {
    studentId: request.studentId,
    profileType: request.profileType,
    academicPeriod: request.academicPeriod,
    dailyCheckIns: request.dailyCheckIns.map((day) => ({
      date: this.toInputDate(day.timestamp),
      sleepHours: day.sleepHours,
      sleepQuality: day.sleepQuality,
      fatigue: day.fatigue,
      stress: day.stress,
      overload: day.overload,
      motivation: day.motivation,
      studyMeaning: day.studyMeaning,
      concentration: day.concentration,
      efficacy: day.efficacy,
      missedObligation: day.missedObligation,
      lowRecovery: day.lowRecovery,
      supportScore: day.supportScore,
      copingStrategy: day.copingStrategy
    }))
  };
}

private toInputDate(timestamp: string): string {
  return new Date(timestamp).toISOString().slice(0, 10);
}

  private buildManualRequest(): AssessmentRequest {
    const dailyCheckIns: DailyCheckIn[] = this.manualForm.dailyCheckIns.map((day, index) => ({
      dayIndex: index + 1,
      timestamp: this.toBackendTimestamp(day.date),
      sleepHours: Number(day.sleepHours),
      sleepQuality: day.sleepQuality,
      fatigue: Number(day.fatigue),
      stress: Number(day.stress),
      overload: Number(day.overload),
      motivation: Number(day.motivation),
      studyMeaning: Number(day.studyMeaning),
      concentration: Number(day.concentration),
      efficacy: Number(day.efficacy),
      missedObligation: Boolean(day.missedObligation),
      lowRecovery: Boolean(day.lowRecovery),
      supportScore: Number(day.supportScore),
      copingStrategy: day.copingStrategy
    }));

    return {
      studentId: this.manualForm.studentId.trim() || 'studentManual',
      profileType: this.manualForm.profileType,
      academicPeriod: this.manualForm.academicPeriod,
      dailyCheckIns
    };
  }

  private createDefaultManualDay(date = this.getTodayDate()): ManualDailyCheckIn {
    return {
      date,
      sleepHours: 7,
      sleepQuality: 'GOOD',
      fatigue: 4,
      stress: 4,
      overload: 4,
      motivation: 7,
      studyMeaning: 7,
      concentration: 7,
      efficacy: 7,
      missedObligation: false,
      lowRecovery: false,
      supportScore: 7,
      copingStrategy: 'PLANNING'
    };
  }

  private getTodayDate(): string {
    return new Date().toISOString().slice(0, 10);
  }

  private getNextDate(date: string): string {
    const next = new Date(`${date}T00:00:00.000Z`);
    next.setUTCDate(next.getUTCDate() + 1);
    return next.toISOString().slice(0, 10);
  }

  private toBackendTimestamp(date: string): string {
    return new Date(`${date}T10:00:00.000Z`).toISOString();
  }
}