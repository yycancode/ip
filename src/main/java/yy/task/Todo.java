package yy.task;

public class Todo extends Task {
    public Todo(String description) {
        super(description, TaskType.TODO);
    }
    String extraInfo() { return ""; }
}