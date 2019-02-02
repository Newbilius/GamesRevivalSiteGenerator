package newbilius.GamesRevival;

public class FoldersConfig {
    private String templateFolder;
    private String outputFolder;
    private String inputFolder;

    public FoldersConfig(Config config) {
        templateFolder = config.TemplateFolder;
        outputFolder = config.OutputFolder;
        inputFolder = config.InputFolder;
    }

    public String getTemplateFolder() {
        return templateFolder;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public String getInputFolder() {
        return inputFolder;
    }

    public static class Config {
        public String InputFolder;
        public String OutputFolder;
        public String TemplateFolder;
    }
}
