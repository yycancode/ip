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

public abstract class Task {
    String description;
    Status status;
    TaskType type;

    Task(String description, TaskType type) {
        this.description = description;
        this.status = Status.NOT_DONE;
        this.type = type;
    }

    public void mark() { this.status = Status.DONE; }
    void unmark() { this.status = Status.NOT_DONE; }
    public String checkbox() { return status.toString(); }
    public String getDescription() {return this.description; }

    abstract String extraInfo();

    @Override
    public String toString() {
        return "[" + type.getSymbol() + "]" + checkbox() + " " + description + extraInfo();
    }
}