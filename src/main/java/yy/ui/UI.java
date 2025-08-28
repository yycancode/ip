package yy.ui;

import java.util.Scanner;
import yy.task.TaskList;

public class UI {
    public static final String LINE = "________________________________________";
    private final Scanner sc = new Scanner(System.in);

    public void showWelcome() {
        System.out.println(LINE);
        System.out.println("Hello! I'm YY!");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }

    public String readCommand() {
        return sc.nextLine().trim();
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public void show(String s) {
        System.out.println(s);
    }

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