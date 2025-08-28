public class YY {

    public static void main(String[] args) {
        UI ui = new UI();
        Storage storage = new Storage();
        TaskList tasks = new TaskList(storage.load());
        Parser parser = new Parser();

        ui.showWelcome();

        outer: while (true) {
            String input = ui.readCommand();

            if (input.isEmpty()) {
                ui.showLine();
                ui.show("Please enter a command.");
                ui.showLine();
                continue;
            }

            switch (parser.parseCommand(input)) {
                case BYE: {
                    ui.showLine();
                    ui.show("Bye. Hope to see you again soon!");
                    ui.showLine();
                    break outer;
                }
                case LIST: {
                    ui.showTaskList(tasks);
                    break;
                }
                case MARK: {
                    try {
                        int idx = Integer.parseInt(input.substring(5).trim()) - 1;
                        if (idx >= 0 && idx < tasks.size()) {
                            tasks.mark(idx);
                            storage.save(tasks.asList());
                            ui.showLine();
                            ui.show("Nice! I've marked this task as done:");
                            ui.show("  " + tasks.get(idx));
                            ui.showLine();
                        } else {
                            ui.showLine();
                            ui.show("Invalid task number.");
                            ui.showLine();
                        }
                    } catch (NumberFormatException e) {
                        ui.showLine();
                        ui.show("Please provide a task number, e.g., mark 2");
                        ui.showLine();
                    }
                    break;
                }
                case UNMARK: {
                    try {
                        int idx = Integer.parseInt(input.substring(7).trim()) - 1;
                        if (idx >= 0 && idx < tasks.size()) {
                            tasks.unmark(idx);
                            storage.save(tasks.asList());
                            ui.showLine();
                            ui.show("OK, I've marked this task as not done yet:");
                            ui.show("  " + tasks.get(idx));
                            ui.showLine();
                        } else {
                            ui.showLine();
                            ui.show("Invalid task number.");
                            ui.showLine();
                        }
                    } catch (NumberFormatException e) {
                        ui.showLine();
                        ui.show("Please provide a task number, e.g., unmark 2");
                        ui.showLine();
                    }
                    break;
                }
                case TODO: {
                    if (input.equalsIgnoreCase("todo")) {
                        ui.showLine();
                        ui.show("The description of a todo cannot be empty.");
                        ui.showLine();
                        break;
                    }
                    String desc = input.substring(5).trim();
                    if (desc.isEmpty()) {
                        ui.showLine();
                        ui.show("The description of a todo cannot be empty.");
                        ui.showLine();
                        break;
                    }
                    Task t = new Todo(desc);
                    tasks.add(t);
                    storage.save(tasks.asList());
                    ui.showLine();
                    ui.show("Got it. I've added this task:");
                    ui.show("  " + t);
                    ui.show("Now you have " + tasks.size() + " tasks in the list.");
                    ui.showLine();
                    break;
                }
                case DEADLINE: {
                    if (input.equalsIgnoreCase("deadline")) {
                        ui.showLine();
                        ui.show("The deadline command needs '/by'. Format: deadline <description> /by <yyyy-MM-dd or d/M/yyyy HHmm>");
                        ui.showLine();
                        break;
                    }
                    String rest = input.substring(9).trim();
                    String[] parts = rest.split("/by", 2);
                    if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                        ui.showLine();
                        ui.show("The deadline command needs '/by'. Format: deadline <description> /by <yyyy-MM-dd or d/M/yyyy HHmm>");
                        ui.showLine();
                        break;
                    }
                    Task t = new Deadline(parts[0].trim(), parts[1].trim());
                    tasks.add(t);
                    storage.save(tasks.asList());
                    ui.showLine();
                    ui.show("Got it. I've added this task:");
                    ui.show("  " + t);
                    ui.show("Now you have " + tasks.size() + " tasks in the list.");
                    ui.showLine();
                    break;
                }
                case EVENT: {
                    if (input.equalsIgnoreCase("event")) {
                        ui.showLine();
                        ui.show("The event command needs '/from' and '/to'. Format: event <description> /from <yyyy-MM-dd or d/M/yyyy HHmm> /to <yyyy-MM-dd or d/M/yyyy HHmm>");
                        ui.showLine();
                        break;
                    }
                    String rest = input.substring(6).trim();
                    String[] descAndFrom = rest.split("/from", 2);
                    if (descAndFrom.length < 2 || descAndFrom[0].trim().isEmpty()) {
                        ui.showLine();
                        ui.show("The event command needs '/from' and '/to'. Format: event <description> /from <yyyy-MM-dd or d/M/yyyy HHmm> /to <yyyy-MM-dd or d/M/yyyy HHmm>");
                        ui.showLine();
                        break;
                    }
                    String description = descAndFrom[0].trim();
                    String[] fromAndTo = descAndFrom[1].split("/to", 2);
                    if (fromAndTo.length < 2 || fromAndTo[0].trim().isEmpty() || fromAndTo[1].trim().isEmpty()) {
                        ui.showLine();
                        ui.show("The event command needs '/from' and '/to'. Format: event <description> /from <yyyy-MM-dd or d/M/yyyy HHmm> /to <yyyy-MM-dd or d/M/yyyy HHmm>");
                        ui.showLine();
                        break;
                    }
                    Task t = new Event(description, fromAndTo[0].trim(), fromAndTo[1].trim());
                    tasks.add(t);
                    storage.save(tasks.asList());
                    ui.showLine();
                    ui.show("Got it. I've added this task:");
                    ui.show("  " + t);
                    ui.show("Now you have " + tasks.size() + " tasks in the list.");
                    ui.showLine();
                    break;
                }
                case DELETE: {
                    if (input.equalsIgnoreCase("delete")) {
                        ui.showLine();
                        ui.show("Please provide a task number to delete. e.g., delete 2");
                        ui.showLine();
                        break;
                    }
                    String idxStr = input.substring(7).trim();
                    try {
                        int idx = Integer.parseInt(idxStr) - 1;
                        if (idx < 0 || idx >= tasks.size()) {
                            ui.showLine();
                            ui.show("Invalid task number.");
                            ui.showLine();
                        } else {
                            Task removed = tasks.removeAt(idx);
                            storage.save(tasks.asList());
                            ui.showLine();
                            ui.show("Noted. I've removed this task:");
                            ui.show("  " + removed);
                            ui.show("Now you have " + tasks.size() + " tasks in the list.");
                            ui.showLine();
                        }
                    } catch (NumberFormatException e) {
                        ui.showLine();
                        ui.show("Please provide a valid task number to delete.");
                        ui.showLine();
                    }
                    break;
                }
                case UNKNOWN: {
                    ui.showLine();
                    ui.show("Unknown Command. Try these commands instead!");
                    ui.show("todo <desc>\ndeadline <desc> /by <when>\nevent <desc> /from <start> /to <end>");
                    ui.show("list | mark N | unmark N | bye");
                    ui.showLine();
                    break;
                }
            }
        }
    }
}