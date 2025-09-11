import yy.parser.Parser;
import yy.storage.Storage;
import yy.task.Deadline;
import yy.task.Event;
import yy.task.Task;
import yy.task.TaskList;
import yy.task.Todo;
import yy.ui.UI;

public class Duke {

    private String commandType;
    private static final UI ui = new UI();
    private final Storage storage = new Storage();
    private final TaskList tasks = new TaskList(storage.load());
    private final Parser parser = new Parser();

    public static void main(String[] args) {
        ui.showWelcome();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        Parser.Command c = parser.parseCommand(input);
        commandType = c.getClass().getSimpleName();
        String result = "";

        if (input.isEmpty()) {
            return ("Please enter a command.\n");
        }

        switch (c) {
        case BYE: {
            result += "Bye. Hope to see you again soon!\n";
            break;
        }
        case LIST: {
            result += ui.showTaskList(tasks);
            break;
        }
        case MARK: {
            try {
                int idx = Integer.parseInt(input.substring(5).trim()) - 1;
                if (idx >= 0 && idx < tasks.size()) {
                    tasks.mark(idx);
                    storage.save(tasks.asList());
                    result += "Nice! I've marked this task as done:\n";
                    result += (idx + 1) + ". " + tasks.get(idx);
                } else {
                    result += "Invalid task number.\n";
                }
            } catch (NumberFormatException e) {
                result += "Please provide a task number, e.g., mark 2\n";
            }
            break;
        }
        case UNMARK: {
            try {
                int idx = Integer.parseInt(input.substring(7).trim()) - 1;
                if (idx >= 0 && idx < tasks.size()) {
                    tasks.unmark(idx);
                    storage.save(tasks.asList());
                    result += "OK, I've marked this task as not done yet:\n";
                    result += (idx + 1) + ". " + tasks.get(idx);
                } else {
                    result += "Invalid task number.";
                }
            } catch (NumberFormatException e) {
                result += "Please provide a task number, e.g., unmark 2\n";
            }
            break;
        }
        case TODO: {
            if (input.equalsIgnoreCase("todo")) {
                result += "The description of a todo cannot be empty.\n";
                break;
            }
            String desc = input.substring(5).trim();
            if (desc.isEmpty()) {
                result += "The description of a todo cannot be empty.\n";
                break;
            }
            Task t = new Todo(desc);
            tasks.add(t);
            storage.save(tasks.asList());
            result += "Got it. I've added this task:\n";
            result += "  " + t;
            result += "Now you have " + tasks.size() + " tasks in the list.\n";
            break;
        }
        case DEADLINE: {
            if (input.equalsIgnoreCase("deadline")) {
                result += "The deadline command needs '/by'. Format: deadline <description>"
                        + "/by <yyyy-MM-dd or d/M/yyyy HHmm>\n";
                break;
            }
            String rest = input.substring(9).trim();
            String[] parts = rest.split("/by", 2);
            if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                result += "The deadline command needs '/by'. Format: deadline <description>"
                        + "/by <yyyy-MM-dd or d/M/yyyy HHmm>\n";
                break;
            }
            Task t = new Deadline(parts[0].trim(), parts[1].trim());
            tasks.add(t);
            storage.save(tasks.asList());
            result += "Got it. I've added this task:\n";
            result += "  " + t;
            result += "Now you have " + tasks.size() + " tasks in the list.\n";
            break;
        }
        case EVENT: {
            if (input.equalsIgnoreCase("event")) {
                result += "The event command needs '/from' and '/to'. Format: event <description>"
                        + "/from <yyyy-MM-dd or d/M/yyyy HHmm> /to <yyyy-MM-dd or d/M/yyyy HHmm>\n";
                break;
            }
            String rest = input.substring(6).trim();
            String[] descAndFrom = rest.split("/from", 2);
            if (descAndFrom.length < 2 || descAndFrom[0].trim().isEmpty()) {
                result += "The event command needs '/from' and '/to'. Format: event <description>"
                        + "/from <yyyy-MM-dd or d/M/yyyy HHmm> /to <yyyy-MM-dd or d/M/yyyy HHmm>\n";
                break;
            }
            String description = descAndFrom[0].trim();
            String[] fromAndTo = descAndFrom[1].split("/to", 2);
            if (fromAndTo.length < 2 || fromAndTo[0].trim().isEmpty() || fromAndTo[1].trim().isEmpty()) {
                result += "The event command needs '/from' and '/to'. Format: event <description>"
                        + "/from <yyyy-MM-dd or d/M/yyyy HHmm> /to <yyyy-MM-dd or d/M/yyyy HHmm>\n";
                break;
            }
            Task t = new Event(description, fromAndTo[0].trim(), fromAndTo[1].trim());
            tasks.add(t);
            storage.save(tasks.asList());
            result += "Got it. I've added this task:\n";
            result += "  " + t;
            result += "Now you have " + tasks.size() + " tasks in the list.\n";
            break;
        }
        case DELETE: {
            if (input.equalsIgnoreCase("delete")) {
                result += "Please provide a task number to delete. e.g., delete 2\n";
                break;
            }
            String idxStr = input.substring(7).trim();
            try {
                int idx = Integer.parseInt(idxStr) - 1;
                if (idx < 0 || idx >= tasks.size()) {
                    result += "Invalid task number.\n";
                } else {
                    Task removed = tasks.removeAt(idx);
                    storage.save(tasks.asList());
                    result += "Noted. I've removed this task:\n";
                    result += "  " + removed;
                    result += "Now you have " + tasks.size() + " tasks in the list.\n";
                }
            } catch (NumberFormatException e) {
                result += "Please provide a valid task number to delete.\n";
            }
            break;
        }
        case FIND: {
            if (input.equalsIgnoreCase("find")) {
                result += "Please provide a keyword to search for. e.g., find book\n";
                break;
            }
            String keyword = input.substring(5).trim().toLowerCase();
            result += "Here are the matching tasks in your list:\n";
            int count = 0;
            for (int i = 0; i < tasks.size(); i++) {
                Task t = tasks.get(i);
                if (t.getDescription().toLowerCase().contains(keyword)) {
                    count++;
                    result += count + "." + t;
                }
            }
            if (count == 0) {
                result += "No matching tasks found.\n";
            }
            break;
        }
        case UNKNOWN: {
            result += "Unknown Command. Try these commands instead!\n";
            result += "todo <desc>\ndeadline <desc> /by <when>\nevent <desc> /from <start> /to <end>\n";
            result += "list | mark N | unmark N | bye\n";
            break;
        }
        default:
        }
        return result;
    }

    public String getCommandType() {
        return commandType;
    }
}
