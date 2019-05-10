package newbilius.GamesRevival.Generators;

import com.google.gson.Gson;
import newbilius.GamesRevival.FileHelper;
import newbilius.GamesRevival.FoldersConfig;
import newbilius.GamesRevival.GeneratorsInfrastructure.BasePagesGenerator;
import newbilius.GamesRevival.GeneratorsInfrastructure.RelativePathHelper;
import newbilius.GamesRevival.HTML.PortScreensHTMLGenerator;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class RandomPortsJsonGenerator extends BasePagesGenerator {

    public RandomPortsJsonGenerator(FoldersConfig foldersConfig) {
        super(foldersConfig);
    }

    @Override
    protected void generateData() throws IOException {
        var ports = Games
                .stream()
                .flatMap(game -> game.Ports.stream())
                .filter(port -> port.ImagesPath.length > 0)
                .collect(Collectors.toList());
        Collections.shuffle(ports, new Random());

        var randomPorts = ports.stream()
                .limit(300)
                .map(port -> new RandomPortsJsonGenerator.RandomGame(port.Title,
                        "/" + RelativePathHelper.getPath(port),
                        PortScreensHTMLGenerator.getImageUrl(port, port.ImagesPath[0])
                ))
                .toArray(RandomGame[]::new);

        var json = new Gson().toJson(randomPorts);
        FileHelper.writeStringToFile(foldersConfig.getOutputFolder() + "random_ports.json", json);
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