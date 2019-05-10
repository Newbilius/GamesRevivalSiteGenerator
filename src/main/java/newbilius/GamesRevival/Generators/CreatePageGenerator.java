package newbilius.GamesRevival.Generators;

import newbilius.GamesRevival.FileHelper;
import newbilius.GamesRevival.FoldersConfig;
import newbilius.GamesRevival.GeneratorsInfrastructure.BaseOnePageGenerator;
import newbilius.GamesRevival.Data.DataHelpers;
import newbilius.GamesRevival.HTML.Breadcrumbs.MainBreadcrumbGenerator;

import java.io.IOException;

@SuppressWarnings("unused")
public class CreatePageGenerator extends BaseOnePageGenerator {

    public CreatePageGenerator(FoldersConfig foldersConfig) {
        super(foldersConfig);
    }

    @Override
    protected String getFileName() {
        return "create.php";
    }

    @Override
    protected String getJSFileName() {
        return "create_mod_js.html";
    }

    @Override
    protected String getContent() throws IOException {
        var builder = new StringBuilder();

        var breadcrumbGenerator = new MainBreadcrumbGenerator();
        breadcrumbGenerator.add("Создать модификацию");
        builder.append(breadcrumbGenerator.generate());

        var dataHelpers = new DataHelpers(Games);

        var phpCode = FileHelper.getFileText(foldersConfig.getTemplateFolder() + "create_mod.php");
        var page = FileHelper.getFileText(foldersConfig.getTemplateFolder() + "create_mod.html")
                .replace("#GAMES_ITEMS#", getGamesList())
                .replace("#OS_ITEMS#", getOSList(dataHelpers))
                .replace("#TAG_ITEMS#", getTagsList(dataHelpers))
                .replace("#GENRES_ITEMS#", getGenresList(dataHelpers))
                .replace("#FILE_NAME#", getFileName())
                .replace("#PHP#", phpCode);

        builder.append(page);

        return builder.toString();
    }

    private String getGamesList() {
        var builder = new StringBuilder();

        for (var game : Games) {
            builder.append(String.format("<option value=\"%s\">%s</option>\r\n",
                    game.Path,
                    game.getTitle()));
        }

        return builder.toString();
    }

    private String getOSList(DataHelpers dataHelpers) {
        var builder = new StringBuilder();

        var osList = dataHelpers.getOS();
        for (String os : osList) {
            builder.append(createCheckbox("modOS[]", os));
        }

        return builder.toString();
    }

    private String getGenresList(DataHelpers dataHelpers) {
        var builder = new StringBuilder();

        var genres = dataHelpers.getGenres();
        for (String genre : genres) {
            builder.append(createCheckbox("gameGenres[]", genre));
        }

        return builder.toString();
    }

    private String getTagsList(DataHelpers dataHelpers) {
        var builder = new StringBuilder();

        var tags = dataHelpers.getTags();
        for (String tag : tags) {
            builder.append(createCheckbox("modTags[]", tag));
        }

        return builder.toString();
    }

    private int checkboxIdCounter = 0;

    private String createCheckbox(String name,
                                  String value) {
        checkboxIdCounter++;
        String id = "checkboxId" + checkboxIdCounter;

        return String.format("<div class='custom-control custom-checkbox custom-control-inline'>\n" +
                        "\t<input name='%s' type='checkbox' class='custom-control-input' id='%s' value='%s'>\n" +
                        "\t<label class='custom-control-label' for='%s'>%s</label>\n" +
                        "</div>",
                name,
                id,
                value,
                id,
                value);
    }

    @Override
    protected String getTitle() {
        return "Games Revival - создать модификацию";
    }
}
