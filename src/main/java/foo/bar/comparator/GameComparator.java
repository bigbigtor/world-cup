package foo.bar.comparator;

import foo.bar.model.Game;

import java.util.Comparator;

/**
 * Implements a comparator for {@link Game}
 */
public class GameComparator implements Comparator<Game> {

    private final ScoreComparator scoreComparator = new ScoreComparator();

    private final MatchComparator matchComparator = new MatchComparator();

    @Override
    public int compare(Game o1, Game o2) {
        return Comparator.comparing(Game::getScore, scoreComparator)
                .thenComparing(Game::getMatch, matchComparator)
                .compare(o1, o2);
    }
}
