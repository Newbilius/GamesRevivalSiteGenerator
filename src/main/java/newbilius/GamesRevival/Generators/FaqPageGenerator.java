package newbilius.GamesRevival.Generators;

import newbilius.GamesRevival.FileHelper;
import newbilius.GamesRevival.FoldersConfig;
import newbilius.GamesRevival.GeneratorsInfrastructure.BaseOnePageGenerator;
import newbilius.GamesRevival.HTML.EditMenuGenerator;
import newbilius.GamesRevival.HTML.MDToHtmlConverter;
import newbilius.GamesRevival.HTML.Breadcrumbs.MainBreadcrumbGenerator;

import java.io.IOException;

@SuppressWarnings("unused")
public class FaqPageGenerator extends BaseOnePageGenerator {

    public FaqPageGenerator(FoldersConfig foldersConfig) {
        super(foldersConfig);
    }

    @Override
    protected String getFileName() {
        return "faq.html";
    }

    @Override
    protected String getJSFileName() {
        return "";
    }

    @Override
    protected String getContent() throws IOException {
        var builder = new StringBuilder();

        var breadcrumbGenerator = new MainBreadcrumbGenerator();
        breadcrumbGenerator.add("FAQ");
        builder.append(breadcrumbGenerator.generate());

        var mdToHtmlConverter = new MDToHtmlConverter();
        var faqData = FileHelper.getFileText(foldersConfig.getInputFolder() + "faq.md");
        var text = mdToHtmlConverter.convert(faqData);
        builder.append(text);

        builder.append(EditMenuGenerator.getBlock("faq.md"));

        return builder.toString();
    }

    @Override
    protected String getTitle() {
        return "Games Revival - FAQ";
    }
}