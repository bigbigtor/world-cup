package foo.bar.comparator;

import foo.bar.model.Match;

import java.util.Comparator;

/**
 * Implements a comparator for {@link Match}
 */
public class MatchComparator implements Comparator<Match> {

    @Override
    public int compare(Match o1, Match o2) {
        return o1.getStartTime().compareTo(o2.getStartTime());
    }
}
