package newbilius.GamesRevival;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileHelper {
    private static final String UTF8_BOM = "\uFEFF";

    public static String[] getDirsList(String path) {
        var file = new File(path);
        return file.list((current, name) -> new File(current, name).isDirectory());
    }

    public static String[] getFilesList(String path) {
        var file = new File(path);
        return file.list((current, name) -> !(new File(current, name)).isDirectory());
    }

    public static String getPathIfFileExists(String path) {
        var filePath = Paths.get(path);
        if (!Files.exists(filePath))
            return "";
        return path;
    }

    public static String getFileText(String path) throws IOException {
        var filePath = Paths.get(path);
        Helpers.print("Load file " + filePath);
        if (!Files.exists(filePath))
            return "";
        byte[] fileBytes = Files.readAllBytes(filePath);
        try {
            Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
            return new String(fileBytes, StandardCharsets.UTF_8);
        } catch (java.nio.charset.MalformedInputException error) {
            Helpers.print("LOAD in 1251 " + path);
            return new String(fileBytes, Charset.forName("cp1251"));
        }
    }

    public static String[] getFileLines(String path) throws IOException {
        var filePath = Paths.get(path);
        if (!Files.exists(filePath))
            return new String[0];
        String[] lines;
        try {
            lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8).toArray(new String[0]);
        } catch (java.nio.charset.MalformedInputException error) {
            Helpers.print("LOAD in 1251 " + path);
            lines = Files.readAllLines(Paths.get(path), Charset.forName("cp1251")).toArray(new String[0]);
        }
        if (lines.length > 0) {
            if (lines[0].startsWith(UTF8_BOM)) {
                lines[0] = lines[0].substring(1);
            }
        }
        return lines;
    }

    public static void writeStringToFile(String fileName, String text) throws IOException {
        Helpers.print(String.format("WRITE HTML to %s [%s bytes]", fileName, text.length()));
        try (var fileWriter = new FileWriter(fileName,
                StandardCharsets.UTF_8,
                false)) {
            fileWriter.write(text);
        }
    }

    public static void createFolder(String path) throws IOException {
        var filePath = Paths.get(path);
        if (Files.exists(filePath))
            return;
        Helpers.print("Create folder " + path);
        Files.createDirectories(Paths.get(path));
    }

    public static void copyFile(String from, String to) throws IOException {
        Helpers.print(String.format("Copy file %s to %s", from, to));
        Files.copy(Paths.get(from), Paths.get(to), StandardCopyOption.REPLACE_EXISTING);
    }
}