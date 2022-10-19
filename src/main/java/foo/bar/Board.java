package foo.bar;

import foo.bar.model.Match;
import foo.bar.model.Score;
import foo.bar.model.Summary;

/**
 * Represents the score board for the world cup.
 * It offers some CRUD-like operations for the games.
 */
public class Board {

    /**
     * Starts a game between homeTeam and awayTeam with and initial score of 0 - 0.
     * @param homeTeam the home team.
     * @param awayTeam the away team.
     * @return a Match representing the game just started.
     */
    public Match startGame(String homeTeam, String awayTeam) {
        //TODO: implement
        return null;
    }

    /**
     * Finishes the game for the specified match. It will be removed from the board.
     * @param match the match that will finish.
     */
    public void finishGame(Match match) {
        //TODO: implement
    }

    /**
     * Updates the score for the game of the specified match.
     * @param match the match to update.
     * @param score the new score.
     */
    public void updateScore(Match match, Score score) {
        //TODO: implement
    }

    /**
     * Returns a summary of games ordered by total score. In case of tie, most recently added game will go first.
     * @return the games' summary
     */
    public Summary getSummary() {
        //TODO: implement
        return null;
    }
}
