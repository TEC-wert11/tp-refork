# API Notes — Healthcare Everyday

This project is a **desktop JavaFX app** with local file storage.  
There is **no public HTTP/REST API** in the codebase.

## Application Entry

- Main class: `HealthcareEveryday.MainApp`
- Starts JavaFX and wires service instances:
  - `AuthService`
  - `RoutineService`
  - `LogService`
  - `HistoryService`
  - `SummaryService`

## Internal Service API

### `AuthService`

- `getSeniorNames() -> List<String>`
- `addUser(String name) -> boolean`
- `userExists(String name) -> boolean`
- `deleteUser(String name) -> boolean`
- `validateCaregiverPassword(String password) -> boolean`
- `changeCaregiverPassword(String oldPassword, String newPassword) -> boolean`

### `RoutineService`

- `getUser(String userName) -> User`
- `getRoutines(String userName, RoutineType routineType) -> TaskList`
- `getToday(String userName) -> Day`
- `addRoutine(String userName, String description, RoutineType routineType) -> boolean`
- `removeRoutine(String userName, String description, RoutineType routineType) -> boolean`
- `setDailyCompleted(String userName, String taskName, boolean completed) -> void`
- `setWeeklyCompleted(String userName, String taskName, boolean completed) -> void`
- `saveTodayLog(String userName, String log) -> void`

### `LogService`

- `getTodayLog(String userName) -> String`
- `saveTodayLog(String userName, String log) -> void`

### `HistoryService`

- `getTodayHistoryForAllUsers() -> List<TodayUserHistory>`
- `getWeeklyHistory(String userName) -> WeeklyUserHistory`
- Returned DTOs include:
  - `TaskStatus`
  - `TodayUserHistory`
  - `DailyTaskWeeklyRecord`
  - `WeeklyTaskWeeklyRecord`
  - `WeeklyUserHistory`

### `SummaryService`

- `generateMonthlySummary(String userName) -> Path`
- Delegates CSV creation to `SummaryGenerator`

## Persistence Contract (`Storage`)

Data root: `data/`

- `data/users/<senior>/profile.txt`
- `data/users/<senior>/dailyRoutines.txt`
- `data/users/<senior>/weeklyRoutines.txt`
- `data/users/<senior>/days/<yyyy-mm-dd>.txt`
- `data/app/caregiver.txt`

Day file format:

- `log=<text>`
- `[daily]` section with `<task>=true|false`
- `[weekly]` section with `<task>=true|false`

## Report Output

- Monthly summary CSV is generated under `report/`
- File naming: `<UserName>_summary_<yyyy-mm-dd>.csv` (spaces in user name become `_`)

## UI Flow Coverage

Main scenes include:

- Login (senior picker + caregiver entry)
- Senior tasks and daily log
- Caregiver login/menu
- Caregiver routine editing
- Today/weekly history views
- Summary generation user selection
- Delete user view
