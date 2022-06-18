package Script;

import java.util.concurrent.locks.*;

public class Searcher {

    public static int maxThreads = 16;
    private int fileCount = 0;
    private double totalSize = 0;
    private String measure = "B";
    private int folderCount = 0;
    private int threadCount = 0;
    private Lock lock = new ReentrantLock();
    private long start = 0;

    Searcher() {}

    public void searchDir(String path, String name, String ext, boolean hidden) {
        start = System.nanoTime();
        changeThread(1);
        SearchThread begin = new SearchThread(path, name, ext, hidden, this);
        begin.start();
    }

    public void changeThread(int amount) {
        lock.lock();
        try {
            threadCount += amount;
        } finally {
            lock.unlock();
        }
        if (threadCount == 0) {
            long end = System.nanoTime();
            System.out.println("Search has Concluded");
            System.out.println("Time Took: " + (double) (end-start)/1000000000 + " Seconds");
            System.out.println("File Count: ~" + getFileCount());
            System.out.println("Folder Count: ~" + getFolderCount());
            System.out.println("Total Size: " + getTotalSize() + " " + getMeasure());
            fileCount = 0;
            totalSize = 0;
            measure = "B";
            folderCount = 0;
            threadCount = 0;
            start = 0;
        }
    }

    public int getThreadCount() {
        return threadCount;
    }

    public int getFileCount() {
        return fileCount;
    }

    public double getTotalSize() {
        if (totalSize > 1024.0 && measure == "B") {
            totalSize /= 1024.0;
            measure = "KB";
        }
        if (totalSize > 1024.0 && measure == "KB") {
            totalSize /= 1024.0;
            measure = "MB";
        }
        if (totalSize > 1024.0 && measure == "MB") {
            totalSize /= 1024.0;
            measure = "GB";
        }
        return totalSize;
    }

    public String getMeasure() {
        return measure;
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

}