package newbilius.GamesRevival.HTML;

import newbilius.GamesRevival.GeneratorsInfastructure.RelativePathHelper;

public class EditMenuGenerator {
    public static String getBlock(String fileName) {
        var path = RelativePathHelper.getEditUrl(fileName);

        return String.format("<br>\n" +
                        "<div class=\"card\">\n" +
                        "<div class=\"card-header\">\n" +
                        "<a href='%s' style=\"font-size:0.8em !important;display: inline-block;float: left;\">Редактировать страницу</a>\n" +
                        "</div>\n" +
                        "</div>",
                path);
    }
}