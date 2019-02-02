package newbilius.GamesRevival.Generators;

import newbilius.GamesRevival.FoldersConfig;
import newbilius.GamesRevival.GeneratorsInfastructure.BaseOnePageGenerator;
import newbilius.GamesRevival.GeneratorsInfastructure.RelativePathHelper;
import newbilius.GamesRevival.HTML.Breadcrumbs.MainBreadcrumbGenerator;

import java.io.IOException;

@SuppressWarnings("unused")
public class ShortTextsPageGenerator extends BaseOnePageGenerator {

    public ShortTextsPageGenerator(FoldersConfig foldersConfig) {
        super(foldersConfig);
    }

    @Override
    protected String getFileName() {
        return "help_no_texts.html";
    }

    @Override
    protected String getJSFileName() {
        return "no_data_js.html";
    }

    @Override
    protected String getContent() throws IOException {
        var stringBuilder = new StringBuilder();

        for (var game : Games) {
            for (var port : game.Ports) {
                if (port.About.length() < 140) {
                    stringBuilder.append("<tr class=\"game_line\">");

                    stringBuilder.append(String.format("<td><a href=%s>%s</a></td>",
                            RelativePathHelper.getPath(port),
                            port.Title));

                    stringBuilder.append(String.format("<td><a href=%s>%s</a></td>",
                            RelativePathHelper.getPath(game),
                            game.getTitle()));

                    stringBuilder.append("</tr>\r\n");
                }
            }
        }

        var breadcrumbGenerator = new MainBreadcrumbGenerator();
        breadcrumbGenerator.add("Моды со слишком коротким описанием");

        return getTemplateFileContent("no_data.html")
                .replace("#PAGE_TOP#", breadcrumbGenerator.generate())
                .replace("#TABLE#", stringBuilder.toString());
    }

    @Override
    protected String getTitle() {
        return "Games Revival - моды со слишком коротким описанием";
    }
}
