public class Event extends Task {
    String from;
    String to;

    Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    String getTypeSymbol() { return "E"; }
    String extraInfo() { return " (from: " + from + " to: " + to + ")"; }
}