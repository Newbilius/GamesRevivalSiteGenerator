package newbilius.GamesRevival.Generators;

import com.google.gson.Gson;
import newbilius.GamesRevival.Data.Game;
import newbilius.GamesRevival.Data.Port;
import newbilius.GamesRevival.FileHelper;
import newbilius.GamesRevival.FoldersConfig;
import newbilius.GamesRevival.GeneratorsInfrastructure.BasePagesGenerator;
import newbilius.GamesRevival.GeneratorsInfrastructure.RelativePathHelper;
import newbilius.GamesRevival.HTML.PortScreensHTMLGenerator;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class RandomPortsJsonGenerator extends BasePagesGenerator {

    public RandomPortsJsonGenerator(FoldersConfig foldersConfig) {
        super(foldersConfig);
    }

    @Override
    protected void generateData() throws IOException {
        var ports = Games
                .stream()
                .flatMap(this::CreatePortWithGame)
                .filter(portWithGame -> portWithGame.Port.ImagesPath.length > 0)
                .collect(Collectors.toList());

        Collections.shuffle(ports, new Random());

        var randomPorts = ports.stream()
                .limit(300)
                .map(portWithGame -> new RandomPortsJsonGenerator.RandomGame(portWithGame.Game.getTitle(),
                        "/" + RelativePathHelper.getPath(portWithGame.Port),
                        PortScreensHTMLGenerator.getImageUrl(portWithGame.Port, portWithGame.Port.ImagesPath[0])
                ))
                .toArray(RandomGame[]::new);

        var json = new Gson().toJson(randomPorts);
        FileHelper.writeStringToFile(foldersConfig.getOutputFolder() + "random_ports.json", json);
    }

    private Stream<PortWithGame> CreatePortWithGame(Game game) {
        return game
                .Ports
                .stream()
                .map(port -> new PortWithGame(game, port));
    }

    class PortWithGame {
        Game Game;
        newbilius.GamesRevival.Data.Port Port;

        PortWithGame(newbilius.GamesRevival.Data.Game game, Port port) {
            Game = game;
            Port = port;
        }
    }

    private class RandomGame {
        String Title;
        String Url;
        String ImgLink;

        RandomGame(String title,
                   String url,
                   String imageLink) {
            Title = title;
            Url = url;
            ImgLink = imageLink;
        }
    }
}