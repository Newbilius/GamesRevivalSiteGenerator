package newbilius.GamesRevival.Generators;

import newbilius.GamesRevival.Data.Game;
import newbilius.GamesRevival.Data.Port;
import newbilius.GamesRevival.FileHelper;
import newbilius.GamesRevival.FoldersConfig;
import newbilius.GamesRevival.GeneratorsInfastructure.BasePagesGenerator;
import newbilius.GamesRevival.GeneratorsInfastructure.RelativePathHelper;
import newbilius.GamesRevival.HTML.*;
import newbilius.GamesRevival.HTML.Badges.BadgeGenerator;
import newbilius.GamesRevival.HTML.Badges.BadgeType;
import newbilius.GamesRevival.HTML.Breadcrumbs.MainBreadcrumbGenerator;

import java.io.IOException;

@SuppressWarnings("unused")
public class PortsPagesGenerator extends BasePagesGenerator {
    private PortScreensHTMLGenerator portScreensHTMLGenerator;

    public PortsPagesGenerator(FoldersConfig foldersConfig) {
        super(foldersConfig);
    }

    @Override
    public void generateData() throws IOException {

        var mdToHtmlConverter = new MDToHtmlConverter();
        var portJs = getTemplateFileContent("port_js.html");
        var portTemplate = getTemplateFileContent("port.html");

        for (var game : Games) {
            for (var port : game.Ports) {
                var text = setJS(template, portJs);
                text = setTitle(text, String.format("Games Revival - %s - %s",
                        game.getTitle(),
                        port.Title));
                text = setContent(text, getContent(portTemplate, game, port, mdToHtmlConverter));

                var path = foldersConfig.getOutputFolder() + "/" + RelativePathHelper.getPath(port);

                FileHelper.createFolder(path);
                FileHelper.writeStringToFile(path + "index.html", text);
            }
        }
    }

    private String getContent(String portTemplate, Game game, Port port, MDToHtmlConverter mdToHtmlConverter) throws IOException {
        var builder = new StringBuilder();

        var breadcrumbGenerator = new MainBreadcrumbGenerator();
        breadcrumbGenerator.add(game.Title[0].trim(), "/" + RelativePathHelper.getPath(game));
        breadcrumbGenerator.add(port.Title.trim(), "/" + RelativePathHelper.getPath(port));
        builder.append(breadcrumbGenerator.generate());

        var portHTML = portTemplate
                .replace("#TEXT#", mdToHtmlConverter.convert(port.About))
                .replace("#TITLE#", port.Title)
                .replace("#OS_BADGES#", getOSBadges(port))
                .replace("#TAGS#", getTagsBadges(port))
                .replace("#IMAGES#", getImages(port))
                .replace("#VIDEO#", getVideos(port))
                .replace("#VIDEO_TAB_STYLE#", getVideosTabShowing(port))
                .replace("#IMAGES_TAB_STYLE#", getImagesTabShowing(port))
                .replace("#PORT_LINKS#", getLinks(port, mdToHtmlConverter))
                .replace("#EDIT_LINK#", RelativePathHelper.getEditUrl(port))
                .replace("#ADD_SCREENS_TO_MOD#", RelativePathHelper.getAddScreensUrl(port));
        builder.append(portHTML);

        return builder.toString();
    }

    private String getLinks(Port port, MDToHtmlConverter mdToHtmlConverter) {
        var stringBuilder = new StringBuilder();

        if (!port.Link.isBlank())
            stringBuilder.append(String.format("<h4><a href=%s>Скачать с официального сайта</a></h4><p></p>", port.Link));

        stringBuilder.append(mdToHtmlConverter.convert(port.Files));

        return stringBuilder.toString();
    }

    private String getImagesTabShowing(Port port) {
        if (port.ImagesPath.length > 0 && !port.ImagesPath[0].trim().isBlank())
            return "";
        return "display:none;";
    }

    private String getVideosTabShowing(Port port) {
        if (port.Video.length > 0 && !port.Video[0].trim().isBlank())
            return "";
        return "display:none;";
    }

    private String getVideos(Port port) {
        var stringBuilder = new StringBuilder();

        for (var video : port.Video) {
            var videoParts = video.split("=");
            String videoCode;
            if (videoParts.length > 1)
                videoCode = video.split("=")[1];
            else {
                videoParts = video.split("/");
                videoCode = videoParts[videoParts.length - 1];
            }

            stringBuilder.append(String.format("<div class='embed-responsive embed-responsive-gamesrevival-video embed-responsive-16by9'>\n" +
                            "<iframe class='embed-responsive-item' style='padding:10px;' src='https://www.youtube.com/embed/%s' allow='accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture' allowfullscreen='' frameborder='0'></iframe>\n" +
                            "</div>\n",
                    videoCode));
        }

        return stringBuilder.toString();
    }

    private String getImages(Port port) throws IOException {
        var stringBuilder = new StringBuilder();
        var haveImages = port.ImagesPath.length > 0;
        if (haveImages) {
            stringBuilder.append("<div class='d-flex align-content-start flex-wrap'>");

            var screensFolder = foldersConfig
                    .getOutputFolder()
                    + RelativePathHelper.getPath(port)
                    + "//screens//";

            FileHelper.createFolder(screensFolder);

            for (var image : port.ImagesPath) {
                var outputTileName = PortScreensHTMLGenerator.getOutputFileName(image);
                FileHelper.copyFile(image, screensFolder + outputTileName.toString());
                stringBuilder.append(PortScreensHTMLGenerator.getImageBlock(port, image));
            }

            stringBuilder.append("</div>");
        }
        return stringBuilder.toString();
    }

    private String getOSBadges(Port port) {
        var stringBuilder = new StringBuilder();
        for (var os : port.OS)
            stringBuilder.append(BadgeGenerator.createBadge(os, BadgeType.Grey));
        return stringBuilder.toString();
    }

    private String getTagsBadges(Port port) {
        var stringBuilder = new StringBuilder();
        for (var tag : port.Tags)
            stringBuilder.append(BadgeGenerator.createBadge(tag));
        return stringBuilder.toString();
    }
}