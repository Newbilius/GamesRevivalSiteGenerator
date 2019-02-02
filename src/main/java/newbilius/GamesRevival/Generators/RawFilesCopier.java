package newbilius.GamesRevival.Generators;

import newbilius.GamesRevival.FileHelper;
import newbilius.GamesRevival.FoldersConfig;
import newbilius.GamesRevival.GeneratorsInfastructure.BasePagesGenerator;

import java.io.IOException;

@SuppressWarnings("unused")
public class RawFilesCopier extends BasePagesGenerator {

    public RawFilesCopier(FoldersConfig foldersConfig) {
        super(foldersConfig);
    }

    @Override
    protected void generateData() throws IOException {
        var rawFolder = foldersConfig.getTemplateFolder() + "RAW";
        String[] gameDirs = FileHelper.getDirsList(rawFolder);

        for (var dir : gameDirs) {
            var filePath = rawFolder + "/" + dir;
            var files = FileHelper.getFilesList(filePath);
            for (var file : files) {
                var outputFolder = foldersConfig.getOutputFolder();
                FileHelper.createFolder(outputFolder + dir);
                FileHelper.copyFile(filePath + "/" + file,
                        outputFolder + dir + "/" + file);
            }
        }
    }
}