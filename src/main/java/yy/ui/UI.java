package yy.ui;

import java.util.Scanner;

import yy.task.TaskList;

/**
 * Handles all user interactions for the YY application.
 * <p>
 * Responsible for displaying messages, showing task lists,
 * and reading user input from the console.
 */
public class UI {
    public static final String LINE = "________________________________________";
    private final Scanner sc = new Scanner(System.in);

    /**
     * Displays the welcome message when the application starts.
     */
    public void showWelcome() {
        System.out.println(LINE);
        System.out.println("Hello! I'm YY!");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }

    /**
     * Reads the next line of user input from the console.
     *
     * @return trimmed input string entered by the user
     */
    public String readCommand() {
        return sc.nextLine().trim();
    }

    /**
     * Prints a horizontal line divider to the console.
     */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Prints a given string to the console.
     *
     * @param s message to print
     */
    public void show(String s) {
        System.out.println(s);
    }

    /**
     * Displays the list of tasks in a numbered format.
     * If no tasks exist, shows a message indicating the list is empty.
     *
     * @param tasks the TaskList to display
     */
    public void showTaskList(TaskList tasks) {
        showLine();
        if (tasks.isEmpty()) {
            show("Your list is empty.");
        } else {
            show("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i));
            }
        }
        showLine();
    }
}
