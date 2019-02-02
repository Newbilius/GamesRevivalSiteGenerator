package newbilius.GamesRevival.Generators;

import newbilius.GamesRevival.FoldersConfig;
import newbilius.GamesRevival.GeneratorsInfastructure.BaseOnePageGenerator;
import newbilius.GamesRevival.HTML.Breadcrumbs.MainBreadcrumbGenerator;

import java.io.IOException;

@SuppressWarnings("unused")
public class UploadImagesPageGenerator extends BaseOnePageGenerator {

    public UploadImagesPageGenerator(FoldersConfig foldersConfig) {
        super(foldersConfig);
    }

    @Override
    protected String getFileName() {
        return "upload.php";
    }

    @Override
    protected String getJSFileName() {
        return "upload_images_js.html";
    }

    @Override
    protected String getContent() throws IOException {
        var builder = new StringBuilder();

        var breadcrumbGenerator = new MainBreadcrumbGenerator();
        breadcrumbGenerator.add("Загрузить скриншоты");
        builder.append(breadcrumbGenerator.generate());

        var phpCode = getTemplateFileContent("upload_images.php");
        var page = getTemplateFileContent("upload_images.html")
                .replace("#PHP#", phpCode);

        builder.append(page);

        return builder.toString();
    }

    @Override
    protected String getTitle() {
        return "Games Revival - загрузить скриншоты";
    }
}
