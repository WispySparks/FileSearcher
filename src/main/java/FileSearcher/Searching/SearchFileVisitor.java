package FileSearcher.Searching;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class SearchFileVisitor implements FileVisitor<Path> {

    Searcher searcher;
    String name = "";
    String ext = "";
    boolean hidden = false;
    boolean addDirectories = false;

    SearchFileVisitor(Searcher searcher) {
        this.searcher = searcher;
    }

    public void configure(String name, String ext, boolean hidden, boolean addDirectories) {
        this.name = name;
        this.ext = ext;
        this.hidden = hidden;
        this.addDirectories = addDirectories;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        File d = dir.toFile();
        if (d.isHidden() && hidden == false) return FileVisitResult.CONTINUE;
        if (addDirectories && chopExt(d.getName().toLowerCase()).contains(name)) {
            searcher.addResult(d);
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        File f = file.toFile();
        if (f.isHidden() && hidden == false) return FileVisitResult.CONTINUE;
        if (f.getName().toLowerCase().endsWith(ext) && chopExt(f.getName().toLowerCase()).contains(name)) {
            searcher.addResult(f);
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    private String chopExt(String s) {
        int dot = s.lastIndexOf(".");
        if (dot == -1) {
            dot = s.length();
        }
        return s.substring(0, dot);
    }
    
}
