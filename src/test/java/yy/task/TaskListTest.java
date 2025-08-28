package yy.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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