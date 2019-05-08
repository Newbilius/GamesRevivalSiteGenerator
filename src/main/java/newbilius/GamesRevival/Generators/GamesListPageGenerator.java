package newbilius.GamesRevival.Generators;

import newbilius.GamesRevival.Data.DataHelpers;
import newbilius.GamesRevival.Data.Game;
import newbilius.GamesRevival.FoldersConfig;
import newbilius.GamesRevival.GeneratorsInfastructure.BaseOnePageGenerator;
import newbilius.GamesRevival.GeneratorsInfastructure.RelativePathHelper;
import newbilius.GamesRevival.HTML.Breadcrumbs.MainBreadcrumbGenerator;

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
            builder.append(String.format("<tr class=\"game_line\">" +
                            "<td class='game_image_block' style=\"width:180px; max-width: 180px; display: none;\">" +
                            "<a href=%s>" +
                            "<img src=%s width=150>" +
                            "</a>" +
                            "</td>" +
                            "<td>" +
                            "<a href=%s><h5>%s</h5></a>" +
                            "</td>" +
                            "<td>" +
                            "<h5>%s</h5>" +
                            "" +
                            "</td>" +
                            "</tr>",
                    RelativePathHelper.getPath(game),
                    getGameCoverUrl(game),
                    RelativePathHelper.getPath(game),
                    game.getTitle(),
                    game.getGenres()
                    )
            );
        }

        var breadcrumbGenerator = new MainBreadcrumbGenerator();
        breadcrumbGenerator.add("Список игр");

        return getTemplateFileContent("games_list.html")
                .replace("#PAGE_TOP#", breadcrumbGenerator.generate())
                .replace("#TABLE#", builder.toString())
                .replace("#FILTER#", generateFilter());
    }

    private String generateFilter() {
        var builder = new StringBuilder();


        var dataHelpers = new DataHelpers(Games);
        var genresList = dataHelpers.getGenres();

        int counter = 0;
        for (var genre : genresList) {
            counter++;
            builder.append(filterItemTemplate
                    .replaceAll("#NUMBER#", String.valueOf(counter))
                    .replaceAll("#VALUE#", genre));
        }


        return builder.toString();
    }

    private String filterItemTemplate = "<div class='custom-control custom-checkbox custom-control-inline'>\n" +
            "  <input type='checkbox' class='custom-control-input' id='customCheck#NUMBER#' value='#VALUE#'>\n" +
            "  <label class='custom-control-label' for='customCheck#NUMBER#'>#VALUE#</label>\n" +
            "</div>";

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
