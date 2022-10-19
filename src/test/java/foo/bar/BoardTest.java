package foo.bar;

import foo.bar.comparator.GameComparator;
import foo.bar.exception.BusyTeamException;
import foo.bar.exception.MatchNotFoundException;
import foo.bar.model.Game;
import foo.bar.model.Match;
import foo.bar.model.Score;
import foo.bar.model.Summary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BoardTest {

    private static final String TEAM_1 = "team1";
    private static final String TEAM_2 = "team2";
    private static final String TEAM_3 = "team3";
    private static final String TEAM_4 = "team4";
    private static final String TEAM_5 = "team5";
    private static final String TEAM_6 = "team6";
    Board board;

    GameComparator gameComparator;

    Match match12;
    Match match34;
    Match match56;

    Score score1;
    Score score2;
    Score score3;

    Game game1;
    Game game2;
    Game game3;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        board = new Board();
        match12 = new Match(TEAM_1, TEAM_2);
        match34 = new Match(TEAM_3, TEAM_4);
        match56 = new Match(TEAM_5, TEAM_6);

        score1 = new Score(0, 1);
        score2 = new Score(2, 3);
        score3 = new Score(5, 1);

        game1 = new Game(match12, score1);
        game2 = new Game(match34, score2);
        game3 = new Game(match56, score3);

        board.getGames().put(match12, score1);
        board.getGames().put(match34, score2);

        board.getPlayingTeams().add(TEAM_1);
        board.getPlayingTeams().add(TEAM_2);
        board.getPlayingTeams().add(TEAM_3);
        board.getPlayingTeams().add(TEAM_4);

        gameComparator = mock(GameComparator.class);
        Field gameComparatorField = board.getClass().getDeclaredField("gameComparator");
        gameComparatorField.setAccessible(true);
        gameComparatorField.set(board, gameComparator);
    }

    @Test
    void startGameFailsIfATeamIsBusy() {
        Exception e = assertThrows(BusyTeamException.class, () -> board.startGame(TEAM_1, TEAM_2));
        assertEquals("Cannot start a game between team1 and team2. At least one of the teams is already playing.", e.getMessage());
        e = assertThrows(BusyTeamException.class, () -> board.startGame(TEAM_1, TEAM_5));
        assertEquals("Cannot start a game between team1 and team5. At least one of the teams is already playing.", e.getMessage());
    }

    @Test
    void startGameWorks() {
        assertEquals(match56, board.startGame(TEAM_5, TEAM_6));
        assertTrue(board.getGames().containsKey(match56));
        assertTrue(board.getPlayingTeams().contains(TEAM_5));
        assertTrue(board.getPlayingTeams().contains(TEAM_6));
    }

    @Test
    void finishGameFailsIfMatchIsNotFound() {
        Match match15 = new Match(TEAM_1, TEAM_5);
        Exception e = assertThrows(MatchNotFoundException.class, () -> board.finishGame(match56));
        assertEquals("Cannot finish a game for team5 and team6. There is no ongoing game between them.", e.getMessage());
        e = assertThrows(MatchNotFoundException.class, () -> board.finishGame(match15));
        assertEquals("Cannot finish a game for team1 and team5. There is no ongoing game between them.", e.getMessage());
    }

    @Test
    void finishGameWorks() {
        board.finishGame(match34);
        assertFalse(board.getGames().containsKey(match34));
        assertFalse(board.getPlayingTeams().contains(TEAM_3));
        assertFalse(board.getPlayingTeams().contains(TEAM_4));
    }

    @Test
    void updateScoreFailsIfMatchIsNotFound() {
        Match match15 = new Match(TEAM_1, TEAM_5);
        Score score1 = new Score(10, 5);
        Score score2 = new Score(4, 7);
        Exception e = assertThrows(MatchNotFoundException.class, () -> board.updateScore(match56, score1));
        assertEquals("Cannot update the score for team5 and team6. There is no ongoing game between them.", e.getMessage());
        e = assertThrows(MatchNotFoundException.class, () -> board.updateScore(match15, score2));
        assertEquals("Cannot update the score for team1 and team5. There is no ongoing game between them.", e.getMessage());
    }

    @Test
    void updateScoreWorks() {
        Score score = new Score(4, 9);
        board.updateScore(match34, score);
        assertEquals(score.getHomeTeamScore(), board.getGames().get(match34).getHomeTeamScore());
        assertEquals(score.getAwayTeamScore(), board.getGames().get(match34).getAwayTeamScore());
    }

    @Test
    void getSummary() {
        board.startGame(match56.getHomeTeam(), match56.getAwayTeam());
        board.updateScore(match56, score3);

        when(gameComparator.compare(game1, game2)).thenReturn(1);
        when(gameComparator.compare(game2, game1)).thenReturn(-1);
        when(gameComparator.compare(game2, game3)).thenReturn(1);
        when(gameComparator.compare(game3, game2)).thenReturn(-1);
        when(gameComparator.compare(game1, game3)).thenReturn(1);
        when(gameComparator.compare(game3, game1)).thenReturn(-1);

        Summary summary = board.getSummary();
        assertEquals(List.of(game3, game2, game1), summary.getGames());

        when(gameComparator.compare(game1, game2)).thenReturn(1);
        when(gameComparator.compare(game2, game1)).thenReturn(-1);
        when(gameComparator.compare(game2, game3)).thenReturn(-1);
        when(gameComparator.compare(game3, game2)).thenReturn(1);
        when(gameComparator.compare(game1, game3)).thenReturn(-1);
        when(gameComparator.compare(game3, game1)).thenReturn(1);

        summary = board.getSummary();
        assertEquals(List.of(game2, game1, game3), summary.getGames());

        lenient().when(gameComparator.compare(game1, game2)).thenReturn(-1);
        when(gameComparator.compare(game2, game1)).thenReturn(1);
        when(gameComparator.compare(game2, game3)).thenReturn(-1);
        lenient().when(gameComparator.compare(game3, game2)).thenReturn(1);
        lenient().when(gameComparator.compare(game1, game3)).thenReturn(-1);
        when(gameComparator.compare(game3, game1)).thenReturn(1);

        summary = board.getSummary();
        assertEquals(List.of(game1, game2, game3), summary.getGames());
    }
}