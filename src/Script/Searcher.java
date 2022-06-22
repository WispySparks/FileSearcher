package Script;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.locks.*;

import javafx.util.Pair;

public class Searcher {

    public static int maxThreads = 16;
    private int threadCount = 0;
    private Lock lock = new ReentrantLock();
    private long start = 0;
    private ArrayList<File> fileResults = new ArrayList<File>();
    private ArrayList<File> folderResults = new ArrayList<File>();
    private FilePane[] panes;
    private TopPane tPane;
    private boolean inProgress = false;

    Searcher() {}

    public void searchDir(String path, String name, String ext, boolean hidden, boolean folders) {
        if (inProgress == false) {
            inProgress = true;
            fileResults.clear();
            folderResults.clear();
            threadCount = 0;
            changeThread(1);
            SearchThread begin = new SearchThread(path, name, ext, hidden, folders, this, lock);
            start = System.nanoTime();
            begin.start();
        }
    }

    public void changeThread(int amount) {
        lock.lock();
        try {
            threadCount += amount;
        } finally {
            lock.unlock();
        }
        if (threadCount == 0 && inProgress == true) {
            long end = System.nanoTime();
            inProgress = false;
            System.out.println("Search has Concluded");
            System.out.println("Search Took: " + (double) (end-start)/1000000000 + " Seconds");
            System.out.println(fileResults.size() + " " + folderResults.size());
            long start2 = System.nanoTime();
            ArrayList<File> sortedFiles = FilePane.sortFilesByAlphabet(fileResults);
            ArrayList<File> sortedFolders = FilePane.sortFilesByAlphabet(folderResults);
            System.out.println("Sorting Took: " + (double) (System.nanoTime()-start2)/1000000000 + " Seconds");
            for (FilePane pane : panes) {
                pane.update(sortedFiles, sortedFolders);
            }
            tPane.update();
        }
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void addFile(File file) {
        fileResults.add(file);
    }

    public ArrayList<File> getFiles() {
        return fileResults;
    }

    public void addFolder(File folder) {
        folderResults.add(folder);
    }

    public ArrayList<File> getFolders() {
        return folderResults;
    }

    public Pair<Double, String> getFormatSize(double size) {
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

    public void setPanes(FilePane[] panes, TopPane tPane) {
        if (panes != null) {
            this.panes = panes;
        }
        if (tPane != null) {
            this.tPane = tPane;
        }
    }

}