package newbilius.GamesRevival.CommandLineArgumentsParser;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandLineArgumentsParserResult {
    private HashMap<String, String> options = new HashMap<>();
    private ArrayList<String> errors = new ArrayList<>();

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
        if (options.containsKey(option.param))
            return Boolean.parseBoolean(options.get(option.param));
        return defaultValue;
    }

    public String getString(CMDParserOption option, String defaultValue) {
        return options.getOrDefault(option.param, defaultValue);
    }

    public int getInt(CMDParserOption option, int defaultValue) {
        if (options.containsKey(option.param))
            return Integer.parseInt(options.get(option.param));
        return defaultValue;
    }
}