import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    LocalDateTime from;
    LocalDateTime to;

    Event(String description, String fromRaw, String toRaw) {
        super(description, TaskType.EVENT);
        this.from = parseDateTime(fromRaw.trim());
        this.to = parseDateTime(toRaw.trim());
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

    String getFromIso() {
        return from.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    String getToIso() {
        return to.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @Override
    String extraInfo() {
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("MMM d yyyy");
        DateTimeFormatter dateTimeFmt = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm");

        boolean fromMidnight = from.getHour() == 0 && from.getMinute() == 0;
        boolean toMidnight = to.getHour() == 0 && to.getMinute() == 0;

        String showFrom = fromMidnight ? from.toLocalDate().format(dateFmt) : from.format(dateTimeFmt);
        String showTo = toMidnight ? to.toLocalDate().format(dateFmt) : to.format(dateTimeFmt);

        return " (from: " + showFrom + " to: " + showTo + ")";
    }
}