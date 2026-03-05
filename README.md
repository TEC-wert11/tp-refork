# XMOKE

A desktop chatbot for managing tasks, built with Java and JavaFX for course CS2103DE. Named after the CS god, XMOKE.

## Features

- **GUI**: Chat-style window; type commands in the text box and press Enter or click Send.
- **Tasks**: Add todos, deadlines, and events; list, mark, unmark, delete, and find tasks.
- **Persistence**: Tasks are saved to a data file and loaded when you start the app.
- **Bye**: Type `bye` to see a goodbye message and close the app.

## Prerequisites

- **JDK 21**

## Setting up in IntelliJ

1. Open IntelliJ (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project first).
2. Open the project:
   - Click `Open`.
   - Select the project directory, then click `OK`.
   - Accept the defaults if prompted.
3. Configure the project to use **JDK 21**: [Set up JDK in IntelliJ](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk). Set **Project language level** to `SDK default`.
4. To run the app: run `MainApp` (in `src/main/java/MainApp.java`) — e.g. right-click `MainApp.java` and choose **Run 'MainApp.main()'**, or run the Gradle task **run** (e.g. `.\gradlew run`). A window titled **XMOKE** should open; select a user, click **Enter**, then type in the text box and press Enter or click **Send**.

**Note:** Keep the `src/main/java` folder as the root for Java source files so that tools like Gradle can find them.

## Running the app (command line)

From the project root:

```bash
./gradlew run
```

On Windows:

```batch
gradlew.bat run
```

The GUI window will open. You can also build a runnable JAR:

```bash
./gradlew shadowJar
```

The JAR is created in `build/libs/`. Run it with:

```bash
java -jar build/libs/javafx-tutorial-duke.jar
```

## Running tests

Unit tests (JUnit 5):

```bash
./gradlew test
```

On Windows:

```batch
gradlew.bat test
```

## Tech

- Java 21, JavaFX 21 (GUI), Gradle. Main entry point: `MainApp`; GUI and FXML under `src/main/resources/view/`.
