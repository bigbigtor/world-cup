package foo.bar.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
public class Match {

    private final String homeTeam;

    private final String awayTeam;

    @EqualsAndHashCode.Exclude
    private final LocalDateTime startTime;

    public Match(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        startTime = LocalDateTime.now();
    }
}
