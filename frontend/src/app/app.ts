import { ChangeDetectorRef, Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AssessmentService } from './assessment.service';
import { RiskAssessment, scenarios } from './scenarios';
import { JsonPipe, NgClass, NgFor, NgIf } from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [FormsModule, NgIf, NgFor, NgClass, JsonPipe],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  scenarioKeys = Object.keys(scenarios);
  scenarios = scenarios;

  selectedScenario = 'severeBurnoutPattern';
  requestBody = this.formatJson(scenarios[this.selectedScenario].body);

  assessment: RiskAssessment | null = null;
  errorMessage = '';
  loading = false;

  constructor(
    private readonly assessmentService: AssessmentService,
    private readonly changeDetectorRef: ChangeDetectorRef
  ) {}

  onScenarioChange(): void {
    this.requestBody = this.formatJson(scenarios[this.selectedScenario].body);
    this.assessment = null;
    this.errorMessage = '';
  }

  runAssessment(): void {
    this.loading = true;
    this.assessment = null;
    this.errorMessage = '';

    let parsedBody;

    try {
      parsedBody = JSON.parse(this.requestBody);
    } catch {
      this.loading = false;
      this.errorMessage = 'Request body is not valid JSON.';
      this.changeDetectorRef.detectChanges();
      return;
}

    this.assessmentService.assess(parsedBody).subscribe({
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

  getRiskClass(riskLevel: string | undefined): string {
    if (!riskLevel) {
      return '';
    }

    return riskLevel.toLowerCase().replaceAll('_', '-');
  }

  formatJson(value: unknown): string {
    return JSON.stringify(value, null, 2);
  }
}
