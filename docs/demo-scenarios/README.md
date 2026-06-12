# StudyGuard demo scenarios

Ovaj folder sadrži test scenarije za demonstraciju glavnih dijelova StudyGuard ekspertskog sistema.

Endpoint:

```text
POST /api/assessment
Content-Type: application/json
```

## Scenarios

### 1. `low-risk.json`

Demonstrira osnovni slučaj bez aktiviranih indikatora.

Expected:

```text
riskLevel = LOW_RISK
activatedEvidence = []
hasAcademicBurnoutPattern = false
```

Ovaj scenario pokazuje da sistem ne dodjeljuje povišen rizik kada dnevni unos ne sadrži rizične vrijednosti.

---

### 2. `moderate-exam-risk.json`

Demonstrira template pragove za ispitni rok.

U ovom scenariju student ima `efficacy = 5`. Za `EXAM_PERIOD`, template-generated pravilo kreira `ThresholdProfile` sa strožim pragom za nisku akademsku efikasnost.

Expected:

```text
riskLevel = MODERATE_RISK
activatedEvidence contains ExhaustionIndicator and ReducedEfficacyIndicator
hasAcademicBurnoutPattern = false
```

Ovaj scenario pokazuje da akademski period utiče na pragove koji se koriste u pravilima.

---

### 3. `severe-burnout-pattern.json`

Glavni demonstracioni scenario.

Demonstrira:

```text
forward chaining
CEP patterns
recursive backward chaining
final RiskAssessment
```

Expected:

```text
riskLevel = SEVERE_RISK
activatedEvidence contains:
- ExhaustionIndicator
- CynicismIndicator
- ReducedEfficacyIndicator
- SustainedPoorSleepPattern
- SustainedStressPattern
- LowRecoveryPattern
- AvoidancePattern
- RepeatedLowMotivationPattern
- AcademicGoalDeviationPattern
- BurnoutDevelopmentPattern
- SevereRiskPattern

hasAcademicBurnoutPattern = true
```

Ovaj scenario pokazuje kompletan tok zaključivanja: dnevni unosi se pretvaraju u skorove, skorovi u indikatore, CEP pravila detektuju vremenske obrasce, forward chaining formira severe rizik, a backward chaining potvrđuje da postoji obrazac konzistentan sa akademskim burnout-om.

---

### 4. `negative-trend.json`

Demonstrira detekciju negativnog trenda preko `ScoreSnapshotEvent`.

Expected:

```text
riskLevel = HIGH_RISK
activatedEvidence contains NegativeTrend
hasAcademicBurnoutPattern = false
```

Ovaj scenario pokazuje da se dnevni skorovi čuvaju kao `ScoreSnapshotEvent` i da sistem može detektovati pogoršanje kroz tri uzastopna unosa.

## Notes

Datumi u scenarijima su postavljeni tako da budu u istom vremenskom prozoru za CEP pravila.

Ako se testovi pokreću mnogo kasnije, timestamp vrijednosti treba ažurirati na trenutne datume, jer CEP pravila koriste time-based sliding windows, npr. `window:time(7d)` i `window:time(10d)`.
