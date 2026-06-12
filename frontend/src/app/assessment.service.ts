import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AssessmentRequest, RiskAssessment } from './scenarios';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AssessmentService {
  private readonly assessmentUrl = '/api/assessment';

  constructor(private readonly http: HttpClient) {}

  assess(request: AssessmentRequest): Observable<RiskAssessment> {
    return this.http.post<RiskAssessment>(this.assessmentUrl, request);
  }
}
