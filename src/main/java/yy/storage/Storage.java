
package yy.storage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import yy.task.*;

/**
 * Handles loading and saving the task list to disk.
 * <p>
 * Uses a simple line-based format stored at a relative path (default: {@code data/yy.txt}).
 * Lines are pipe-delimited with optional whitespace around the delimiter:
 * <pre>
 *   T | 0/1 | <description>
 *   D | 0/1 | <description> | <by-ISO>
 *   E | 0/1 | <description> | <from-ISO> | <to-ISO>
 * </pre>
 * where {@code 1} means done and {@code 0} means not done.
 */
public final class Storage {
    private final Path file;

    /**
     * Creates a Storage that reads from and writes to {@code data/yy.txt}.
     */
    public Storage() {
        this(Paths.get("data", "yy.txt"));
    }

    /**
     * Creates a Storage instance backed by the given file path.
     *
     * @param file path to the data file to use
     */
    public Storage(Path file) {
        this.file = file;
    }

    /**
     * Loads tasks from disk.
     * <p>
     * If the file (or its parent directory) does not exist, this method creates them and
     * returns an empty list. Malformed lines are skipped.
     *
     * @return a list of tasks reconstructed from the save file
     */
    public ArrayList<Task> load() {
        ensureParentFolder();

        if (!Files.exists(file)) {
            try {
                Files.createFile(file);
            } catch (IOException ignored) { /* best effort */ }
            return new ArrayList<>();
        }

        ArrayList<Task> tasks = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
            for (String raw : lines) {
                String line = raw.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s*\\|\\s*");
                if (parts.length < 3) continue;

                String type = parts[0];
                boolean isDone = "1".equals(parts[1]);
                String desc = parts[2];

                Task t = null;
                switch (type) {
                    case "T": {
                        t = new Todo(desc);
                        break;
                    }
                    case "D": {
                        if (parts.length >= 4) {
                            String by = parts[3];
                            t = new Deadline(desc, by);
                        }
                        break;
                    }
                    case "E": {
                        if (parts.length >= 5) {
                            String from = parts[3];
                            String to = parts[4];
                            t = new Event(desc, from, to);
                        }
                        break;
                    }
                }
                if (t != null) {
                    if (isDone) t.mark();
                    tasks.add(t);
                }
            }
        } catch (IOException e) {
            // if anything goes wrong, just start with an empty list
            return new ArrayList<>();
        }
        return tasks;
    }

    /**
     * Saves the provided tasks to disk.
     * <p>
     * Writes to a temporary file and then moves it into place to reduce corruption risk.
     * This is a best-effort operation; I/O errors are swallowed.
     *
     * @param tasks tasks to persist
     */
    public void save(List<Task> tasks) {
        ensureParentFolder();
        List<String> lines = new ArrayList<>(tasks.size());
        for (Task t : tasks) {
            lines.add(serialize(t));
        }

        Path tmp = file.resolveSibling(file.getFileName() + ".tmp");
        try (BufferedWriter out = Files.newBufferedWriter(tmp, StandardCharsets.UTF_8)) {
            for (String line : lines) {
                out.write(line);
                out.newLine();
            }
        } catch (IOException e) {
            return; // best effort: silently skip on save failure
        }
        try {
            Files.move(tmp, file, java.nio.file.StandardCopyOption.REPLACE_EXISTING, java.nio.file.StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            // fallback if atomic move not supported
            try {
                Files.move(tmp, file, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ignored) { }
        }
    }

    /**
     * Ensures the parent directory for the data file exists, creating it if necessary.
     */
    private void ensureParentFolder() {
        try {
            Path parent = file.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
        } catch (IOException ignored) { }
    }

    /**
     * Converts a {@link Task} into its single-line save representation.
     *
     * @param t task to serialize
     * @return line to be written to the save file
     */
    private String serialize(Task t) {
        boolean done = "[X]".equals(t.checkbox());
        int d = done ? 1 : 0;

        if (t instanceof Todo) {
            return "T | " + d + " | " + t.getDescription();
        } else if (t instanceof Deadline dl) {
            return "D | " + d + " | " + dl.getDescription() + " | " + dl.getByIso();
        } else if (t instanceof Event ev) {
            return "E | " + d + " | " + ev.getDescription() + " | " + ev.getFromIso() + " | " + ev.getToIso();
        }
        return "T | " + d + " | " + t;
    }
}