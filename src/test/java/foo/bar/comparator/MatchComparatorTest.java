package foo.bar.comparator;

import foo.bar.model.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MatchComparatorTest {

    private MatchComparator matchComparator;
    private Match match1;
    private Match match2;
    private Match match3;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        matchComparator = new MatchComparator();
        match1 = new Match("team1", "team2");
        match2 = new Match("team3", "team4");
        match3 = new Match("team5", "team6");
        Field startTime1 = match1.getClass().getDeclaredField("startTime");
        Field startTime2 = match2.getClass().getDeclaredField("startTime");
        Field startTime3 = match3.getClass().getDeclaredField("startTime");
        startTime1.setAccessible(true);
        startTime2.setAccessible(true);
        startTime3.setAccessible(true);
        startTime1.set(match1, LocalDateTime.of(2022, 10, 19, 13, 30));
        startTime2.set(match2, LocalDateTime.of(2022, 10, 19, 13, 30));
        startTime3.set(match3, LocalDateTime.of(2022, 10, 19, 13, 40));
    }

    @Test
    void compare() {
        assertEquals(0, matchComparator.compare(match1, match2));
        assertEquals(0, matchComparator.compare(match2, match1));
        assertEquals(1, matchComparator.compare(match3, match1));
        assertEquals(-1, matchComparator.compare(match1, match3));
    }
}