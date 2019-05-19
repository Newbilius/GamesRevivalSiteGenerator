package newbilius.GamesRevival;

import newbilius.GamesRevival.Data.Game;
import newbilius.GamesRevival.Data.Port;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class DataLoader {

    private FoldersConfig foldersConfig;

    public DataLoader(FoldersConfig foldersConfig) {
        this.foldersConfig = foldersConfig;
    }

    List<Game> Execute() throws IOException {
        String[] gameDirs = FileHelper.getDirsList(foldersConfig.getInputFolder());
        List<Game> Games = new ArrayList<>();

        for (var gameDir : gameDirs) {
            var game = GetGameData(gameDir);

            var gameFolder = foldersConfig.getInputFolder() + gameDir;
            Helpers.print("load data from GAME folder " + gameFolder);
            var portsDirs = FileHelper.getDirsList(gameFolder);

            for (var portDir : portsDirs) {
                var port = GetGamePort(game, portDir);
                game.Ports.add(port);
            }

            Games.add(game);
        }

        var gamesWithEmptyTitle = Games.stream()
                .filter(x -> x.Title == null || x.Title.length == 0)
                .toArray(Game[]::new);

        for (var gameWithEmptyTitle :
                gamesWithEmptyTitle) {
            Helpers.print("!!!!! ERROR! GAME FOLDER WITH EMPTY TITLE " + gameWithEmptyTitle.Path);
            Games.remove(gameWithEmptyTitle);
        }

        for (var game : Games)
            game.Ports.sort(Comparator.comparing(o -> o.Title));
        Games.sort(Comparator.comparing(o -> o.Title[0]));

        return Games;
    }

    private Port GetGamePort(Game game, String portDir) throws IOException {
        var port = new Port();
        var portFullDir = foldersConfig.getInputFolder() + game.Path + "/" + portDir;
        port.Path = game.Path + "/" + portDir;
        port.About = FileHelper.getFileText(portFullDir + "/about.md");
        port.Files = FileHelper.getFileText(portFullDir + "/links.md");
        port.Link = FileHelper.getFileText(portFullDir + "/link.txt");
        port.Title = FileHelper.getFileText(portFullDir + "/title.txt");
        port.Video = FileHelper.getFileLines(portFullDir + "/video.txt");
        port.Tags = FileHelper.getFileLines(portFullDir + "/tags.txt");
        port.OS = FileHelper.getFileLines(portFullDir + "/os.txt");
        port.Redirect = FileHelper.getFileText(portFullDir + "/redirect.txt");
        port.ImagesPath = new String[0];

        port.Video = Helpers.removeEmptyLines(port.Video);

        for (int i = 0; i < port.Tags.length; i++) {
            port.Tags[i] = port.Tags[i].strip().toLowerCase();
        }

        for (int i = 0; i < port.OS.length; i++) {
            var os = port.OS[i].toLowerCase().strip();
            if (os.equals("windows"))
                port.OS[i] = "Windows";
            if (os.equals("linux"))
                port.OS[i] = "Linux";
            if (os.equals("android"))
                port.OS[i] = "Android";
            if (os.equals("ios"))
                port.OS[i] = "iOS";
            if (os.equals("ms-dos"))
                port.OS[i] = "MS-DOS";
            if (os.contains("mac")
                    && os.contains("os")
                    && os.contains("x"))
                port.OS[i] = "Mac OS X";
        }

        var screensFolder = portFullDir + "/";
        var images = Arrays.stream(FileHelper.getFilesList(screensFolder))
                .filter(image -> image.endsWith(".jpg"))
                .sorted()
                .toArray(String[]::new);
        if (images.length > 0)
            port.ImagesPath = Arrays
                    .stream(images)
                    .map(value -> screensFolder + value)
                    .toArray(String[]::new);

        return port;
    }

    private Game GetGameData(String gameDir) throws IOException {
        var game = new Game();
        var gameFullDir = foldersConfig.getInputFolder() + "/" + gameDir;
        game.Path = gameDir;
        game.Links = FileHelper.getFileText(gameFullDir + "/links.md");
        game.About = FileHelper.getFileText(gameFullDir + "/about.md");
        game.OldGames = FileHelper.getFileLines(gameFullDir + "/old_games.txt");
        game.Title = FileHelper.getFileLines(gameFullDir + "/title.txt");
        game.Genre = FileHelper.getFileLines(gameFullDir + "/genre.txt");
        game.LogoPath = FileHelper.getPathIfFileExists(gameFullDir + "/logo.jpg");
        game.Ports = new ArrayList<>(1);
        return game;
    }
}