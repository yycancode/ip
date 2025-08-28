package yy.task;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of tasks in the task manager.
 * <p>
 * Provides methods to add, remove, retrieve, and update tasks.
 * Wraps an ArrayList and serves as the main collection for Task objects.
 */

public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() { this.tasks = new ArrayList<>(); }

    /**
     * Constructs a TaskList preloaded with tasks.
     *
     * @param loaded list of tasks to initialize with
     */
    public TaskList(List<Task> loaded) {
        this.tasks = new ArrayList<>(loaded);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return task count
     */
    public int size() { return tasks.size(); }

    /**
     * Checks if the task list is empty.
     *
     * @return true if no tasks are present, false otherwise
     */
    public boolean isEmpty() { return tasks.isEmpty(); }

    /**
     * Returns the task at the specified index.
     *
     * @param i index of the task to retrieve
     * @return task at the given index
     */
    public Task get(int i) { return tasks.get(i); }

    /**
     * Adds a task to the list.
     *
     * @param t task to add
     */
    public void add(Task t) { tasks.add(t); }

    /**
     * Removes and returns the task at the specified index.
     *
     * @param idx index of the task to remove
     * @return removed task
     */
    public Task removeAt(int idx) { return tasks.remove(idx); }

    /**
     * Marks the task at the specified index as done.
     *
     * @param idx index of the task to mark
     */
    public void mark(int idx) { tasks.get(idx).mark(); }

    /**
     * Marks the task at the specified index as not done.
     *
     * @param idx index of the task to unmark
     */
    public void unmark(int idx) { tasks.get(idx).unmark(); }

    /**
     * Returns the underlying list of tasks.
     *
     * @return ArrayList of tasks
     */
    public ArrayList<Task> asList() { return tasks; }
}