public class Event extends Task {
    String from;
    String to;

    Event(String description, String from, String to) {
        super(description, TaskType.EVENT);
        this.from = from;
        this.to = to;
    }

    String extraInfo() { return " (from: " + from + " to: " + to + ")"; }
}