# Healthcare Everyday

**Small habits carry the day** — a pill at the right hour, water before noon, a short walk, a stretch before bed. When those habits pile up in your head, they start to feel heavy. When someone you care about is juggling them alone, the worry piles up too.

**Healthcare Everyday** is a calm, offline-first desktop app for that ordinary heroism: one screen where a **senior** can see *today’s* plan, tick off what’s done, and jot how they’re feeling — and where a **caregiver** can tune the routines, look back over weeks, and export a CSV summary when a doctor or family member asks, *“How have things actually been going?”*

Under the hood it’s a **JavaFX** application: checklists for **daily and weekly** routines, a **daily wellbeing log**, **history** views, and **local files** under `data/` so nothing leaves the machine unless you choose to share a report.

> The app is for **routine tracking and record-keeping only**. It does not provide medical diagnosis or treatment advice.

---

## Features

- **Seniors**: Open the app, select their profile, complete today’s **daily and weekly** routine checkboxes, and write or edit a **daily log**.
- **Caregivers**: Sign in with a shared password, **add or edit routines** per senior, **browse weekly/monthly history**, and **generate CSV reports** under `report/`.
- **Persistence**: Each senior has their own folder under `data/users/` (profile, routines, per-day files). Caregiver password is stored in `data/app/caregiver.txt` (first-run setup as implemented in the app).

---

## Why these features (user stories)

- **As a senior**, I want to **see today’s daily and weekly routines** and mark what I have done, so I do not forget important habits like medication or hydration.
- **As a senior**, I want to **write a short daily wellbeing log**, so patterns (sleep, mood, symptoms) can be looked at later without a complicated interface.
- **As a caregiver**, I want to **add, change, or remove routines** for a senior I support, so the task list matches their current care plan.
- **As a caregiver**, I want to **open weekly or monthly history** and **export a CSV summary**, so I can monitor adherence and share a clear report with family or healthcare staff.
- **As a user**, I want **my data stored on this computer** and **loaded the next time I open the app**, so records are not lost between sessions.

---

## Demos

### Main (login — pick senior or caregiver)

![Main screen — login / entry](docs/images/ui-main.png)

### Senior (tasks and routines)

![Senior screen — daily & weekly checklist](docs/images/ui-senior.png)

### Caregiver (menu or caregiver login)

![Caregiver screen — menu after sign-in](docs/images/ui-caregiver.png)

---

## Using the app

### Senior

1. Launch the app and select your **name** on the login screen.
2. **Tasks**: Check off **daily** and **weekly** items for today; completions are saved promptly.
3. **Daily log**: Open the log screen to add or change **free-text notes** for the day.
4. Use the on-screen buttons to move between tasks, log, and logout/back.

### Caregiver

1. Choose the **caregiver** path on the login screen and enter the **password**. The password is the **single line of text** in `data/app/caregiver.txt` (trimmed). If that file did not exist the first time the app started, it was created with default password **`caregiver`**; change it anytime from the caregiver menu (**Change password**).
2. From the menu: **manage routines** (pick a senior, add/remove **daily** or **weekly** routines), **view history** (weekly/monthly views), and **generate summary** CSVs for a selected user.
3. Reports are written under **`report/`** at the project root (for example `*_summary_*.csv`).

### Data locations

- **`data/users/<SeniorName>/`** — `profile.txt`, routine files, `days/<yyyy-MM-dd>.txt`
- **`data/app/caregiver.txt`** — caregiver password file
- **`report/`** — exported CSV summaries

Avoid renaming user folders while the app is running; back up `data/` before editing files by hand.

---

## Requirements

- **JDK 21**
- **Gradle** (wrapper included: `gradlew` / `gradlew.bat`)

---

## Run the application

Run these commands from the **repository root** (the folder that contains `build.gradle`).

```bash
./gradlew run
```

On Windows:

```batch
gradlew.bat run
```

**From an IDE**: use **`HealthcareEveryday.Launcher`** as the main class (matches `build.gradle` `application.mainClass`), or run **`HealthcareEveryday.MainApp`** with the same JavaFX module settings Gradle applies.

The window title is **Healthcare Everyday**. The first screen lets you choose a **senior** or **caregiver** flow.

---

## Build a runnable JAR

```bash
./gradlew shadowJar
```

Output: `build/libs/healthcare-everyday.jar`

```bash
java -jar build/libs/healthcare-everyday.jar
```

On Windows, use `gradlew.bat shadowJar` if you use the batch wrapper.

---

## Tests and style

```bash
./gradlew test
./gradlew checkstyleMain
```

---

## Tech stack

- Java 21, JavaFX 21, Gradle
- FXML views in `src/main/resources/view/`, stylesheet `src/main/resources/css/app.css`
- Domain and persistence in package `HealthcareEveryday` (`Storage`, `User`, `Day`, tasks, services)

---

## Further documentation

| Document | Contents |
|----------|----------|
| [PRD.md](docs/PRD.md) | Product goals, users, functional requirements |
| [SDD.md](docs/SDD.md) | Software design: layers, components, behaviour |
| [ARCHITECTURE.md](docs/ARCHITECTURE.md) | System architecture diagram and storage overview |
| [SEQUENCE_DIAGRAMS.md](docs/SEQUENCE_DIAGRAMS.md) | Step-by-step flows between UI, services, and storage |
| [API.md](docs/API.md) | Implementation notes: packages, `Storage`, controllers (no HTTP API) |
| [TEAM_GIT_WORKFLOW_SourceTree.md](docs/TEAM_GIT_WORKFLOW_SourceTree.md) | Git / SourceTree workflow for contributors |

Editable diagram sources (draw.io): `docs/images/*.drawio`. Exported images: `docs/images/`, `docs/architecture/`.
