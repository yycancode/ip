package yy.task;

enum Status {
    DONE("[X]"),
    NOT_DONE("[ ]");

    private final String checkbox;

    Status(String checkbox) {
        this.checkbox = checkbox;
    }

    @Override
    public String toString() {
        return checkbox;
    }
}

enum TaskType {
    TODO("T"),
    DEADLINE("D"),
    EVENT("E");

    private final String symbol;

    TaskType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}


/**
 * Represents a generic task in the task manager.
 * <p>
 * Each task has a description, a status (done or not done), and a type
 * (todo, deadline, or event). Subclasses add additional details specific
 * to their task type.
 */

public abstract class Task {
    private final String description;
    private Status status;
    private final TaskType type;

    /**
     * Constructs a new Task with the given description and type.
     * By default, a new task is marked as not done.
     *
     * @param description description of the task
     * @param type type of the task (e.g. TODO, DEADLINE, EVENT)
     */
    Task(String description, TaskType type) {
        this.description = description;
        this.status = Status.NOT_DONE;
        this.type = type;
    }

    /**
     * Marks this task as done.
     */
    public void mark() {
        this.status = Status.DONE;
    }

    /**
     * Marks this task as not done.
     */
    void unmark() {
        this.status = Status.NOT_DONE;
    }

    /**
     * Returns the checkbox symbol representing the task's status.
     *
     * @return "[X]" if done, "[ ]" if not done
     */
    public String checkbox() {
        return status.toString();
    }

    /**
     * Returns the description of this task.
     *
     * @return task description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns additional information specific to the concrete task type,
     * such as deadlines or event timings.
     *
     * @return formatted string with extra details
     */
    abstract String extraInfo();

    /**
     * Returns the string representation of the task, including its type symbol,
     * checkbox status, description, and extra info.
     *
     * @return formatted string representation of the task
     */
    @Override
    public String toString() {
        return "[" + type.getSymbol() + "]" + checkbox() + " " + description + extraInfo();
    }
}
