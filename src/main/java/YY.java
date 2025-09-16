import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import yy.parser.Parser;
import yy.storage.Storage;
import yy.task.Deadline;
import yy.task.Event;
import yy.task.Task;
import yy.task.TaskList;
import yy.task.Todo;
import yy.ui.UI;

/**
 * Represents the YY chatbot application that interacts with users by processing
 * commands related to tasks (e.g., adding tasks, marking them, finding them, etc.).
 * The application handles tasks of type TODO, DEADLINE, EVENT, and others, managing them
 * with the help of task storage and UI components.
 */
public class YY {

    private static final UI ui = new UI();
    private String commandType;
    private final Storage storage = new Storage();
    private final TaskList tasks = new TaskList(storage.load());
    private final Parser parser = new Parser();
    private final Deque<List<Task>> undoStack = new ArrayDeque<>();

    public static void main(String[] args) {
        ui.showWelcome();
    }

    /**
     * Processes the user's input and generates an appropriate response based on the command.
     * Handles commands such as TODO, DEADLINE, EVENT, MARK, UNMARK, DELETE, FIND, etc.
     * Validates the input and performs actions accordingly, such as adding tasks, updating task status,
     * or providing feedback to the user.
     *
     * @param input The user input command string.
     * @return A string containing the result of processing the user's command.
     */
    public String getResponse(String input) {
        assert input != null : "input must not be null";
        Parser.Command c = parser.parseCommand(input);
        commandType = c.getClass().getSimpleName();
        String result = "";

        if (input.isEmpty()) {
            return ("Please enter a command.\n");
        }

        assert tasks.size() >= 0 : "Task list size cannot be negative";

        switch (c) {
        case BYE:
            result += handleBye();
            break;
        case LIST:
            result += handleList();
            break;
        case MARK:
            result += handleMark(input);
            break;
        case UNMARK:
            result += handleUnmark(input);
            break;
        case TODO:
            result += handleTodo(input);
            break;
        case DEADLINE:
            result += handleDeadline(input);
            break;
        case EVENT:
            result += handleEvent(input);
            break;
        case DELETE:
            result += handleDelete(input);
            break;
        case FIND:
            result += handleFind(input);
            break;
        case UNDO:
            result += handleUndo();
            break;
        case UNKNOWN:
            result += handleUnknown();
            break;
        default:
        }
        return result;
    }

    // ==== Command handlers (each returns a user-facing message) ====

    private String handleBye() {
        return "Bye. Hope to see you again soon!\n";
    }

    private String handleList() {
        return ui.showTaskList(tasks);
    }

    private String handleMark(String input) {
        StringBuilder sb = new StringBuilder();
        try {
            int idx = Integer.parseInt(input.substring(5).trim()) - 1;
            Task taskToMark = (idx >= 0 && idx < tasks.size()) ? tasks.get(idx) : null;
            if (taskToMark != null) {
                undoStack.push(new ArrayList<>(tasks.asList()));
                tasks.mark(idx);
                storage.save(tasks.asList());
                sb.append("Nice! I've marked this task as done:\n")
                  .append(idx + 1).append(". ").append(taskToMark);
            } else {
                sb.append("Invalid task number.\n");
            }
        } catch (NumberFormatException e) {
            sb.append("Please provide a task number, e.g., mark 2\n");
        }
        return sb.toString();
    }

    private String handleUnmark(String input) {
        StringBuilder sb = new StringBuilder();
        try {
            int idx = Integer.parseInt(input.substring(7).trim()) - 1;
            if (idx >= 0 && idx < tasks.size()) {
                undoStack.push(new ArrayList<>(tasks.asList()));
                tasks.unmark(idx);
                storage.save(tasks.asList());
                sb.append("OK, I've marked this task as not done yet:\n")
                  .append(idx + 1).append(". ").append(tasks.get(idx));
            } else {
                sb.append("Invalid task number.\n");
            }
        } catch (NumberFormatException e) {
            sb.append("Please provide a task number, e.g., unmark 2\n");
        }
        return sb.toString();
    }

    private String handleTodo(String input) {
        String errorMsg = "The todo command needs a description. Format: todo <description>\n";
        if (input.equalsIgnoreCase("todo")) {
            return errorMsg;
        }
        String desc = input.substring(5).trim();
        if (desc.isEmpty()) {
            return errorMsg;
        }
        undoStack.push(new ArrayList<>(tasks.asList()));
        Task t = new Todo(desc);
        tasks.add(t);
        storage.save(tasks.asList());
        return "Got it. I've added this task:\n"
                + "  " + t + System.lineSeparator()
                + "Now you have " + tasks.size() + " tasks in the list.\n";
    }

    private String handleDeadline(String input) {
        String errorMsg = "The deadline command needs '/by'."
            + "Format: deadline <description>/by <yyyy-MM-dd or d/M/yyyy HHmm>\n";

        if (input.equalsIgnoreCase("deadline")) {
            return errorMsg;
        }
        String rest = input.substring(9).trim();
        String[] parts = rest.split("/by", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            return errorMsg;
        }
        undoStack.push(new ArrayList<>(tasks.asList()));
        Task t = new Deadline(parts[0].trim(), parts[1].trim());
        tasks.add(t);
        storage.save(tasks.asList());
        return "Got it. I've added this task:\n"
                + "  " + t + System.lineSeparator()
                + "Now you have " + tasks.size() + " tasks in the list.\n";
    }

    private String handleEvent(String input) {
        String errorMsg = "The event command needs '/from' and '/to'."
            + "Format: event <description>/from <yyyy-MM-dd or d/M/yyyy HHmm> /to <yyyy-MM-dd or d/M/yyyy HHmm>\n";

        if (input.equalsIgnoreCase("event")) {
            return errorMsg;
        }
        String rest = input.substring(6).trim();
        String[] descAndFrom = rest.split("/from", 2);
        if (descAndFrom.length < 2 || descAndFrom[0].trim().isEmpty()) {
            return errorMsg;
        }
        String description = descAndFrom[0].trim();
        String[] fromAndTo = descAndFrom[1].split("/to", 2);
        if (fromAndTo.length < 2 || fromAndTo[0].trim().isEmpty() || fromAndTo[1].trim().isEmpty()) {
            return errorMsg;
        }
        undoStack.push(new ArrayList<>(tasks.asList()));
        Task t = new Event(description, fromAndTo[0].trim(), fromAndTo[1].trim());
        tasks.add(t);
        storage.save(tasks.asList());
        return "Got it. I've added this task:\n"
                + "  " + t + System.lineSeparator()
                + "Now you have " + tasks.size() + " tasks in the list.\n";
    }

    private String handleDelete(String input) {
        String errorMsg = "The delete command needs a task number. Format: delete <task number>\n";

        if (input.equalsIgnoreCase("delete")) {
            return errorMsg;
        }
        String idxStr = input.substring(7).trim();
        try {
            int idx = Integer.parseInt(idxStr) - 1;
            if (idx < 0 || idx >= tasks.size()) {
                return errorMsg;
            }
            Task removed = tasks.get(idx);
            undoStack.push(new ArrayList<>(tasks.asList()));
            tasks.asList().remove(idx);
            storage.save(tasks.asList());
            return "Noted. I've removed this task:\n"
                    + "  " + removed + System.lineSeparator()
                    + "Now you have " + tasks.size() + " tasks in the list.\n";
        } catch (NumberFormatException e) {
            return errorMsg;
        }
    }

    private String handleFind(String input) {
        if (input.equalsIgnoreCase("find")) {
            return "Please provide a keyword to search for. e.g., find book\n";
        }
        String keyword = input.substring(5).trim().toLowerCase();
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
        List<Task> matchingTasks = tasks.asList().stream()
            .filter(t -> t.getDescription().toLowerCase().contains(keyword))
            .toList();
        if (matchingTasks.isEmpty()) {
            sb.append("No matching tasks found.\n");
        } else {
            for (int i = 0; i < matchingTasks.size(); i++) {
                sb.append(i + 1).append(". ").append(matchingTasks.get(i));
            }
        }
        return sb.toString();
    }

    private String handleUndo() {
        if (undoStack.isEmpty()) {
            return "No actions to undo.\n";
        }
        List<Task> previousState = undoStack.pop();
        tasks.asList().clear();
        tasks.asList().addAll(previousState);
        storage.save(tasks.asList());
        return "Undo successful. The task list has been restored.\n";
    }

    private String handleUnknown() {
        return "Unknown Command. Try these commands instead!\n"
            + "todo <desc>\n"
            + "deadline <desc> /by <when>\n"
            + "event <desc> /from <start> /to <end>\n"
            + "list | mark N | unmark N | bye\n";
    }

    /**
     * Returns the type of the most recent command parsed by the Duke application.
     *
     * @return The type of the command (e.g., TODO, DEADLINE, etc.).
     */
    public String getCommandType() {
        return commandType;
    }
}
