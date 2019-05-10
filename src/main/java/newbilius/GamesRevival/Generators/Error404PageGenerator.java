package newbilius.GamesRevival.Generators;

import newbilius.GamesRevival.FileHelper;
import newbilius.GamesRevival.FoldersConfig;
import newbilius.GamesRevival.GeneratorsInfrastructure.BaseOnePageGenerator;
import newbilius.GamesRevival.HTML.MDToHtmlConverter;

import java.io.IOException;

@SuppressWarnings("unused")
public class Error404PageGenerator extends BaseOnePageGenerator {

    public Error404PageGenerator(FoldersConfig foldersConfig) {
        super(foldersConfig);
    }

    @Override
    protected String getFileName() {
        return "error404.html";
    }

    @Override
    protected String getJSFileName() {
        return "";
    }

    @Override
    protected String getContent() throws IOException {
        var builder = new StringBuilder();

        var mdToHtmlConverter = new MDToHtmlConverter();
        var faqData = FileHelper.getFileText(foldersConfig.getInputFolder() + "error404.md");
        var text = mdToHtmlConverter.convert(faqData);
        builder.append(text);
        return builder.toString();
    }

    @Override
    protected String getTitle() {
        return "Games Revival - 404";
    }
}