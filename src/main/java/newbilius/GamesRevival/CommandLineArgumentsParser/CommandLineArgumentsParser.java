package newbilius.GamesRevival.CommandLineArgumentsParser;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandLineArgumentsParser {
    private ArrayList<CMDParserOption> options = new ArrayList<>();

    public void addOption(CMDParserOption option) {
        options.add(option);
    }

    public CommandLineArgumentsParserResult parse(String[] args) {
        var result = new CommandLineArgumentsParserResult();

        var exitAndShowHelp = exitIfOnlyHelpParam(args);
        if (exitAndShowHelp != null) {
            result.addError(exitAndShowHelp);
            return result;
        }

        for (var i = 0; i < args.length; i++) {
            var arg = args[i].toLowerCase().trim();
            var commandOptional = options.stream()
                    .filter(x -> ("-" + x.param.toLowerCase()).equals(arg)
                            || ("--" + x.param.toLowerCase()).equals(arg))
                    .findFirst();

            if (!commandOptional.isPresent()) {
                result.addError(getUnknownOptionExceptionText(arg));
                continue;
            }

            var command = commandOptional.get();
            if (command.withArgs) {
                if (i + 1 >= args.length)
                    result.addError(notSettedParamOfOptionExceptionText(arg));
                var argParam = args[i + 1];
                if (argParam.startsWith("-"))
                    result.addError(notSettedParamOfOptionExceptionText(arg));
                i++;
                result.addOption(command.param, argParam);
            } else {
                result.addOption(command.param);
            }
        }

        for (var option : options)
            if (option.required && !result.haveValue(option))
                result.addError(notSettedRequiredParamExceptionText(option.param));

        return result;
    }

    private String exitIfOnlyHelpParam(String[] args) {
        if (Arrays.stream(args)
                .anyMatch(s -> s.contains("-help")
                        || s.contains("-h")
                        || s.contains("-?")))
            return helpExceptionText();

        if (args.length == 1
                && (args[0].equals("?")
                || args[0].equals("h")
                || args[0].contains("help")))
            return helpExceptionText();

        if (args.length == 0)
            return helpExceptionText();
        return null;
    }

    public void printHelp() {
        printHelpHeader();
        for (var option : options)
            System.out.println(getHelpTextLine(option));
    }

    //выделены для потенциальной локализации

    @SuppressWarnings("WeakerAccess")
    protected String helpExceptionText() {
        return "Помощь по использованию";
    }

    @SuppressWarnings("WeakerAccess")
    protected String notSettedRequiredParamExceptionText(String arg) {
        return String.format("Не указан обязательный параметр %s", arg);
    }

    @SuppressWarnings("WeakerAccess")
    protected String notSettedParamOfOptionExceptionText(String arg) {
        return String.format("Не указан параметр опции %s", arg);
    }

    @SuppressWarnings("WeakerAccess")
    protected String getUnknownOptionExceptionText(String arg) {
        return String.format("Неизвестный параметр: %s", arg);
    }

    @SuppressWarnings("WeakerAccess")
    protected String getHelpTextLine(CMDParserOption option) {
        return String.format("%-20s\t\t\t%s%s",
                "-" + option.param + (option.withArgs ? " <аргумент>" : ""),
                option.required ? "[обязательный] " : "",
                option.description);
    }

    @SuppressWarnings("WeakerAccess")
    protected void printHelpHeader() {
        System.out.println("Параметры:");
    }
}

