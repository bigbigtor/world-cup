package foo.bar.comparator;

import foo.bar.model.Score;

import java.util.Comparator;

/**
 * Implements a comparator for {@link Score}
 */
public class ScoreComparator implements Comparator<Score> {

    @Override
    public int compare(Score o1, Score o2) {
        Integer sum1 = o1.getHomeTeamScore() + o1.getAwayTeamScore();
        Integer sum2 = o2.getHomeTeamScore() + o2.getAwayTeamScore();
        return sum1.compareTo(sum2);
    }
}
