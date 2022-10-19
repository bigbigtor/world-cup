package foo.bar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Game {

    private Match match;

    private Score score;

}
