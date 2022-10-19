package foo.bar;

import foo.bar.exception.BusyTeamException;
import foo.bar.exception.MatchNotFoundException;
import foo.bar.model.Match;
import foo.bar.model.Score;
import foo.bar.model.Summary;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents the score board for the world cup.
 * It offers some CRUD-like operations for the games.
 */
@Getter
public class Board {

    private final Map<Match, Score> games = new HashMap<>();

    private final Set<String> playingTeams = new HashSet<>();

    /**
     * Starts a game between homeTeam and awayTeam with and initial score of 0 - 0.
     * @param homeTeam the home team.
     * @param awayTeam the away team.
     * @return a Match representing the game just started.
     */
    public Match startGame(String homeTeam, String awayTeam) {
        Match match = new Match(homeTeam, awayTeam);
        if (playingTeams.contains(homeTeam) || playingTeams.contains(awayTeam)) {
            throw new BusyTeamException(
                    String.format("Cannot start a game between %s and %s. At least one of the teams is already playing.", homeTeam, awayTeam));
        } else {
            playingTeams.add(homeTeam);
            playingTeams.add(awayTeam);
            games.put(match, new Score(0, 0));
        }
        return match;
    }

    /**
     * Finishes the game for the specified match. It will be removed from the board.
     * @param match the match that will finish.
     */
    public void finishGame(Match match) {
        if (games.containsKey(match)) {
            games.remove(match);
            playingTeams.remove(match.getHomeTeam());
            playingTeams.remove(match.getAwayTeam());
        } else {
            throw new MatchNotFoundException(
                    String.format("Cannot finish a game for %s and %s. There is no ongoing game between them.", match.getHomeTeam(), match.getAwayTeam()));
        }
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
