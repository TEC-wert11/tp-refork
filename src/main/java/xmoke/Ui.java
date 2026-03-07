package xmoke;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles all user-facing output messages for the chatbot.
 * Two styles of methods:
 * - get*Message() / get*(): return a string for the GUI to display (used by MainWindow).
 * - show*(): print the same content to stdout (used by the CLI in Xmoke.run()).
 */
public class Ui {
    /** Line separator for GUI replies (empty so no dashes/lines in chat). */
    private static final String LINE_SEPARATOR = "";
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    // --- Welcome / goodbye ---
    public String getWelcomeMessage() {
        return LINE_SEPARATOR + "\nHello! I'm XMOKE\nWhat can I do for you?\n" + LINE_SEPARATOR + "\n";
    }

    public void showWelcome() {
        System.out.print(getWelcomeMessage());
    }

    public String getGoodbyeMessage() {
        return LINE_SEPARATOR + "\nBye. Hope to see you again soon!\n" + LINE_SEPARATOR + "\n";
    }

    public void showGoodbye() {
        System.out.print(getGoodbyeMessage());
    }

    /** Prints the XMOKE ASCII logo to stdout (CLI only; GUI does not use this). */
    public void showLogo() {
        String logo =
                "__  __ __  __  ___  _  __ _____\n"
                        + "\\ \\/ /|  \\/  |/ _ \\| |/ /| ____|\n"
                        + " \\  / | |\\/| | | | | ' / |  _|\n"
                        + " /  \\ | |  | | |_| | . \\ | |___\n"
                        + "/_/\\_\\|_|  |_|\\___/|_|\\_\\|_____|\n";

        System.out.println(LINE_SEPARATOR);
        System.out.println("Hello from");
        System.out.println(logo);
        System.out.println(LINE_SEPARATOR);
    }

    // --- Errors and generic messages ---
    public String getErrorMessage(String message) {
        return LINE_SEPARATOR + "\n" + message + "\n" + LINE_SEPARATOR + "\n";
    }

    public void showError(String message) {
        System.out.print(getErrorMessage(message));
    }

    public String getSortSuccessMessage() {
        return LINE_SEPARATOR + "\nTasks sorted by deadline (tasks without deadline at the end).\n"
                + LINE_SEPARATOR + "\n";
    }

    public void showSortSuccess() {
        System.out.print(getSortSuccessMessage());
    }

    // --- Task list (list command) ---
    public String getTaskListMessage(TaskList taskList) {
        StringBuilder sb = new StringBuilder();
        sb.append(LINE_SEPARATOR).append("\nHere are the tasks in your list:\n");
        for (int i = 0; i < taskList.size(); i++) {
            sb.append((i + 1)).append(".").append(taskList.getTask(i)).append("\n");
        }
        sb.append(LINE_SEPARATOR).append("\n");
        return sb.toString();
    }

    public void showTaskList(TaskList taskList) {
        System.out.print(getTaskListMessage(taskList));
    }

    // --- Task added (todo / deadline / event) ---
    public String getTaskAddedMessage(Task task, int taskCount) {
        return LINE_SEPARATOR + "\nGot it. I've added this task:\n" + task + "\n"
                + "Now you have " + taskCount + " tasks in the list.\n" + LINE_SEPARATOR + "\n";
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.print(getTaskAddedMessage(task, taskCount));
    }

    public String getTaskDeletedMessage(Task task, int taskCount) {
        return LINE_SEPARATOR + "\nNoted. I've removed this task:\n  " + task + "\n"
                + "Now you have " + taskCount + " tasks in the list.\n" + LINE_SEPARATOR + "\n";
    }

    public void showTaskDeleted(Task task, int taskCount) {
        System.out.print(getTaskDeletedMessage(task, taskCount));
    }

    // --- Mark / unmark ---
    public String getTaskMarkedMessage(Task task) {
        return LINE_SEPARATOR + "\nNice! I've marked this task as done:\n  " + task + "\n"
                + LINE_SEPARATOR + "\n";
    }

    public void showTaskMarked(Task task) {
        System.out.print(getTaskMarkedMessage(task));
    }

    public String getTaskUnmarkedMessage(Task task) {
        return LINE_SEPARATOR + "\nNice! I've marked this task as not done yet:\n  " + task + "\n"
                + LINE_SEPARATOR + "\n";
    }

    public void showTaskUnmarked(Task task) {
        System.out.print(getTaskUnmarkedMessage(task));
    }

    // --- Tasks on a given date ---
    public String getTasksOnDateMessage(ArrayList<Task> tasks, LocalDate date) {
        StringBuilder sb = new StringBuilder();
        sb.append(LINE_SEPARATOR).append("\n");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy");
        if (tasks.isEmpty()) {
            sb.append("No tasks found on ").append(date.format(formatter)).append("\n");
        } else {
            sb.append("Tasks on ").append(date.format(formatter)).append(":\n");
            for (int i = 0; i < tasks.size(); i++) {
                sb.append((i + 1)).append(".").append(tasks.get(i)).append("\n");
            }
        }
        sb.append(LINE_SEPARATOR).append("\n");
        return sb.toString();
    }

    public void showTasksOnDate(ArrayList<Task> tasks, LocalDate date) {
        System.out.print(getTasksOnDateMessage(tasks, date));
    }

    // --- Find command (keyword search) ---
    public String getFoundTasksMessage(ArrayList<Task> foundTasks) {
        StringBuilder sb = new StringBuilder();
        sb.append(LINE_SEPARATOR).append("\nHere are the matching tasks in your list:\n");
        for (int i = 0; i < foundTasks.size(); i++) {
            sb.append((i + 1)).append(".").append(foundTasks.get(i)).append("\n");
        }
        sb.append(LINE_SEPARATOR).append("\n");
        return sb.toString();
    }

    public void showFoundTasks(ArrayList<Task> foundTasks) {
        System.out.print(getFoundTasksMessage(foundTasks));
    }

    // --- Cheer command (random quote) ---
    public String getCheerMessage(String quote) {
        return LINE_SEPARATOR + "\n" + quote + "\n" + LINE_SEPARATOR + "\n";
    }

    public void showCheer(String quote) {
        System.out.print(getCheerMessage(quote));
    }

    // --- CLI input (used only by Xmoke.run()) ---
    public String readCommand() {
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }
}
