package newbilius.GamesRevival.GeneratorsInfastructure;

import newbilius.GamesRevival.FileHelper;
import newbilius.GamesRevival.FoldersConfig;

import java.io.IOException;

public abstract class BaseOnePageGenerator extends BasePagesGenerator {

    public BaseOnePageGenerator(FoldersConfig foldersConfig) {
        super(foldersConfig);
    }

    @Override
    public void generateData() throws IOException {
        var jsContent = "";
        var jsFileName = getJSFileName();
        if (jsFileName != null && !jsFileName.isEmpty())
            jsContent = getTemplateFileContent(jsFileName);

        var text = setJS(template, jsContent);
        text = setContent(text, getContent());
        text = setTitle(text, getTitle());

        FileHelper.writeStringToFile(foldersConfig.getOutputFolder() + "/" + getFileName(), text);
    }

    protected abstract String getFileName();

    protected abstract String getJSFileName();

    protected abstract String getContent() throws IOException;

    protected abstract String getTitle();
}
