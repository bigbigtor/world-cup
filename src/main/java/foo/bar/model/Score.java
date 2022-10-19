package foo.bar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Score {

    private int homeTeamScore;

    private int awayTeamScore;
}
