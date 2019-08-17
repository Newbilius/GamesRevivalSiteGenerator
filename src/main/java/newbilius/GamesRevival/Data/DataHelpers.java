package newbilius.GamesRevival.Data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class DataHelpers {
    private final List<Game> Games;

    public DataHelpers(List<Game> games) {
        Games = games;
    }

    public String[] getTags() {
        var firstTags = new String[]{"sourceport", "официальный remaster", "mod"};
        return Stream.concat(Arrays.stream(firstTags),
                Games
                        .stream()
                        .flatMap(game -> game.Ports.stream())
                        .flatMap(port -> Arrays.stream(port.Tags).sorted()))
                .distinct()
                .toArray(String[]::new);
    }

    public String[] getOS() {
        var firstOS = new String[]{"Windows", "Linux", "Mac OS X", "Android", "iOS", "MS-DOS"};
        return Stream.concat(Arrays.stream(firstOS),
                Games
                        .stream()
                        .flatMap(game -> game.Ports.stream())
                        .flatMap(port -> Arrays.stream(port.OS).sorted()))
                .distinct()
                .toArray(String[]::new);
    }

    public String[] getGenres() {
        return Games
                .stream()
                .flatMap(game -> Arrays.stream(game.Genre))
                .distinct()
                .sorted()
                .toArray(String[]::new);
    }
}