package newbilius.GamesRevival.Generators;

import com.google.gson.Gson;
import newbilius.GamesRevival.FileHelper;
import newbilius.GamesRevival.FoldersConfig;
import newbilius.GamesRevival.GeneratorsInfrastructure.BasePagesGenerator;
import newbilius.GamesRevival.GeneratorsInfrastructure.RelativePathHelper;

import java.io.IOException;
import java.util.HashMap;

@SuppressWarnings("unused")
public class OldGamesJsonGenerator extends BasePagesGenerator {

    private String baseUrl = "https://www.gamesrevival.ru/";

    public OldGamesJsonGenerator(FoldersConfig foldersConfig) {
        super(foldersConfig);
    }

    @Override
    protected void generateData() throws IOException {
        var data = new HashMap<String, OldGamesRuPort[]>();

        for (var game : Games) {
            if (game.OldGames.length > 0) {

                var ports = game.Ports
                        .stream()
                        .map(port -> new OldGamesRuPort(port.Title, baseUrl + RelativePathHelper.getPath(port)))
                        .toArray(OldGamesRuPort[]::new);

                for (var oldGameNum : game.OldGames)
                    data.put(oldGameNum, ports);
            }
        }

        var json = new Gson().toJson(data);
        FileHelper.writeStringToFile(foldersConfig.getOutputFolder() + "old_games_export.json", json);
    }

    private class OldGamesRuPort {
        String Name;
        String Url;

        OldGamesRuPort(String name, String url) {
            Name = name;
            Url = url;
        }
    }
}
