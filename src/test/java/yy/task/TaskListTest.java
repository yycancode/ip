package yy.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TaskListTest {

    @Test
    public void addTask_sizeIncreases() {
        TaskList list = new TaskList();
        Task t = new Todo("read book");
        list.add(t);
        assertEquals(1, list.size());
        assertSame(t, list.get(0));
    }

    @Test
    public void removeTask_invalidIndex_throws() {
        TaskList list = new TaskList();
        assertThrows(IndexOutOfBoundsException.class, () -> list.removeAt(0));
    }
}
