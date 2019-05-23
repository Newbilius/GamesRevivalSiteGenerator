package newbilius.GamesRevival.CommandLineArgumentsParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandLineArgumentsParser {
    private List<CMDParserOption> options = new ArrayList<>();

    private static boolean isAnyEqualsIgnoreCase(String text, String... matchedTexts) {
        return Arrays.stream(matchedTexts)
                .anyMatch(text::equalsIgnoreCase);
    }

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
            var arg = args[i].trim();
            var commandOptional = options.stream()
                    .filter(x -> isAnyEqualsIgnoreCase(arg, "-" + x.param, "--" + x.param))
                    .findFirst();

            if (commandOptional.isEmpty()) {
                result.addError(getUnknownOptionExceptionText(arg));
                continue;
            }

            var command = commandOptional.get();
            if (command.withArgs) {
                if (i + 1 >= args.length || args[i + 1].startsWith("-")) {
                    result.addError(notSettedParamOfOptionExceptionText(arg));
                } else {
                    i++;
                    result.addOption(command.param, args[i]);
                }
            } else {
                result.addOption(command.param);
            }
        }

        for (var option : options) {
            if (option.required && !result.haveValue(option)) {
                result.addError(notSettedRequiredParamExceptionText(option.param));
            }
        }

        return result;
    }

    private String exitIfOnlyHelpParam(String[] args) {
        var containsHelpArgument = Arrays.stream(args)
                .map(String::trim)
                .anyMatch(s -> isAnyEqualsIgnoreCase(s, "-?", "-h", "-help"));
        if (containsHelpArgument)
            return helpExceptionText();

        if (args.length == 1 && isAnyEqualsIgnoreCase(args[0], "?", "h", "help"))
            return helpExceptionText();

        return null;
    }

    public void printHelp() {
        printHelpHeader();
        for (var option : options) {
            System.out.println(getHelpTextLine(option));
        }
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

