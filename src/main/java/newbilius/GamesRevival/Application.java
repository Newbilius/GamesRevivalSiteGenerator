package newbilius.GamesRevival;

import newbilius.GamesRevival.CommandLineArgumentsParser.CMDParserOption;
import newbilius.GamesRevival.CommandLineArgumentsParser.CommandLineArgumentsParser;
import newbilius.GamesRevival.CommandLineArgumentsParser.CommandLineArgumentsParserResult;
import newbilius.GamesRevival.GeneratorsInfastructure.ISiteGenerator;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;

//todo перетащить на DI (Dagger2 например)

public class Application {
    public static void main(String[] args) {
        try {
            //todo эту штуку пора в отдельный репозиторий вытаскивать, уже в двух проектах заюзал (первый - https://github.com/Newbilius/HabrStatisticCollector/tree/master/src/main/java/com/newbilius/HabrStatisticCollector/CommandLineParser)
            var argsParser = new CommandLineArgumentsParser();

            var inputOption = new CMDParserOption("input", true, true, "data folder");
            argsParser.addOption(inputOption);

            var outputOption = new CMDParserOption("output", true, true, "site output folder");
            argsParser.addOption(outputOption);

            var templateOption = new CMDParserOption("template", true, true, "template folder");
            argsParser.addOption(templateOption);

            CommandLineArgumentsParserResult cmd = argsParser.parse(args);

            if (cmd.haveError()) {
                for (var error : cmd.getErrors())
                    Helpers.print(error);
                argsParser.printHelp();
                System.exit(1);
            }

            //----------
            var foldersConfigConfig = new FoldersConfig.Config();
            foldersConfigConfig.InputFolder = cmd.getString(inputOption, "");
            foldersConfigConfig.OutputFolder = cmd.getString(outputOption, "");
            foldersConfigConfig.TemplateFolder = cmd.getString(templateOption, "");

            var foldersConfig = new FoldersConfig(foldersConfigConfig);
            var games = new DataLoader(foldersConfig).Execute();

            var pagesGenerators = new Reflections("newbilius").getSubTypesOf(ISiteGenerator.class);
            for (var generator : pagesGenerators) {
                if (!Modifier.isAbstract(generator.getModifiers())) {
                    var generatorInstance = generator
                            .getDeclaredConstructor(FoldersConfig.class)
                            .newInstance(foldersConfig);

                    var partsOfClassName = generator.getName().split("/.");
                    Helpers.print("Working: " + partsOfClassName[partsOfClassName.length - 1]);

                    generatorInstance.generate(games);
                }
            }

            Helpers.print("");
            Helpers.print("COMPLETE!");

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}