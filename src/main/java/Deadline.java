public class Deadline extends Task {
    String by;

    Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    String getTypeSymbol() { return "D"; }
    String extraInfo() { return " (by: " + by + ")"; }
}