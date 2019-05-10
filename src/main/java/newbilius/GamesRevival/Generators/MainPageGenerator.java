package newbilius.GamesRevival.Generators;

import newbilius.GamesRevival.Data.Game;
import newbilius.GamesRevival.FoldersConfig;
import newbilius.GamesRevival.GeneratorsInfrastructure.BaseOnePageGenerator;
import newbilius.GamesRevival.GeneratorsInfrastructure.RelativePathHelper;
import newbilius.GamesRevival.HTML.Badges.BadgeGenerator;
import newbilius.GamesRevival.HTML.Badges.BadgeType;
import newbilius.GamesRevival.Data.DataHelpers;
import newbilius.GamesRevival.Helpers;

import java.io.IOException;


@SuppressWarnings("unused")
public class MainPageGenerator extends BaseOnePageGenerator {
    public MainPageGenerator(FoldersConfig foldersConfig) {
        super(foldersConfig);
    }

    @Override
    protected String getFileName() {
        return "index.html";
    }

    @Override
    protected String getJSFileName() {
        return "main_js.html";
    }

    @Override
    protected String getContent() throws IOException {
        var data = getTemplateFileContent("main.html");

        data = data
                .replaceFirst("#FILTER_TAGS#", getHeaderFilterTags())
                .replaceFirst("#GAMES_COUNT#", getGamesCount())
                .replace("#GAMES_DATA#", getGamesData());

        return data;
    }

    @Override
    protected String getRightBlockContent() {
        return "";
    }

    private String getGamesData() {
        var stringBuilder = new StringBuilder();

        for (var game : Games) {
            stringBuilder.append(generateHeader(game));
            generatePort(game, stringBuilder);
            stringBuilder.append(generateFooter());
        }

        return stringBuilder.toString();
    }

    private String getGamesCount() {
        var games = Games.size();
        var portCount = Games
                .stream()
                .mapToLong(game -> game.Ports.size())
                .sum();
        return String.format("Всего на сайте <b>%d %s</b> и <b>%d %s</b>.",
                games,
                Helpers.plural(games, "игра", "игры", "игр"),
                portCount,
                Helpers.plural(portCount, "мод", "мода", "модов"))
                .replace(" ", "&nbsp;");
    }

    private String getHeaderFilterTags() {
        var builder = new StringBuilder();

        var dataHelpers = new DataHelpers(Games);

        var tagsList = dataHelpers.getTags();
        var osList = dataHelpers.getOS();

        int counter = 0;
        for (var os : osList) {
            counter++;
            builder.append(tagsFilterItemTemplate
                    .replaceAll("#NUMBER#", String.valueOf(counter))
                    .replaceAll("#VALUE#", os));
        }

        builder.append("<br>");

        for (var tag : tagsList) {
            counter++;
            builder.append(tagsFilterItemTemplate
                    .replaceAll("#NUMBER#", String.valueOf(counter))
                    .replaceAll("#VALUE#", tag));
        }
        return builder.toString();
    }

    private String tagsFilterItemTemplate = "<div class='custom-control custom-checkbox custom-control-inline'>\n" +
            "  <input type='checkbox' class='custom-control-input' id='customCheck#NUMBER#' value='#VALUE#'>\n" +
            "  <label class='custom-control-label' for='customCheck#NUMBER#'>#VALUE#</label>\n" +
            "</div>";

    private void generatePort(Game game, StringBuilder stringBuilder) {
        for (var port : game.Ports) {
            //название и ссылка на порт
            stringBuilder.append("<div class='port_block'>");

            stringBuilder.append(String.format("<h5><a href=%s>%s</a></h5>",
                    RelativePathHelper.getPath(port),
                    port.Title
            ));

            stringBuilder.append("<div class='tags_block'>");
            //операционки
            for (var os : port.OS) {
                if (!os.isBlank())
                    stringBuilder.append(BadgeGenerator.createBadge(os, BadgeType.Grey));
            }

            //тэги
            for (var tag : port.Tags)
                if (!tag.isBlank()) {
                    stringBuilder.append(BadgeGenerator.createBadge(tag));
                }

            stringBuilder.append("</div></div>");
        }
    }

    @Override
    protected String getTitle() {
        return "Games Revival - новая жизнь для старых игр";
    }

    private String generateFooter() {
        return "</div></div>\n";
    }

    private String generateHeader(Game game) {
        return String.format("<div class='card game_card' style='display:none;'>\n" +
                        "<div class='card-header'>\n" +
                        "<a href=%s>%s</a>\n" +
                        "</div>\n" +
                        "<div class='card-body'>",
                RelativePathHelper.getPath(game),
                game.getTitleBR());
    }
}