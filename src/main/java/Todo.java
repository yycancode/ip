class Todo extends Task {
    Todo(String description) {
        super(description, TaskType.TODO);
    }
    String extraInfo() { return ""; }
}