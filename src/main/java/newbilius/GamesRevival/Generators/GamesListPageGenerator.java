package newbilius.GamesRevival.Generators;

import newbilius.GamesRevival.Data.Game;
import newbilius.GamesRevival.Data.Port;
import newbilius.GamesRevival.FoldersConfig;
import newbilius.GamesRevival.GeneratorsInfastructure.BaseOnePageGenerator;
import newbilius.GamesRevival.GeneratorsInfastructure.RelativePathHelper;
import newbilius.GamesRevival.HTML.Breadcrumbs.MainBreadcrumbGenerator;

import java.io.IOException;
import java.util.List;

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
            builder.append(String.format("<tr class=\"game_line\">" +
                                "<td class='game_image_block' style=\"width:180px; max-width: 180px;\">" +
                                    "<a href=%s>" +
                                        "<img src=%s width=150>" +
                                    "</a>" +
                                "</td>" +
                                "<td>" +
                                        "<a href=%s><h5>%s</h5></a>" +
                                "</td>" +
                            "</tr>",
                    RelativePathHelper.getPath(game),
                    getGameCoverUrl(game),
                    RelativePathHelper.getPath(game),
                    game.getTitle()
                    )
            );
        }

        var breadcrumbGenerator = new MainBreadcrumbGenerator();
        breadcrumbGenerator.add("Список игр");

        return getTemplateFileContent("games_list.html")
                .replace("#PAGE_TOP#", breadcrumbGenerator.generate())
                .replace("#TABLE#", builder.toString());
    }

    private String getGameCoverUrl(Game game) {
        if (game.LogoPath.isBlank())
            return "/img/none_image.jpg";

        //todo "logo.jpg" дублируется в двух местах, поправить
        return "/" + RelativePathHelper.getPath(game) + "logo.jpg";
    }

    @Override
    protected String getTitle() {
        return "Games Revival - список игр";
    }
}
