package foo.bar.comparator;

import foo.bar.model.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreComparatorTest {

    private ScoreComparator scoreComparator;
    private Score score1;
    private Score score2;
    private Score score3;

    @BeforeEach
    void setUp() {
        scoreComparator = new ScoreComparator();
        score1 = new Score(2, 1);
        score2 = new Score(0, 3);
        score3 = new Score(5, 7);
    }

    @Test
    void compare() {
        assertEquals(0, scoreComparator.compare(score1, score2));
        assertEquals(0, scoreComparator.compare(score2, score1));
        assertEquals(1, scoreComparator.compare(score3, score1));
        assertEquals(-1, scoreComparator.compare(score2, score3));
    }
}