package newbilius.GamesRevival;

import java.util.Arrays;

public class Helpers {
    public static void print(String text) {
        System.out.println(text);
    }

    public static String plural(long value,
                                String form1,
                                String form2,
                                String form5) {
        var number = Math.abs(value);
        return (number % 10 == 1 && number % 100 != 11)
                ? form1
                : (number % 10 >= 2 && number % 10 <= 4 && (number % 100 < 10 || number % 100 >= 20) ? form2 : form5);
    }

    public static String[] removeEmptyLines(String[] items) {
        return Arrays.stream(items)
                .filter(s -> s.length() > 10)
                .toArray(String[]::new);
    }
}
