package newbilius.GamesRevival.HTML;

import newbilius.GamesRevival.Data.Port;
import newbilius.GamesRevival.GeneratorsInfrastructure.RelativePathHelper;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.google.common.html.HtmlEscapers.htmlEscaper;

public class PortScreensHTMLGenerator {

    public static String getImageUrl(Port port, String imagePathFromPort) {
        var outputTileName = getOutputFileName(imagePathFromPort);
        return "/" + RelativePathHelper.getPath(port) + "screens/" + outputTileName;
    }

    public static String getImageBlock(Port port, String imagePath) {
        var imageUrl = getImageUrl(port, imagePath);
        return String.format("<a data-title='%s' data-toggle='lightbox' data-gallery='example-gallery' target=_new href='%s'>" +
                        "<img src='%s' style='max-width:300px;margin:4px;' class='img-thumbnail img-fluid'></a>",
                htmlEscaper().escape(port.Title),
                imageUrl,
                imageUrl
        );
    }

    public static Path getOutputFileName(String imagePath) {
        return Paths.get(imagePath).getFileName();
    }

}
