package xmoke;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Entry point of the XMOKE chatbot application.
 * Handles the program startup flow and delegates user interactions to other components.
 */
public class Xmoke {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /** Creates Xmoke with default storage path and loads tasks from disk. */
    public Xmoke() {
        ui = new Ui();
        storage = new Storage();
        tasks = storage.loadTasks();
    }

    /** Creates Xmoke with the given storage (e.g. for a specific user). */
    public Xmoke(Storage storage) {
        ui = new Ui();
        this.storage = storage;
        this.tasks = storage.loadTasks();
    }

    /**
     * Generates a response for the given user input.
     * Used by the GUI to get a single reply for a single message.
     * Command order below matters: more specific patterns (e.g. "delete 1") are checked
     * before generic ones (e.g. "delete").
     *
     * @param input The user's input string.
     * @return The response string to display.
     */
    public String getResponse(String input) {
        // --- bye: exit greeting (GUI uses this; CLI exits in run() loop) ---
        if (input.trim().equalsIgnoreCase("bye")) {
            return ui.getGoodbyeMessage();
        }

        // --- list: show all tasks ---
        if (input.equals("list")) {
            return ui.getTaskListMessage(tasks);
        }

        // --- sort: sort tasks by deadline, then save and show updated list ---
        if (input.trim().equalsIgnoreCase("sort")) {
            tasks.sortByDeadline();
            storage.saveTasks(tasks);
            return ui.getSortSuccessMessage() + ui.getTaskListMessage(tasks);
        }

        // --- cheer: show a random motivational quote from storage ---
        if (input.trim().equals("cheer")) {
            return ui.getCheerMessage(storage.getRandomCheerQuote());
        }

        // --- delete: require "delete <number>"; bare "delete" is an error ---
        if (input.trim().equals("delete")) {
            return ui.getErrorMessage("OOPS!!! Please provide a task number to delete.");
        }

        if (input.trim().startsWith("delete ")) {
            try {
                int index = Parser.parseTaskIndex(
                        input.trim().substring("delete ".length()), tasks.size());
                Task deletedTask = tasks.deleteTask(index);
                storage.saveTasks(tasks);
                return ui.getTaskDeletedMessage(deletedTask, tasks.size());
            } catch (NumberFormatException e) {
                return ui.getErrorMessage("OOPS!!! Please provide a valid task number.");
            } catch (IndexOutOfBoundsException e) {
                return ui.getErrorMessage("OOPS!!! That task number is out of range.");
            }
        }

        // --- find: search tasks by keyword; "find <keyword>" ---
        if (input.trim().equals("find")) {
            return ui.getErrorMessage("OOPS!!! Please provide a keyword to find.");
        }

        if (input.trim().startsWith("find ")) {
            String keyword = input.trim().substring("find ".length()).trim();
            if (keyword.isEmpty()) {
                return ui.getErrorMessage("OOPS!!! Please provide a keyword to find.");
            }
            return ui.getFoundTasksMessage(tasks.findTasks(keyword));
        }

        // --- mark: mark task as done; "mark <number>" ---
        if (input.trim().equals("mark")) {
            return ui.getErrorMessage("OOPS!!! Please provide a task number to mark.");
        }

        if (input.trim().startsWith("mark ")) {
            try {
                int index = Parser.parseTaskIndex(
                        input.trim().substring("mark ".length()), tasks.size());
                tasks.markTask(index);
                storage.saveTasks(tasks);
                return ui.getTaskMarkedMessage(tasks.getTask(index));
            } catch (NumberFormatException e) {
                return ui.getErrorMessage("OOPS!!! Please provide a valid task number.");
            } catch (IndexOutOfBoundsException e) {
                return ui.getErrorMessage("OOPS!!! That task number is out of range.");
            }
        }

        // --- unmark: mark task as not done; "unmark <number>" ---
        if (input.trim().equals("unmark")) {
            return ui.getErrorMessage("OOPS!!! Please provide a task number to unmark.");
        }

        if (input.trim().startsWith("unmark ")) {
            try {
                int index = Parser.parseTaskIndex(
                        input.trim().substring("unmark ".length()), tasks.size());
                tasks.unmarkTask(index);
                storage.saveTasks(tasks);
                return ui.getTaskUnmarkedMessage(tasks.getTask(index));
            } catch (NumberFormatException e) {
                return ui.getErrorMessage("OOPS!!! Please provide a valid task number.");
            } catch (IndexOutOfBoundsException e) {
                return ui.getErrorMessage("OOPS!!! That task number is out of range.");
            }
        }

        // --- capacity check: no new tasks if list is full ---
        if (tasks.isFull()) {
            return ui.getErrorMessage("I can't take it anymore!");
        }

        // --- todo: add a todo task; "todo <description>" ---
        if (input.trim().equals("todo")) {
            return ui.getErrorMessage("OOPS!!! The description of a todo cannot be empty.");
        }

        if (input.trim().startsWith("todo ")) {
            String description = input.trim().substring("todo ".length()).trim();
            if (description.isEmpty()) {
                return ui.getErrorMessage("OOPS!!! The description of a todo cannot be empty.");
            }
            Task task = new Task(description, Task.TaskType.T);
            tasks.addTask(task);
            storage.saveTasks(tasks);
            return ui.getTaskAddedMessage(task, tasks.size());
        }

        // --- deadline: add a deadline task; "deadline <description> /by <date time>" ---
        if (input.trim().startsWith("deadline ")) {
            String remainder = input.trim().substring("deadline ".length()).trim();
            String[] parts = remainder.split(" /by ", 2);
            if (parts.length < 2) {
                return ui.getErrorMessage("OOPS!!! A deadline must have /by followed by a date/time.");
            }

            String description = parts[0].trim();
            String dateTimeStr = parts[1].trim();

            try {
                LocalDateTime dateTime = Parser.parseDateTime(dateTimeStr);
                Task task = new Task(description, Task.TaskType.D, dateTime);
                tasks.addTask(task);
                storage.saveTasks(tasks);
                return ui.getTaskAddedMessage(task, tasks.size());
            } catch (DateTimeParseException e) {
                return ui.getErrorMessage("OOPS!!! Invalid date/time format. Use yyyy-MM-dd HHmm or d/M/yyyy HHmm");
            }
        }

        // --- event: add an event with start/end; "event <description> /from <date time> /to <date time>" ---
        if (input.trim().startsWith("event ")) {
            String remainder = input.trim().substring("event ".length()).trim();
            String[] firstSplit = remainder.split(" /from ", 2);

            if (firstSplit.length < 2) {
                return ui.getErrorMessage("OOPS!!! An event must have /from and /to.");
            }

            String descriptionPart = firstSplit[0].trim();
            String[] secondSplit = firstSplit[1].split(" /to ", 2);

            if (secondSplit.length < 2) {
                return ui.getErrorMessage("OOPS!!! An event must have /to.");
            }

            String fromTimeStr = secondSplit[0].trim();
            String toTimeStr = secondSplit[1].trim();

            if (descriptionPart.isEmpty() || fromTimeStr.isEmpty() || toTimeStr.isEmpty()) {
                return ui.getErrorMessage("OOPS!!! The description/from/to of an event cannot be empty.");
            }

            try {
                LocalDateTime fromTime = Parser.parseDateTime(fromTimeStr);
                LocalDateTime toTime = Parser.parseDateTime(toTimeStr);

                String description = descriptionPart + " (from: " + fromTimeStr + "; to: " + toTimeStr + ")";
                Task task = new Task(description, Task.TaskType.E, fromTime);
                tasks.addTask(task);
                storage.saveTasks(tasks);
                return ui.getTaskAddedMessage(task, tasks.size());
            } catch (DateTimeParseException e) {
                return ui.getErrorMessage("OOPS!!! Invalid date/time format. Use yyyy-MM-dd HHmm or d/M/yyyy HHmm");
            }
        }

        // --- unknown command ---
        return ui.getErrorMessage("OOPS!!! I'm sorry, but I don't know what that means :-(");
    }

    /**
     * Runs the text-based UI loop: show logo and welcome, then read commands from stdin,
     * process each (same commands as getResponse), and exit on "bye".
     */
    public void run() {
        ui.showLogo();
        ui.showWelcome();

        while (true) {
            String input = ui.readCommand();

            if (input.trim().equalsIgnoreCase("bye")) {
                ui.showGoodbye();
                break;
            }

            if (input.equals("list")) {
                ui.showTaskList(tasks);
                continue;
            }

            if (input.trim().equalsIgnoreCase("sort")) {
                tasks.sortByDeadline();
                storage.saveTasks(tasks);
                ui.showSortSuccess();
                ui.showTaskList(tasks);
                continue;
            }

            if (input.trim().equals("cheer")) {
                ui.showCheer(storage.getRandomCheerQuote());
                continue;
            }

            if (input.trim().equals("delete")) {
                ui.showError("OOPS!!! Please provide a task number to delete.");
                continue;
            }

            if (input.trim().startsWith("delete ")) {
                try {
                    int index = Parser.parseTaskIndex(
                            input.trim().substring("delete ".length()), tasks.size());
                    Task deletedTask = tasks.deleteTask(index);
                    storage.saveTasks(tasks);
                    ui.showTaskDeleted(deletedTask, tasks.size());
                } catch (NumberFormatException e) {
                    ui.showError("OOPS!!! Please provide a valid task number.");
                } catch (IndexOutOfBoundsException e) {
                    ui.showError("OOPS!!! That task number is out of range.");
                }
                continue;
            }

            if (input.trim().equals("find")) {
                ui.showError("OOPS!!! Please provide a keyword to find.");
                continue;
            }

            if (input.trim().startsWith("find ")) {
                String keyword = input.trim().substring("find ".length()).trim();
                if (keyword.isEmpty()) {
                    ui.showError("OOPS!!! Please provide a keyword to find.");
                    continue;
                }
                ui.showFoundTasks(tasks.findTasks(keyword));
                continue;
            }

            if (input.trim().equals("mark")) {
                ui.showError("OOPS!!! Please provide a task number to mark.");
                continue;
            }

            if (input.trim().startsWith("mark ")) {
                try {
                    int index = Parser.parseTaskIndex(
                            input.trim().substring("mark ".length()), tasks.size());
                    tasks.markTask(index);
                    storage.saveTasks(tasks);
                    ui.showTaskMarked(tasks.getTask(index));
                } catch (NumberFormatException e) {
                    ui.showError("OOPS!!! Please provide a valid task number.");
                } catch (IndexOutOfBoundsException e) {
                    ui.showError("OOPS!!! That task number is out of range.");
                }
                continue;
            }

            if (input.trim().equals("unmark")) {
                ui.showError("OOPS!!! Please provide a task number to unmark.");
                continue;
            }

            if (input.trim().startsWith("unmark ")) {
                try {
                    int index = Parser.parseTaskIndex(
                            input.trim().substring("unmark ".length()), tasks.size());
                    tasks.unmarkTask(index);
                    storage.saveTasks(tasks);
                    ui.showTaskUnmarked(tasks.getTask(index));
                } catch (NumberFormatException e) {
                    ui.showError("OOPS!!! Please provide a valid task number.");
                } catch (IndexOutOfBoundsException e) {
                    ui.showError("OOPS!!! That task number is out of range.");
                }
                continue;
            }

            if (tasks.isFull()) {
                ui.showError("I can't take it anymore!");
                continue;
            }

            if (input.trim().equals("todo")) {
                ui.showError("OOPS!!! The description of a todo cannot be empty.");
                continue;
            }

            if (input.trim().startsWith("todo ")) {
                String description = input.trim().substring("todo ".length()).trim();
                if (description.isEmpty()) {
                    ui.showError("OOPS!!! The description of a todo cannot be empty.");
                    continue;
                }
                Task task = new Task(description, Task.TaskType.T);
                tasks.addTask(task);
                storage.saveTasks(tasks);
                ui.showTaskAdded(task, tasks.size());
                continue;
            }

            if (input.trim().startsWith("deadline ")) {
                String remainder = input.trim().substring("deadline ".length()).trim();
                String[] parts = remainder.split(" /by ", 2);
                if (parts.length < 2) {
                    ui.showError("OOPS!!! A deadline must have /by followed by a date/time.");
                    continue;
                }

                String description = parts[0].trim();
                String dateTimeStr = parts[1].trim();

                try {
                    LocalDateTime dateTime = Parser.parseDateTime(dateTimeStr);
                    Task task = new Task(description, Task.TaskType.D, dateTime);
                    tasks.addTask(task);
                    storage.saveTasks(tasks);
                    ui.showTaskAdded(task, tasks.size());
                } catch (DateTimeParseException e) {
                    ui.showError("OOPS!!! Invalid date/time format. Use yyyy-MM-dd HHmm or d/M/yyyy HHmm");
                }
                continue;
            }

            if (input.trim().startsWith("event ")) {
                String remainder = input.trim().substring("event ".length()).trim();
                String[] firstSplit = remainder.split(" /from ", 2);

                if (firstSplit.length < 2) {
                    ui.showError("OOPS!!! An event must have /from and /to.");
                    continue;
                }

                String descriptionPart = firstSplit[0].trim();
                String[] secondSplit = firstSplit[1].split(" /to ", 2);

                if (secondSplit.length < 2) {
                    ui.showError("OOPS!!! An event must have /to.");
                    continue;
                }

                String fromTimeStr = secondSplit[0].trim();
                String toTimeStr = secondSplit[1].trim();

                if (descriptionPart.isEmpty() || fromTimeStr.isEmpty() || toTimeStr.isEmpty()) {
                    ui.showError("OOPS!!! The description/from/to of an event cannot be empty.");
                    continue;
                }

                try {
                    LocalDateTime fromTime = Parser.parseDateTime(fromTimeStr);
                    LocalDateTime toTime = Parser.parseDateTime(toTimeStr);

                    String description = descriptionPart + " (from: " + fromTimeStr + "; to: " + toTimeStr + ")";
                    Task task = new Task(description, Task.TaskType.E, fromTime);
                    tasks.addTask(task);
                    storage.saveTasks(tasks);
                    ui.showTaskAdded(task, tasks.size());
                } catch (DateTimeParseException e) {
                    ui.showError("OOPS!!! Invalid date/time format. Use yyyy-MM-dd HHmm or d/M/yyyy HHmm");
                }
                continue;
            }

            ui.showError("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }

        ui.close();
    }

    /**
     * Runs the XMOKE chatbot.
     *
     * @param args Command-line arguments (not used).
     */

    public static void main(String... args) {
        new Xmoke().run();
    }
}
