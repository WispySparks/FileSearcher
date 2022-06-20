package Script;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.locks.*;

import javafx.util.Pair;

public class Searcher {

    public static int maxThreads = 16;
    private int fileCount = 0;
    private double totalSize = 0;
    private int folderCount = 0;
    private int threadCount = 0;
    private Lock lock = new ReentrantLock();
    private long start = 0;
    private ArrayList<File> fileResults = new ArrayList<File>();
    private FilePane[] panes;
    private TopPane tPane;
    private boolean inProgress = false;

    Searcher() {}

    public void searchDir(String path, String name, String ext, boolean hidden) {
        if (inProgress == false) {
            inProgress = true;
            fileResults.clear();
            fileCount = 0;
            totalSize = 0;
            folderCount = 0;
            threadCount = 0;
            start = System.nanoTime();
            changeThread(1);
            SearchThread begin = new SearchThread(path, name, ext, hidden, this);
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
            inProgress = false;
            long end = System.nanoTime();
            System.out.println("Search has Concluded");
            System.out.println("Time Took: " + (double) (end-start)/1000000000 + " Seconds");
            System.out.println("File Count: ~" + getFileCount());
            System.out.println("Folder Count: ~" + getFolderCount());
            System.out.println("Total Size: " + getFormatSize(totalSize).getKey() + " " + getFormatSize(totalSize).getValue());
            for (FilePane pane : panes) {
                pane.update();
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

    public int getFileCount() {
        return fileCount;
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
        size = Math.round(size*100) / 100;
        return new Pair<Double,String>(size, measure);
    }

    public int getFolderCount() {
        return folderCount;
    }

    public void folderInc() {
        folderCount++;
    }

    public void fileInc() {
        fileCount++;
    }

    public void sizeInc(double amount) {
        totalSize += amount;
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