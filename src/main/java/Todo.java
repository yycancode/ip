class Todo extends Task {
    Todo(String description) {
        super(description);
    }
    String getTypeSymbol() { return "T"; }
    String extraInfo() { return ""; }
}