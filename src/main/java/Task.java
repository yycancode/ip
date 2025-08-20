public abstract class Task {
    String description;
    boolean done;

    Task(String description) {
        this.description = description;
        this.done = false;
    }

    void mark() { this.done = true; }
    void unmark() { this.done = false; }
    String checkbox() { return done ? "[X]" : "[ ]"; }

    abstract String getTypeSymbol();   // "T", "D", or "E"
    abstract String extraInfo();       // for deadline/event formatting

    @Override
    public String toString() {
        return "[" + getTypeSymbol() + "]" + checkbox() + " " + description + extraInfo();
    }
}