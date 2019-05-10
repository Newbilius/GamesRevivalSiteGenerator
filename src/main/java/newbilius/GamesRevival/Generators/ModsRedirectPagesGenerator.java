package newbilius.GamesRevival.Generators;

import newbilius.GamesRevival.FileHelper;
import newbilius.GamesRevival.FoldersConfig;
import newbilius.GamesRevival.GeneratorsInfrastructure.BasePagesGenerator;
import newbilius.GamesRevival.GeneratorsInfrastructure.RelativePathHelper;

import java.io.IOException;

@SuppressWarnings("unused")
public class ModsRedirectPagesGenerator extends BasePagesGenerator {

    public ModsRedirectPagesGenerator(FoldersConfig foldersConfig) {
        super(foldersConfig);
    }

    @Override
    protected void generateData() throws IOException {
        for (var game : Games) {
            for (var port : game.Ports) {
                if (!port.Redirect.isBlank()) {
                    var fileDir = foldersConfig.getOutputFolder() + "mods/" + port.Redirect;
                    FileHelper.createFolder(fileDir);

                    var filePath = fileDir + "/index.php";
                    var newPath = "/" + RelativePathHelper.getPath(port);
                    var text = String.format("<?php\n" +
                                    "header('HTTP/1.1 301 Moved Permanently');\n" +
                                    "header('Location: %s');\n" +
                                    "?>",
                            newPath);
                    FileHelper.writeStringToFile(filePath, text);
                }
            }
        }
    }
}
