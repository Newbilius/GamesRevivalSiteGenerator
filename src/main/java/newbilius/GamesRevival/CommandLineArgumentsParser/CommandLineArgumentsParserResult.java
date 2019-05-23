package newbilius.GamesRevival.CommandLineArgumentsParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandLineArgumentsParserResult {
    private Map<String, String> options = new HashMap<>();
    private List<String> errors = new ArrayList<>();

    public boolean haveError() {
        return !errors.isEmpty();
    }

    public void addError(String error) {
        errors.add(error);
    }

    public String[] getErrors() {
        return errors.toArray(new String[0]);
    }

    public void addOption(String arg, String argParam) {
        options.put(arg, argParam);
    }

    public void addOption(String arg) {
        options.put(arg, "");
    }

    public boolean haveValue(CMDParserOption option) {
        return options.containsKey(option.param);
    }

    public boolean getBoolean(CMDParserOption option, boolean defaultValue) {
        String value;
        return (value = options.get(option.param)) != null ? Boolean.parseBoolean(value) : defaultValue;
    }

    public String getString(CMDParserOption option, String defaultValue) {
        return options.getOrDefault(option.param, defaultValue);
    }

    public int getInt(CMDParserOption option, int defaultValue) {
        String value;
        return (value = options.get(option.param)) != null ? Integer.parseInt(value) : defaultValue;
    }
}