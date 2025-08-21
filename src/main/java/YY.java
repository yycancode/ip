import java.util.ArrayList;
import java.util.Scanner;

public class YY {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        String line = "________________________________________";

        System.out.println(line);
        System.out.println("Hello! I'm YY!");
        System.out.println("What can I do for you?");
        System.out.println(line);

        while (true) {
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println(line);
                System.out.println("Please enter a command.");
                System.out.println(line);
                continue;
            }

            if (input.equalsIgnoreCase("bye")) {
                System.out.println(line);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(line);
                break;

            } else if (input.equalsIgnoreCase("list")) {
                System.out.println(line);
                if (tasks.isEmpty()) {
                    System.out.println("Your list is empty.");
                } else {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        Task t = tasks.get(i);
                        System.out.println((i + 1) + "." + t);
                    }
                }
                System.out.println(line);

            } else if (input.toLowerCase().startsWith("mark ")) {
                try {
                    int idx = Integer.parseInt(input.substring(5).trim()) - 1;
                    if (idx >= 0 && idx < tasks.size()) {
                        Task t = tasks.get(idx);
                        t.mark();
                        System.out.println(line);
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("  " + t);
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

            } else if (input.toLowerCase().startsWith("unmark ")) {
                try {
                    int idx = Integer.parseInt(input.substring(7).trim()) - 1;
                    if (idx >= 0 && idx < tasks.size()) {
                        Task t = tasks.get(idx);
                        t.unmark();
                        System.out.println(line);
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("  " + t);
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

            } else if (input.equalsIgnoreCase("todo")) {
                System.out.println(line);
                System.out.println("The description of a todo cannot be empty.");
                System.out.println(line);

            } else if (input.toLowerCase().startsWith("todo ")) {
                String desc = input.substring(5).trim();
                if (desc.isEmpty()) {
                    System.out.println(line);
                    System.out.println("The description of a todo cannot be empty.");
                    System.out.println(line);
                    continue;
                }
                Task t = new Todo(desc);
                tasks.add(t);
                System.out.println(line);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + t);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                System.out.println(line);

            } else if (input.equalsIgnoreCase("deadline")) {
                System.out.println(line);
                System.out.println("The deadline command needs '/by'. Format: deadline <description> /by <when>");
                System.out.println(line);

            } else if (input.toLowerCase().startsWith("deadline ")) {
                String rest = input.substring(9).trim();
                String[] parts = rest.split("/by", 2);
                if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                    System.out.println(line);
                    System.out.println("The deadline command needs '/by'. Format: deadline <description> /by <when>");
                    System.out.println(line);
                    continue;
                }
                Task t = new Deadline(parts[0].trim(), parts[1].trim());
                tasks.add(t);
                System.out.println(line);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + t);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                System.out.println(line);

            } else if (input.equalsIgnoreCase("event")) {
                System.out.println(line);
                System.out.println("The event command needs '/from' and '/to'. Format: event <description> /from <start> /to <end>");
                System.out.println(line);

            } else if (input.toLowerCase().startsWith("event ")) {
                String rest = input.substring(6).trim();
                String[] descAndFrom = rest.split("/from", 2);
                if (descAndFrom.length < 2 || descAndFrom[0].trim().isEmpty()) {
                    System.out.println(line);
                    System.out.println("The event command needs '/from' and '/to'. Format: event <description> /from <start> /to <end>");
                    System.out.println(line);
                    continue;
                }
                String description = descAndFrom[0].trim();
                String[] fromAndTo = descAndFrom[1].split("/to", 2);
                if (fromAndTo.length < 2 || fromAndTo[0].trim().isEmpty() || fromAndTo[1].trim().isEmpty()) {
                    System.out.println(line);
                    System.out.println("The event command needs '/from' and '/to'. Format: event <description> /from <start> /to <end>");
                    System.out.println(line);
                    continue;
                }
                Task t = new Event(description, fromAndTo[0].trim(), fromAndTo[1].trim());
                tasks.add(t);
                System.out.println(line);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + t);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                System.out.println(line);

            } else if (input.equalsIgnoreCase("delete")) {
                System.out.println(line);
                System.out.println("Please provide a task number to delete. e.g., delete 2");
                System.out.println(line);

            } else if (input.toLowerCase().startsWith("delete ")) {
                String idxStr = input.substring(7).trim();
                try {
                    int idx = Integer.parseInt(idxStr) - 1;
                    if (idx < 0 || idx >= tasks.size()) {
                        System.out.println(line);
                        System.out.println("Invalid task number.");
                        System.out.println(line);
                    } else {
                        Task removed = tasks.remove(idx);
                        System.out.println(line);
                        System.out.println("Noted. I've removed this task:");
                        System.out.println("  " + removed);
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        System.out.println(line);
                    }
                } catch (NumberFormatException e) {
                    System.out.println(line);
                    System.out.println("Please provide a valid task number to delete.");
                    System.out.println(line);
                }

            } else {
                System.out.println(line);
                System.out.println("Unknown Command. Try these commands instead!");
                System.out.println("todo <desc>\ndeadline <desc> /by <when>\nevent <desc> /from <start> /to <end>");
                System.out.println("list | mark N | unmark N | bye");
                System.out.println(line);
            }
        }
    }
}