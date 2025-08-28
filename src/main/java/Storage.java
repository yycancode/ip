import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public final class Storage {
    private final Path file;

    public Storage() {
        this(Paths.get("data", "yy.txt"));
    }

    public Storage(Path file) {
        this.file = file;
    }

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
                        // we save Event as: E | done | desc | from | to
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

    private void ensureParentFolder() {
        try {
            Path parent = file.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
        } catch (IOException ignored) { }
    }

    private String serialize(Task t) {
        boolean done = "[X]".equals(t.checkbox());
        int d = done ? 1 : 0;

        if (t instanceof Todo) {
            return "T | " + d + " | " + ((Todo) t).description;
        } else if (t instanceof Deadline dl) {
            return "D | " + d + " | " + dl.description + " | " + dl.getByIso();
        } else if (t instanceof Event ev) {
            return "E | " + d + " | " + ev.description + " | " + ev.getFromIso() + " | " + ev.getToIso();
        }
        return "T | " + d + " | " + t.toString();
    }
}