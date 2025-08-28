import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    LocalDateTime by;

    Deadline(String description, String byRaw) {
        super(description, TaskType.DEADLINE);
        this.by = parseDateTime(byRaw.trim());
    }

    private static LocalDateTime parseDateTime(String raw) {
        try {
            DateTimeFormatter f = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            return LocalDateTime.parse(raw, f);
        } catch (Exception ignored) { }
        try {
            return LocalDateTime.parse(raw);
        } catch (Exception ignored) { }
        LocalDate d = LocalDate.parse(raw);
        return d.atStartOfDay();
    }

    String getByIso() {
        return by.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @Override
    String extraInfo() {
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("MMM d yyyy");
        DateTimeFormatter dateTimeFmt = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm");
        boolean midnight = by.getHour() == 0 && by.getMinute() == 0;
        String pretty = midnight ? by.toLocalDate().format(dateFmt) : by.format(dateTimeFmt);
        return " (by: " + pretty + ")";
    }
}