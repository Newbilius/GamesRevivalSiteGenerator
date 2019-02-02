package newbilius.GamesRevival.GeneratorsInfastructure;

import newbilius.GamesRevival.Data.Game;
import newbilius.GamesRevival.FileHelper;
import newbilius.GamesRevival.FoldersConfig;
import newbilius.GamesRevival.Helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public abstract class BasePagesGenerator implements ISiteGenerator {
    protected String template;
    protected List<Game> Games;
    protected FoldersConfig foldersConfig;

    public BasePagesGenerator(FoldersConfig foldersConfig) {
        this.foldersConfig = foldersConfig;
    }

    @Override
    public void generate(List<Game> games) throws IOException {
        FileHelper.createFolder(foldersConfig.getOutputFolder());

        Games = games;
        var templateFilePath = foldersConfig.getTemplateFolder() + "template.html";
        template = FileHelper.getFileText(templateFilePath);
        if (template.isBlank()) {
            Helpers.print("TEMPLATE NOT FOUND! " + templateFilePath);
            System.exit(1);
        }
        generateData();
    }

    protected abstract void generateData() throws IOException;

    protected String setTitle(String text, String title) {
        return text.replaceFirst("#TITLE#", title);
    }

    protected String setContent(String text, String content) {
        return text.replaceFirst("#CONTENT#", content.replaceAll("\\$", "\\\\\\$"));
    }

    protected String setJS(String text, String js) {
        return text.replaceFirst("#JS#", js.replaceAll("\\$", "\\\\\\$"));
    }

    protected String getTemplateFileContent(String file) throws IOException {
        return FileHelper.getFileText(getFileFromTemplateFolderFullName(file));
    }

    private String getFileFromTemplateFolderFullName(String file) throws IOException {
        var fullFileName = foldersConfig.getTemplateFolder() + file;
        if (!Files.exists(Paths.get(fullFileName)))
            throw new IOException("File not found " + file);

        return fullFileName;
    }
}
