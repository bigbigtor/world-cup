package foo.bar;

import foo.bar.exception.BusyTeamException;
import foo.bar.exception.MatchNotFoundException;
import foo.bar.model.Match;
import foo.bar.model.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


class BoardTest {

    private static final String TEAM_1 = "team1";
    private static final String TEAM_2 = "team2";
    private static final String TEAM_3 = "team3";
    private static final String TEAM_4 = "team4";
    private static final String TEAM_5 = "team5";
    private static final String TEAM_6 = "team6";
    Board board;
    @BeforeEach
    void setUp() {
        board = new Board();
        board.getGames().put(new Match(TEAM_1, TEAM_2), new Score(0, 1));
        board.getGames().put(new Match(TEAM_3, TEAM_4), new Score(2, 3));
        board.getPlayingTeams().add(TEAM_1);
        board.getPlayingTeams().add(TEAM_2);
        board.getPlayingTeams().add(TEAM_3);
        board.getPlayingTeams().add(TEAM_4);
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
        Match expectedMatch = new Match(TEAM_5, TEAM_6);
        assertEquals(expectedMatch, board.startGame(TEAM_5, TEAM_6));
        assertTrue(board.getGames().containsKey(expectedMatch));
        assertTrue(board.getPlayingTeams().contains(TEAM_5));
        assertTrue(board.getPlayingTeams().contains(TEAM_6));
    }

    @Test
    void finishGameFailsIfMatchIsNotFound() {
        Match match56 = new Match(TEAM_5, TEAM_6);
        Match match15 = new Match(TEAM_1, TEAM_5);
        Exception e = assertThrows(MatchNotFoundException.class, () -> board.finishGame(match56));
        assertEquals("Cannot finish a game for team5 and team6. There is no ongoing game between them.", e.getMessage());
        e = assertThrows(MatchNotFoundException.class, () -> board.finishGame(match15));
        assertEquals("Cannot finish a game for team1 and team5. There is no ongoing game between them.", e.getMessage());
    }

    @Test
    void finishGameWorks() {
        Match match = new Match(TEAM_3, TEAM_4);
        board.finishGame(match);
        assertFalse(board.getGames().containsKey(match));
        assertFalse(board.getPlayingTeams().contains(TEAM_3));
        assertFalse(board.getPlayingTeams().contains(TEAM_4));
    }

    @Test
    void updateScoreFailsIfMatchIsNotFound() {
        Match match56 = new Match(TEAM_5, TEAM_6);
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
        Match match = new Match(TEAM_3, TEAM_4);
        Score score = new Score(4, 9);
        board.updateScore(match, score);
        assertEquals(score.getHomeTeamScore(), board.getGames().get(match).getHomeTeamScore());
        assertEquals(score.getAwayTeamScore(), board.getGames().get(match).getAwayTeamScore());
    }

    @Test
    void getSummary() {
        fail();
    }
}