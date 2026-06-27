# StudyGuard 

StudyGuard je rule-based ekspertski sistem za procjenu rizika od akademskog burnout-a kod studenata.

Sistem koristi **Drools** za ekspertsko zaključivanje na osnovu dnevnih self-report unosa studenta, akademskog konteksta i vremenskih obrazaca. Cilj sistema nije postavljanje medicinske dijagnoze, već rana identifikacija rizičnih obrazaca i davanje objašnjivih preporuka.

---

## Osnovna ideja

Akademski burnout se ne posmatra kao jedan izolovan simptom, nego kao kombinacija više faktora:

* loš san,
* visok stres,
* umor i preopterećenost,
* pad motivacije,
* cinizam ili distanca prema studijama,
* smanjena akademska efikasnost,
* izbjegavanje obaveza,
* slab oporavak,
* negativan trend kroz više dana.

StudyGuard zato kombinuje klasično rule-based zaključivanje, CEP pravila za vremenske obrasce, template pravila za konfigurabilne pragove i backward chaining za provjeru da li postoji obrazac konzistentan sa akademskim burnout-om.

---

## Struktura projekta

Projekat je organizovan u pet glavnih cjelina:

```text
model       - domenski model, činjenice, događaji, indikatori, obrasci i izlazni objekti
kjar        - Drools pravila, query-ji i rule template fajlovi
service     - Spring Boot REST API i integracija sa Drools engine-om
frontend    - Angular klijentska aplikacija za unos i demonstraciju scenarija
docs        - projektna dokumentacija, dijagrami i demo testni podaci
```

---

## Tehnologije

Backend:

```text
Java 17
Spring Boot
Drools 7.49.0.Final
Maven
```

Frontend:

```text
Angular
TypeScript
npm
```

---

## Pokretanje backend-a

Backend se sastoji iz tri Maven projekta: `model`, `kjar` i `service`.

Prvo je potrebno instalirati `model`, zatim `kjar`, a nakon toga pokrenuti `service`.

### Windows PowerShell / Command Prompt

```bash
cd model
mvn clean install

cd ..\kjar
mvn clean install

cd ..\service
mvn spring-boot:run
```

### Git Bash / Linux / macOS

```bash
cd model
./mvnw clean install

cd ../kjar
./mvnw clean install

cd ../service
./mvnw spring-boot:run
```

Backend se pokreće na:

```text
http://localhost:8080
```

Glavni endpoint:

```text
POST /api/assessment
Content-Type: application/json
```

---

## Pokretanje frontend-a

Frontend se nalazi u folderu `frontend`.

```bash
cd frontend
npm install
npm start
```

Aplikacija se pokreće na:

```text
http://localhost:4200
```

Frontend koristi `proxy.conf.json`, tako da se pozivi ka `/api` prosljeđuju na backend:

```text
http://localhost:8080
```

---

## Ulaz u sistem

Glavni ulazni objekat je `AssessmentRequest`.

Sadrži:

```text
studentId
profileType
academicPeriod
dailyCheckIns
```

`dailyCheckIns` je lista dnevnih unosa tipa `DailyCheckInEvent`.

Jedan dnevni unos sadrži, između ostalog:

```text
sleepHours
sleepQuality
fatigue
stress
overload
motivation
studyMeaning
concentration
efficacy
missedObligation
lowRecovery
supportScore
copingStrategy
timestamp
dayIndex
```

---

## Izlaz iz sistema

Backend vraća `RiskAssessment`.

Izlaz sadrži:

```text
riskLevel
dominantDimensions
activatedEvidence
recommendations
explanation
hasAcademicBurnoutPattern
```

Mogući nivoi rizika:

```text
LOW_RISK
MODERATE_RISK
HIGH_RISK
SEVERE_RISK
```

---

## Implementirani koncepti

### 1. Forward chaining

Forward chaining je osnovni tok zaključivanja u sistemu.

Lanac zaključivanja je organizovan kroz više nivoa:

```text
DailyCheckInEvent
→ ScoreAdjustment
→ ExhaustionScore / CynicismScore / EfficacyScore
→ ExhaustionIndicator / CynicismIndicator / ReducedEfficacyIndicator
→ ModerateBurnoutRisk / HighBurnoutRisk / SevereRiskPattern
→ RiskAssessment
```

Ovim se pokazuje da sistem ne donosi finalni zaključak direktno iz jednog ulaza, nego kroz lanac izvedenih činjenica.

---

### 2. Accumulate funkcija

`accumulate` se koristi za sabiranje i agregaciju više činjenica.

Primjeri upotrebe:

* formiranje ukupnog `ExhaustionScore`,
* formiranje ukupnog `CynicismScore`,
* formiranje ukupnog `EfficacyScore`,
* brojanje događaja u CEP pravilima,
* računanje prosječnog stresa u vremenskom prozoru.

---

### 3. CEP

CEP se koristi za obrasce koji se razvijaju kroz vrijeme.

Događaji su deklarisani kao event činjenice u fajlu:

```text
00_declarations.drl
```

Korišćeni događaji:

```text
DailyCheckInEvent
ScoreSnapshotEvent
```

`kmodule.xml` koristi stream mode:

```xml
<kbase name="studyguardKBase" packages="rules" eventProcessingMode="stream">
    <ksession name="studyguardSession" type="stateful"/>
</kbase>
```

Primjeri CEP obrazaca:

```text
SustainedPoorSleepPattern
SustainedStressPattern
LowRecoveryPattern
AvoidancePattern
RepeatedLowMotivationPattern
AcademicGoalDeviationPattern
NegativeTrend
```

CEP pravila koriste vremenske prozore, npr:

```text
window:time(7d)
window:time(10d)
```

Za live demonstraciju na odbrani frontend prepared examples dinamički računaju timestamp vrijednosti, kako bi događaji uvijek upadali u odgovarajuće CEP vremenske prozore.

---

### 4. Rule templates

Template pravila se koriste za generisanje pragova po tipu studenta i akademskom periodu.

Template fajl:

```text
kjar/src/main/resources/templates/profile-thresholds.drt
```

Generator:

```text
kjar/src/test/java/com/sbnz/kjar/templates/ThresholdTemplateGenerator.java
```

Generisani DRL fajl:

```text
kjar/src/main/resources/rules/10_profile_thresholds.drl
```

Template generiše pravila koja kreiraju `ThresholdProfile`.

Na primjer, za isti dnevni unos sistem može dati različit rezultat u zavisnosti od akademskog perioda:

```text
REGULAR_CLASSES → LOW_RISK
EXAM_PERIOD     → MODERATE_RISK
```

Time se demonstrira da template-generated pragovi stvarno utiču na dalji tok zaključivanja.

---

### 5. Backward chaining

Backward chaining se koristi za provjeru cilja:

```text
hasAcademicBurnoutPattern(studentId)
```

Implementiran je preko evidence graph pristupa:

```text
EvidenceNode
EvidenceRelation
```

Konkretne izvedene činjenice, kao što su indikatori i CEP obrasci, pretvaraju se u `EvidenceNode`. Zatim se preko `EvidenceRelation` modeluje hijerarhija dokaza.

Glavni query:

```text
hasAcademicBurnoutPattern(studentId)
```

Rekurzivni query:

```text
reachesEvidence(...)
```

Servisni sloj poziva query preko:

```text
ksession.getQueryResults("hasAcademicBurnoutPattern", studentId)
```

Rezultat se upisuje u finalni `RiskAssessment` kroz polje:

```text
hasAcademicBurnoutPattern
```

---

## Demo scenariji

Demo scenariji se nalaze u:

```text
docs/demo-scenarios/
```

Trenutni scenariji:

```text
low-risk.json
moderate-exam-risk.json
negative-trend.json
severe-burnout-pattern.json
template-regular-classes.json
template-exam-period.json
```

Isti tipovi scenarija postoje i u frontend aplikaciji kao prepared examples.

Glavni scenario za kompletnu demonstraciju je:

```text
severe-burnout-pattern.json
```

On demonstrira:

```text
forward chaining
CEP obrasce
risk escalation
dominant dimensions
recursive backward chaining
final RiskAssessment
```

---

## Demonstracija template-a

Za template demonstraciju koriste se dva skoro ista scenarija:

```text
template-regular-classes.json
template-exam-period.json
```

Razlika je samo u akademskom periodu:

```text
REGULAR_CLASSES
EXAM_PERIOD
```

U oba slučaja dnevne vrijednosti su iste, ali `EXAM_PERIOD` koristi stroži prag za nisku akademsku efikasnost. Zbog toga isti unos u ispitnom roku aktivira `ReducedEfficacyIndicator`, dok u redovnom periodu ne aktivira taj indikator.

Ovo pokazuje da `profile-thresholds.drt` i generisani `10_profile_thresholds.drl` nisu samo dokumentacioni dio projekta, nego direktno utiču na rezultat zaključivanja.

---

## Demonstracija CEP-a

CEP se najjasnije demonstrira kroz scenario:

```text
Severe burnout pattern
```

Ovaj scenario sadrži više dnevnih događaja i aktivira vremenske obrasce:

```text
SustainedPoorSleepPattern
SustainedStressPattern
LowRecoveryPattern
AvoidancePattern
RepeatedLowMotivationPattern
AcademicGoalDeviationPattern
```

Za detekciju trenda koristi se scenario:

```text
Negative trend
```

On pokazuje da sistem formira `ScoreSnapshotEvent` i detektuje pogoršanje kroz više uzastopnih dana.

---

## Napomena o admin/savjetnik ulozi

U proposal-u je opisana i savjetnik/administrator uloga.

U ovoj implementaciji ne postoji poseban admin panel. Ta uloga je predstavljena kroz održavanje baze znanja i konfiguracionih pragova, prvenstveno kroz rule template podatke i generisanje `10_profile_thresholds.drl`.

Drugim riječima, pragovi se ne mijenjaju kroz UI u runtime-u, nego kroz template podatke i regenerisanje pravila.
---
