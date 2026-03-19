# Healthcare App for Tracking Daily Activities for Seniors

## Product Overview
This application is a desktop GUI-based system designed to help seniors and caregivers manage essential daily and weekly routines, such as medication intake, supplements, hydration, and exercise.

In addition, the system allows users to record simple daily wellbeing logs (e.g., headache, sleep quality, appetite), enabling long-term tracking of health-related patterns.

The application stores records over time and provides weekly or monthly summaries, which can be reviewed by caregivers or shared with healthcare professionals to support better monitoring and communication.

The system is intended for routine tracking and record-keeping purposes only and does not provide medical diagnosis or treatment recommendations.

---

## Problem Statement
Seniors may forget to perform essential daily or weekly activities such as taking medication, maintaining hydration, or following exercise routines. This can negatively affect their health and overall wellbeing.

At the same time, caregivers and healthcare professionals often lack consistent and structured records of a senior’s daily condition, making it difficult to identify patterns or issues over time.

Existing solutions may be too complex, require constant internet access, or are not tailored for elderly users.

Therefore, there is a need for a simple and user-friendly application that helps seniors track routines and log daily wellbeing, while allowing caregivers to review and monitor this information easily.

---

## Target Users / Stakeholders

### Primary Users
- Seniors who need assistance in remembering and tracking daily or weekly routines
- Seniors who want to log their daily wellbeing in a simple way

### Secondary Users
- Caregivers (family members or helpers) who want to monitor the senior’s condition but are not always physically present
- Healthcare professionals who may review summary reports during medical consultations or checkups

---

## User Stories
- As a senior, I want to view my daily routines, so that I know what tasks I need to complete today.
- As a senior, I want to mark routines as completed, so that I can keep track of what I have done.
- As a senior, I want to log how I feel each day, so that my condition can be tracked over time.
- As a senior, I want the system to be simple and easy to use, so that I do not feel overwhelmed.
- As a caregiver, I want to review a senior’s weekly records, so that I can monitor their wellbeing.
- As a caregiver, I want to see if routines are being followed, so that I can identify any issues.
- As a caregiver, I want to generate a summary report, so that it can be shared with a doctor.
- As a user, I want to add, edit, or delete routines, so that I can adapt them according to changing needs or medical advice.
- As a user, I want my data to be saved automatically, so that I do not lose my records.
- As a user, I want the system to remember my profile, so that my data can be differentiated from other users.

---

## Functional Requirements
- The system shall allow users to create, edit, and delete recurring routines (daily or weekly).
- The system shall display routines due for the current day or week.
- The system shall allow users to mark routines as completed or skipped.
- The system shall allow users to record daily wellbeing logs, including symptom type, severity, and optional notes.
- The system shall store data persistently and retrieve it when the application is restarted.
- The system shall maintain recent records (e.g., up to one month) for efficient storage management.
- The system shall allow users to view past records within a selected date range.
- The system shall generate weekly or monthly summaries of routines and wellbeing logs.
- The system shall allow users to export summary data in a readable format (e.g., CSV or PDF).

---

## Non-Functional Requirements

### Usability
- The system should have a simple and intuitive graphical user interface suitable for seniors.
- The system should use clear labels, readable fonts, and minimal steps for common actions.

### Performance
- The system should respond to user actions within 1 second under normal usage conditions.
- The system should generate summaries within a reasonable time (e.g., a few seconds).

### Reliability
- The system should store data reliably and prevent data loss during normal operation.
- The system should handle invalid inputs gracefully without crashing.

### Security
- The system should store user data locally and avoid exposing sensitive information unnecessarily.

### Portability
- The system should run on common desktop operating systems (e.g., Windows, macOS).

### Maintainability
- The system should follow a modular design to allow future enhancements and maintenance.