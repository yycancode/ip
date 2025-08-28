package yy.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeadlineTest {

    @Test
    public void deadline_parsesIsoDate_correctly() {
        Deadline d = new Deadline("return book", "2019-12-02T18:00");
        assertTrue(d.extraInfo().contains("Dec 2 2019 18:00"));
    }

    @Test
    public void deadline_parsesShortDate_correctly() {
        Deadline d = new Deadline("return book", "2/12/2019 1800");
        String info = d.extraInfo();
        assertTrue(info.contains("Dec 2 2019 18:00"), "Should format into nice date/time");
    }
}