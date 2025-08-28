package yy.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task with a start and end date/time.
 * <p>
 * An Event spans a period of time between a start ("from") and an end ("to").
 * It supports parsing from multiple date/time formats, including ISO date/time
 * and "d/M/yyyy HHmm".
 */

public class Event extends Task {
    LocalDateTime from;
    LocalDateTime to;

    /**
     * Constructs a new Event with the specified description, start time, and end time.
     * <p>
     * The raw input strings are parsed into LocalDateTime objects, accepting multiple formats.
     *
     * @param description description of the event
     * @param fromRaw raw start date/time string (e.g. "2019-12-02T18:00" or "2/12/2019 1800")
     * @param toRaw raw end date/time string (e.g. "2019-12-02T20:00" or "2/12/2019 2000")
     */
    public Event(String description, String fromRaw, String toRaw) {
        super(description, TaskType.EVENT);
        this.from = parseDateTime(fromRaw.trim());
        this.to = parseDateTime(toRaw.trim());
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
     * Returns the start date/time in ISO-8601 format.
     *
     * @return ISO-8601 formatted string representing the start date/time
     */
    public String getFromIso() {
        return from.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    /**
     * Returns the end date/time in ISO-8601 format.
     *
     * @return ISO-8601 formatted string representing the end date/time
     */
    public String getToIso() {
        return to.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    /**
     * Returns the string representation of the event's period in a user-friendly format.
     * <p>
     * If either start or end is midnight, only the date is shown; otherwise both date and time are included.
     *
     * @return formatted event period string
     */
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