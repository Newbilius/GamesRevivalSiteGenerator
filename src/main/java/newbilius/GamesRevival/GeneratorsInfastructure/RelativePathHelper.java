package newbilius.GamesRevival.GeneratorsInfastructure;

import newbilius.GamesRevival.Data.Game;
import newbilius.GamesRevival.Data.Port;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class RelativePathHelper {
    private final static String gameFolderName = "games";
    private final static String editBaseUrl = "https://github.com/Newbilius/GamesRevival/tree/master/DATA/";

    public static String getPath(Game game) {
        return gameFolderName + "/" + game.Path + "/";
    }

    public static String getPath(Port port) {
        return gameFolderName + "/" + port.Path + "/";
    }

    public static String getEditUrl(Game game) {
        return editBaseUrl + game.Path;
    }

    public static String getAddModUrl(Game game) {
        return "/create.php?GameId=" + game.Path;
    }

    public static String getEditUrl(Port port) {
        return editBaseUrl + port.Path;
    }

    public static String getEditUrl(String path) {
        return editBaseUrl + path;
    }

    public static String getAddScreensUrl(Port port) {
        var pathPart = port.Path.split("/");
        return String.format("/upload.php?ModName=%s&GameId=%s&ModeId=%s",
                URLEncoder.encode(port.Title, StandardCharsets.UTF_8),
                pathPart[0],
                pathPart[1]);
    }
}