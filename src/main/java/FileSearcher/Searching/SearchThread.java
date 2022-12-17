package FileSearcher.Searching;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class SearchThread extends Thread {

    private String path;
    private String name;
    private String ext;
    private boolean hidden;
    private boolean matchFolders;
    private Searcher searcher;
    private Lock lock;
    private List<File> directories = new ArrayList<>(); // directories to check
    private List<File> doneDirs = new ArrayList<>(); // directories that have been done and to remove
    private List<File> results = new ArrayList<>(); // matches
    private int recursion = 0;

    SearchThread(String path, String name, String ext, boolean hidden, boolean matchFolders, Searcher searcher, Lock lock) {
        this.path = path;
        this.name = name;
        this.ext = ext;
        this.hidden = hidden;
        this.matchFolders = matchFolders;
        this.searcher = searcher;
        this.lock = lock;
    }

    public void run() {// I'm using File.listFiles, Files.list seems to have same results and response time but more complicated
        File startDir = new File(path);
        List<File> newDirs = new ArrayList<>();
        directories.removeAll(doneDirs);
        if (hidden == false) {
            newDirs = Arrays.asList(startDir.listFiles((file) -> file.isDirectory() && !file.isHidden()));
            if (matchFolders) results.addAll(arrayToList(startDir.listFiles((file) -> file.isDirectory() && !file.isHidden() && file.getName().toLowerCase().contains(name))));
            results.addAll(arrayToList(startDir.listFiles((file) -> file.isFile() && !file.isHidden() && file.getName().toLowerCase().endsWith(ext) && chopExt(file.getName().toLowerCase()).contains(name))));
        }
        else {
            newDirs = Arrays.asList(startDir.listFiles((file) -> file.isDirectory()));
            if (matchFolders) results.addAll(arrayToList(startDir.listFiles((file) -> file.isDirectory() && file.getName().toLowerCase().contains(name))));
            results.addAll(arrayToList(startDir.listFiles((file) -> file.isFile() && file.getName().toLowerCase().endsWith(ext) && chopExt(file.getName()).contains(name))));
        }
        results.forEach((file) -> searcher.addResult(file));
        newDirs.forEach((dir) -> directories.add(dir));
        if (directories.size() > 0) { 
            for (File file : directories) {
                boolean recurs = true;
                this.path = file.getAbsolutePath();
                doneDirs.add(file);
                lock.lock();
                try {
                    if (searcher.getThreadCount() < Searcher.maxThreads) {
                        SearchThread sThread = new SearchThread(path, name, ext, hidden, matchFolders, searcher, lock);
                        searcher.changeThread(1);
                        sThread.start();
                        recurs = false;
                    }
                } finally {
                    lock.unlock();
                }
                if (recurs == true) {
                    recursion++;
                    run();
                    recursion--;
                    break;
                }
            }
        }
        if (recursion == 0) {
            searcher.changeThread(-1);
        }
    }

    private String chopExt(String s) {
        int dot = s.lastIndexOf(".");
        if (dot == -1) {
            dot = s.length();
        }
        return s.substring(0, dot);
    }

    private <T> List<T> arrayToList(T[] array) {
        List<T> list = new ArrayList<>();
        for (T element : array) {
            list.add(element);
        }
        return list;
    }
}
