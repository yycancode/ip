package yy.task;

/**
 * Represents a Todo task without any date or time attached.
 * <p>
 * A Todo has only a description and inherits behavior from Task.
 */

public class Todo extends Task {

    /**
     * Constructs a new Todo task with the specified description.
     *
     * @param description description of the todo task
     */
    public Todo(String description) {
        super(description, TaskType.TODO);
    }

    /**
     * Returns an empty string since a Todo task does not have extra information
     * such as a deadline or event timing.
     *
     * @return empty string
     */
    String extraInfo() {
        return "";
    }
}
