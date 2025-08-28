package yy.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task with a specific due date/time.
 * <p>
 * A Deadline is a task that must be completed by a certain date and time.
 * It supports multiple input formats for parsing, including ISO date/time
 * and "d/M/yyyy HHmm".
 */

public class Deadline extends Task {
    LocalDateTime by;

    /**
     * Constructs a new Deadline task with the specified description and raw date/time string.
     * <p>
     * The raw input is parsed into a LocalDateTime, accepting multiple formats.
     *
     * @param description description of the deadline task
     * @param byRaw raw date/time string (e.g. "2019-12-02T18:00" or "2/12/2019 1800")
     */
    public Deadline(String description, String byRaw) {
        super(description, TaskType.DEADLINE);
        this.by = parseDateTime(byRaw.trim());
    }

    /**
     * Parses a raw string into a LocalDateTime.
     * <p>
     * Supports multiple formats: "d/M/yyyy HHmm", ISO_LOCAL_DATE_TIME, and ISO_LOCAL_DATE.
     *
     * @param raw raw date/time string
     * @return parsed LocalDateTime
     */
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

    /**
     * Returns the due date/time in ISO-8601 format.
     *
     * @return ISO-8601 formatted string representing the due date/time
     */
    public String getByIso() {
        return by.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    /**
     * Returns the string representation of the deadline's due date/time
     * in a user-friendly format.
     * <p>
     * If the time is midnight, only the date is shown; otherwise both
     * date and time are included.
     *
     * @return formatted due date/time string
     */
    @Override
    String extraInfo() {
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("MMM d yyyy");
        DateTimeFormatter dateTimeFmt = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm");
        boolean midnight = by.getHour() == 0 && by.getMinute() == 0;
        String pretty = midnight ? by.toLocalDate().format(dateFmt) : by.format(dateTimeFmt);
        return " (by: " + pretty + ")";
    }
}