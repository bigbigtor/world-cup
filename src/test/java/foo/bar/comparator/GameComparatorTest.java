package foo.bar.comparator;


import foo.bar.model.Game;
import foo.bar.model.Match;
import foo.bar.model.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameComparatorTest {

    GameComparator gameComparator;

    ScoreComparator scoreComparator;

    MatchComparator matchComparator;

    Game game1;

    Game game2;

    Score score1;

    Score score2;

    Match match1;

    Match match2;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        gameComparator = new GameComparator();
        scoreComparator = mock(ScoreComparator.class);
        matchComparator = mock(MatchComparator.class);
        game1 = mock(Game.class);
        game2 = mock(Game.class);
        Field scoreComparatorField = gameComparator.getClass().getDeclaredField("scoreComparator");
        Field matchComparatorField = gameComparator.getClass().getDeclaredField("matchComparator");
        scoreComparatorField.setAccessible(true);
        matchComparatorField.setAccessible(true);
        scoreComparatorField.set(gameComparator, scoreComparator);
        matchComparatorField.set(gameComparator, matchComparator);
        lenient().when(game1.getMatch()).thenReturn(match1);
        lenient().when(game2.getMatch()).thenReturn(match2);
        when(game1.getScore()).thenReturn(score1);
        when(game2.getScore()).thenReturn(score2);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1, 1",
            "1, 0, 1",
            "1, -1, 1",
            "0, 1, 1",
            "0, 0, 0",
            "0, -1, -1",
            "-1, 1, -1",
            "-1, 0, -1",
            "-1, -1, -1",
    })
    void compare(int score, int match, int game) {
        when(scoreComparator.compare(score1, score2)).thenReturn(score);
        lenient().when(matchComparator.compare(match1, match2)).thenReturn(match);
        assertEquals(game, gameComparator.compare(game1, game2));
    }
}