package FileSearcher.Display;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Pair;

public record FileData(
    File file,
    StringProperty name,
    StringProperty path,
    StringProperty extension,
    StringProperty dateModified,
    StringProperty size
){
    public static FileData createRecord(File file) {
        String name = file.getName();
        String path = file.getAbsolutePath();
        String extension = getExt(file.getName()).toUpperCase() + " File";
        Instant instance = Instant.ofEpochMilli(file.lastModified());
        LocalDateTime date = LocalDateTime.ofInstant(instance, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy  h:mm a");
        String dateModified = date.format(formatter);
        Pair<Double, String> p = (file.isDirectory()) ? getFormatedSize(getDirSize(file)) : getFormatedSize(getFileSize(file));
        String size = Double.toString(p.getKey()) + " "  + p.getValue();
        return new FileData(
            file,
            new SimpleStringProperty(name), 
            new SimpleStringProperty(path),
            new SimpleStringProperty(extension),
            new SimpleStringProperty(dateModified),
            new SimpleStringProperty(size)
        );
    }

    public static List<FileData> createRecordList(List<File> files) {
        List<FileData> list = new ArrayList<>();
        for (File file : files) {
            list.add(createRecord(file));
        }
        return list;
    }

    private static String getExt(String s) {    // get a file's extension
        int dot = s.lastIndexOf(".") + 1;
        if (dot == -1) {
            dot = 0;
        }
        return s.substring(dot, s.length());
    }

    private static long getDirSize(File folder) {
        long size = 0;
        try {
            size = Files.walk(Paths.get(folder.getCanonicalPath())).mapToLong(p -> p.toFile().length()).sum();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    private static long getFileSize(File file) {
        long size = 0;
        try {
            size = Files.size(Paths.get(file.getCanonicalPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    private static Pair<Double, String> getFormatedSize(double size) {
        String measure = "B";
        if (size > 1024.0 && measure == "B") {
            size /= 1024.0;
            measure = "KB";
        }
        if (size > 1024.0 && measure == "KB") {
            size /= 1024.0;
            measure = "MB";
        }
        if (size > 1024.0 && measure == "MB") {
            size /= 1024.0;
            measure = "GB";
        }
        size = Math.floor(size*100) / 100;
        return new Pair<Double,String>(size, measure);
    }

}
