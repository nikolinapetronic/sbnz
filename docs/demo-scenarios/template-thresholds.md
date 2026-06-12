# Template threshold demo

Ovaj dokument objašnjava kako se testiraju template-generated pravila za pragove u StudyGuard sistemu.

Template-i se koriste da bi se pragovi za procjenu rizika prilagodili tipu studenta i akademskom periodu. Umjesto da se pragovi ručno kreiraju u servisnom sloju, servis ubacuje `StudentContext`, a template-generated pravila kreiraju odgovarajući `ThresholdProfile`.

Tok zaključivanja:

```text
AssessmentRequest
→ StudentContext
→ 10_profile_thresholds.drl
→ ThresholdProfile
→ score / indicator / CEP rules
→ RiskAssessment
```

## StudentContext

`StudentContext` sadrži osnovni kontekst za izbor pragova:

```text
studentId
profileType
academicPeriod
```

Primjer:

```text
profileType = DEFAULT
academicPeriod = EXAM_PERIOD
```

Na osnovu toga se aktivira odgovarajuće template-generated pravilo koje ubacuje `ThresholdProfile`.

## ThresholdProfile

`ThresholdProfile` sadrži pragove koje koriste ostala pravila:

```text
minSleepHours
highStressThreshold
lowEfficacyThreshold
maxMissedObligations
exhaustionIndicatorThreshold
cynicismIndicatorThreshold
```

Ovi pragovi ne predstavljaju konačnu procjenu rizika. Oni su operativne vrijednosti koje koriste score, indicator i CEP pravila.

## Scenario 1: EXAM_PERIOD

Fajl: `moderate-exam-risk.json`

U ovom scenariju student ima:

```text
academicPeriod = EXAM_PERIOD
efficacy = 5
```

Za `EXAM_PERIOD`, template-generated pravilo kreira `ThresholdProfile` sa:

```text
lowEfficacyThreshold = 5
```

Zato se aktivira:

```text
ReducedEfficacyIndicator
```

Pošto isti unos aktivira i `ExhaustionIndicator`, sistem formira:

```text
riskLevel = MODERATE_RISK
```

## Scenario 2: REGULAR_CLASSES

Za isti unos, ali sa:

```text
academicPeriod = REGULAR_CLASSES
```

template-generated pravilo kreira blaži prag:

```text
lowEfficacyThreshold = 4
```

Pošto je u testu:

```text
efficacy = 5
```

ne aktivira se `ReducedEfficacyIndicator`, pa rezultat ostaje:

```text
riskLevel = LOW_RISK
```

Ova razlika pokazuje da template pragovi stvarno utiču na rezonovanje.

## Scenario 3: WORKING_STUDENT

Kod profila `WORKING_STUDENT`, pragovi su prilagođeni studentu koji radi uz studije.

Primjer pragova:

```text
minSleepHours = 5
highStressThreshold = 8
maxMissedObligations = 3
```

To znači da isti unos koji kod `DEFAULT` profila može aktivirati exhaustion ne mora aktivirati exhaustion kod zaposlenog studenta.

Primjer:

```text
DEFAULT + sleepHours = 5.5 + stress = 7
→ može aktivirati ExhaustionIndicator

WORKING_STUDENT + sleepHours = 5.5 + stress = 7
→ ne mora aktivirati ExhaustionIndicator
```

## Zaključak

Template pravila omogućavaju da se pragovi mijenjaju kroz tabelarne podatke i `.drt` template, bez izmjene glavne domenske logike sistema.

Generisana DRL pravila su normalna Drools pravila. Template se koristi kao mehanizam za generisanje tih pravila iz tabelarnih podataka o pragovima.
