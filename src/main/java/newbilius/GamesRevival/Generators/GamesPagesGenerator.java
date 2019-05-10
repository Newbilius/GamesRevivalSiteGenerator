package newbilius.GamesRevival.Generators;

import newbilius.GamesRevival.Data.Game;
import newbilius.GamesRevival.Data.Port;
import newbilius.GamesRevival.FileHelper;
import newbilius.GamesRevival.FoldersConfig;
import newbilius.GamesRevival.GeneratorsInfrastructure.BasePagesGenerator;
import newbilius.GamesRevival.GeneratorsInfrastructure.RelativePathHelper;
import newbilius.GamesRevival.HTML.*;
import newbilius.GamesRevival.HTML.Badges.BadgeGenerator;
import newbilius.GamesRevival.HTML.Badges.BadgeType;
import newbilius.GamesRevival.HTML.Breadcrumbs.MainBreadcrumbGenerator;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@SuppressWarnings("unused")
public class GamesPagesGenerator extends BasePagesGenerator {
    public GamesPagesGenerator(FoldersConfig foldersConfig) {
        super(foldersConfig);
    }

    @Override
    protected void generateData() throws IOException {
        var mdToHtmlConverter = new MDToHtmlConverter();

        for (var game : Games) {
            var path = foldersConfig.getOutputFolder() + "//" + RelativePathHelper.getPath(game);
            FileHelper.createFolder(path);

            var text = setJS(template, getTemplateFileContent("game_js.html"));
            text = setTitle(text, "Games Revival - " + game.getTitle());
            text = setContent(text, getContent(game, mdToHtmlConverter));
            if (game.LogoPath.isBlank())
                text = setSocialImage(text);
            else
                text = setSocialImage(text, "/" + RelativePathHelper.getPath(game) + "logo.jpg");

            FileHelper.writeStringToFile(path + "index.html", text);
        }
    }

    @Override
    protected boolean needAutoSetSocialImage() {
        return false;
    }

    private String getContent(Game game, MDToHtmlConverter mdToHtmlConverter) throws IOException {
        var builder = new StringBuilder();

        var breadcrumbGenerator = new MainBreadcrumbGenerator();
        breadcrumbGenerator.add(game.getTitle(), "/" + RelativePathHelper.getPath(game));
        builder.append(breadcrumbGenerator.generate());

        var portTemplate = getTemplateFileContent("game.html")
                .replace("#LINKS#", mdToHtmlConverter.convert(game.Links))
                .replace("#ABOUT#", mdToHtmlConverter.convert(game.About))
                .replace("#TITLE#", game.getTitleBR())
                .replace("#IMG#", getImage(game))
                .replace("#DATA#", getPorts(game.Ports))
                .replace("#EDIT_LINK#", RelativePathHelper.getEditUrl(game))
                .replace("#ADD_MOD_LINK#", RelativePathHelper.getAddModUrl(game));
        builder.append(portTemplate);

        return builder.toString();
    }

    private String getImage(Game game) throws IOException {
        if (game.LogoPath.isBlank())
            return "";

        var imageName = "logo.jpg";
        var imageOutputPath = foldersConfig
                .getOutputFolder()
                + RelativePathHelper.getPath(game)
                + imageName;
        var url = "/" + RelativePathHelper.getPath(game) + imageName;
        FileHelper.copyFile(game.LogoPath, imageOutputPath);
        return String.format("<img src=%s style='float:left; margin-right:8px;padding-top:6px;max-width:220px;'>",
                url);
    }

    private String getPorts(Collection<Port> ports) {
        var builder = new StringBuilder();
        var first = true;

        for (var port : ports) {
            if (first) {
                first = false;
            } else {
                builder.append("<br /><br />");
            }

            builder.append(String.format("<h3><a href=%s>%s</a> </h3>",
                    "/" + RelativePathHelper.getPath(port),
                    port.Title));

            //операционки
            for (var os : port.OS) {
                if (!os.isBlank())
                    builder.append(BadgeGenerator.createBadge(os, BadgeType.Grey));
            }

            //тэги
            for (var tag : port.Tags)
                if (!tag.isBlank()) {
                    builder.append(BadgeGenerator.createBadge(tag));
                }

            if (port.ImagesPath.length > 0)
                builder.append("<br/><br/>");

            //скриншоты
            for (var image : Arrays
                    .stream(port.ImagesPath)
                    .limit(2).toArray(String[]::new)) {
                builder.append(PortScreensHTMLGenerator.getImageBlock(port, image));
            }
        }

        return builder.toString();
    }
}
