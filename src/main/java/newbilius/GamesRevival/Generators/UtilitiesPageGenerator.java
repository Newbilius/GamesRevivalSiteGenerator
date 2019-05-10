package newbilius.GamesRevival.Generators;

import newbilius.GamesRevival.FileHelper;
import newbilius.GamesRevival.FoldersConfig;
import newbilius.GamesRevival.GeneratorsInfrastructure.BaseOnePageGenerator;
import newbilius.GamesRevival.HTML.Breadcrumbs.MainBreadcrumbGenerator;
import newbilius.GamesRevival.HTML.EditMenuGenerator;
import newbilius.GamesRevival.HTML.MDToHtmlConverter;

import java.io.IOException;

@SuppressWarnings("unused")
public class UtilitiesPageGenerator extends BaseOnePageGenerator {

    public UtilitiesPageGenerator(FoldersConfig foldersConfig) {
        super(foldersConfig);
    }

    protected String getFileName() {
        return "utilities.html";
    }

    @Override
    protected String getJSFileName() {
        return "";
    }

    @Override
    protected String getContent() throws IOException {
        var builder = new StringBuilder();

        var breadcrumbGenerator = new MainBreadcrumbGenerator();
        breadcrumbGenerator.add("Утилиты");
        builder.append(breadcrumbGenerator.generate());

        var mdToHtmlConverter = new MDToHtmlConverter();
        var faqData = FileHelper.getFileText(foldersConfig.getInputFolder() + "utilities.md");
        var text = mdToHtmlConverter.convert(faqData);
        builder.append(text);


        builder.append(EditMenuGenerator.getBlock("utilities.md"));
        return builder.toString();
    }

    @Override
    protected String getTitle() {
        return "Games Revival - Утилиты";
    }
}
