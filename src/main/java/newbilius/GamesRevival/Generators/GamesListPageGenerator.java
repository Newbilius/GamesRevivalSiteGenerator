package newbilius.GamesRevival.Generators;

import newbilius.GamesRevival.FileHelper;
import newbilius.GamesRevival.FoldersConfig;
import newbilius.GamesRevival.GeneratorsInfastructure.BaseOnePageGenerator;
import newbilius.GamesRevival.GeneratorsInfastructure.RelativePathHelper;
import newbilius.GamesRevival.HTML.Breadcrumbs.MainBreadcrumbGenerator;
import newbilius.GamesRevival.HTML.MDToHtmlConverter;

import java.io.IOException;

@SuppressWarnings("unused")
public class GamesListPageGenerator extends BaseOnePageGenerator {

    public GamesListPageGenerator(FoldersConfig foldersConfig) {
        super(foldersConfig);
    }

    @Override
    protected String getFileName() {
        return "games.html";
    }

    @Override
    protected String getJSFileName() {
        return "games_list_js.html";
    }

    @Override
    protected String getContent() throws IOException {
        var builder = new StringBuilder();
        for (var game : Games) {
            builder.append(String.format("<tr class=\"game_line\"><td>" +
                            "<a href=%s>%s</a></td>" +
                            "<td>%s</td></tr>",
                    RelativePathHelper.getPath(game),
                    game.getTitle(),
                    game.Ports.size()));
        }

        var breadcrumbGenerator = new MainBreadcrumbGenerator();
        breadcrumbGenerator.add("Список игр");

        return getTemplateFileContent("games_list.html")
                .replace("#PAGE_TOP#", breadcrumbGenerator.generate())
                .replace("#TABLE#", builder.toString());
    }

    @Override
    protected String getTitle() {
        return "Games Revival - список игр";
    }
}
