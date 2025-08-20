import java.util.ArrayList;
import java.util.Scanner;

public class YY {
    private static class Task {
        String description;
        boolean done;
        Task(String description) { this.description = description; this.done = false; }
        void mark() { this.done = true; }
        void unmark() { this.done = false; }
        String checkbox() { return done ? "[X]" : "[ ]"; }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        String line = "________________________________________";

        System.out.println(line);
        System.out.println("Hello! I'm YY!");
        System.out.println("What can I do for you?");
        System.out.println(line);

        while (true) {
            String input = sc.nextLine();

            if (input.equals("bye")) {
                System.out.println(line);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            } else if (input.equals("list")) {
                System.out.println(line);
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    Task t = tasks.get(i);
                    System.out.println((i + 1) + "." + t.checkbox() + " " + t.description);
                }
                System.out.println(line);
            } else if (input.startsWith("mark ")) {
                try {
                    int idx = Integer.parseInt(input.substring(5).trim()) - 1;
                    if (idx >= 0 && idx < tasks.size()) {
                        Task t = tasks.get(idx);
                        t.mark();
                        System.out.println(line);
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("  " + t.checkbox() + " " + t.description);
                        System.out.println(line);
                    } else {
                        System.out.println(line);
                        System.out.println("Invalid task number.");
                        System.out.println(line);
                    }
                } catch (NumberFormatException e) {
                    System.out.println(line);
                    System.out.println("Please provide a task number, e.g., mark 2");
                    System.out.println(line);
                }
            } else if (input.startsWith("unmark ")) {
                try {
                    int idx = Integer.parseInt(input.substring(7).trim()) - 1;
                    if (idx >= 0 && idx < tasks.size()) {
                        Task t = tasks.get(idx);
                        t.unmark();
                        System.out.println(line);
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("  " + t.checkbox() + " " + t.description);
                        System.out.println(line);
                    } else {
                        System.out.println(line);
                        System.out.println("Invalid task number.");
                        System.out.println(line);
                    }
                } catch (NumberFormatException e) {
                    System.out.println(line);
                    System.out.println("Please provide a task number, e.g., unmark 2");
                    System.out.println(line);
                }
            } else {
                Task t = new Task(input);
                tasks.add(t);
                System.out.println(line);
                System.out.println("added: " + input);
                System.out.println(line);
            }
        }
    }
}