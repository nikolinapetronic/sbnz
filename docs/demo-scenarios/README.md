# StudyGuard demo scenarios

Ovaj folder sadrži test scenarije za demonstraciju glavnih dijelova StudyGuard ekspertskog sistema.

Scenariji su pripremljeni kao JSON ulazi za backend endpoint:

```text
POST /api/assessment
Content-Type: application/json
```

Isti tipovi scenarija postoje i u frontend aplikaciji kao prepared examples. U frontend-u se timestamp vrijednosti računaju dinamički, dok su JSON fajlovi u ovom folderu statički test primjeri i dokumentacija ulaznih podataka.

---

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

U ovom scenariju student ima `efficacy = 5`. Za `EXAM_PERIOD`, template-generated pravilo kreira `ThresholdProfile` sa strožim pragom za nisku akademsku efikasnost, pa se isti unos tretira kao rizičniji nego u redovnom akademskom periodu.

Expected:

```text
riskLevel = MODERATE_RISK
activatedEvidence contains:
- ExhaustionIndicator
- ReducedEfficacyIndicator

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
activatedEvidence contains:
- NegativeTrend

hasAcademicBurnoutPattern = false
```

Ovaj scenario pokazuje da se dnevni skorovi čuvaju kao `ScoreSnapshotEvent` i da sistem može detektovati pogoršanje kroz tri uzastopna unosa.

---

### 5. `template-regular-classes.json`

Prvi dio template comparison demonstracije.

Ovaj scenario ima iste dnevne vrijednosti kao `template-exam-period.json`, ali koristi:

```text
profileType = DEFAULT
academicPeriod = REGULAR_CLASSES
efficacy = 5
```

Za `REGULAR_CLASSES`, template-generated pravilo kreira `ThresholdProfile` kod kojeg je prag za nisku akademsku efikasnost niži nego u ispitnom roku. Zbog toga `efficacy = 5` ne aktivira `ReducedEfficacyIndicator`.

Expected:

```text
riskLevel = LOW_RISK
activatedEvidence = []
hasAcademicBurnoutPattern = false
```

Ovaj scenario služi kao kontrolni primjer za demonstraciju template pragova.

---

### 6. `template-exam-period.json`

Drugi dio template comparison demonstracije.

Ovaj scenario ima iste dnevne vrijednosti kao `template-regular-classes.json`, ali koristi:

```text
profileType = DEFAULT
academicPeriod = EXAM_PERIOD
efficacy = 5
```

Za `EXAM_PERIOD`, template-generated pravilo kreira `ThresholdProfile` sa strožim pragom za nisku akademsku efikasnost. Zbog toga isti unos aktivira `ReducedEfficacyIndicator`.

Expected:

```text
riskLevel = MODERATE_RISK
activatedEvidence contains:
- ModerateRiskWithProtectiveFactorsPattern
- ExhaustionIndicator
- ReducedEfficacyIndicator
- ProtectiveSupportFactor

hasAcademicBurnoutPattern = false
```

Ovaj scenario pokazuje da template-generated pragovi direktno utiču na dalji tok zaključivanja: isti dnevni unos može dati različit nivo rizika kada se promijeni akademski period.

---

## Template comparison demo

Za demonstraciju template-a koriste se dva scenarija:

```text
template-regular-classes.json
template-exam-period.json
```

Oba scenarija imaju iste dnevne vrijednosti. Razlika je samo u akademskom periodu:

```text
REGULAR_CLASSES
EXAM_PERIOD
```

U ispitnom roku sistem koristi stroži prag za nisku akademsku efikasnost, pa se `efficacy = 5` smatra rizičnom vrijednošću. U redovnom periodu isti unos ne prelazi prag za `ReducedEfficacyIndicator`.

Ovo pokazuje da template pravila nisu samo dokumentacioni dio projekta, nego se generisani `ThresholdProfile` stvarno koristi u scoring, indicator i CEP pravilima.

---

## Napomena o datumima za CEP demo

CEP pravila koriste vremenske prozore, npr. posljednjih 7 ili 10 dana. Zbog toga JSON fajlovi u ovom folderu predstavljaju statičke primjere ulaznih podataka i mogu zahtijevati ažuriranje `timestamp` vrijednosti prije ručnog slanja preko Postman-a ili curl-a.

Za live demonstraciju na odbrani koriste se frontend prepared examples, gdje se `timestamp` vrijednosti računaju dinamički u odnosu na trenutni datum. Time se osigurava da događaji uvijek upadaju u CEP vremenske prozore.

---
