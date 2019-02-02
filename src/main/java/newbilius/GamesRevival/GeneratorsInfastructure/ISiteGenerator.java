package newbilius.GamesRevival.GeneratorsInfastructure;

import newbilius.GamesRevival.Data.Game;

import java.io.IOException;
import java.util.List;

public interface ISiteGenerator{
    void generate(List<Game> games) throws IOException;
}
