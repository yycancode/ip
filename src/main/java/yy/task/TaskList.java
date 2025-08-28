package yy.task;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList(List<Task> loaded) {
        this.tasks = new ArrayList<>(loaded);
    }

    public int size() { return tasks.size(); }

    public boolean isEmpty() { return tasks.isEmpty(); }

    public Task get(int i) { return tasks.get(i); }

    public void add(Task t) { tasks.add(t); }

    public Task removeAt(int idx) { return tasks.remove(idx); }

    public void mark(int idx) { tasks.get(idx).mark(); }

    public void unmark(int idx) { tasks.get(idx).unmark(); }

    public ArrayList<Task> asList() { return tasks; }
}