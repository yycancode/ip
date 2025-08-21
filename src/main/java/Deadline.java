public class Deadline extends Task {
    String by;

    Deadline(String description, String by) {
        super(description, TaskType.DEADLINE);
        this.by = by;
    }

    String extraInfo() { return " (by: " + by + ")"; }
}