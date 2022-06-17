package Script;

import java.util.concurrent.locks.*;

public class Searcher {

    private int fileCount = 0;
    private double totalSize = 0;
    private String measure = "B";
    private int folderCount = 0;
    private int threadCount = 0;
    private Lock lock = new ReentrantLock();
    private User user;
    public SearchThread begin;

    Searcher(User user) {
        this.user = user;
    }

    public void searchDir(String path, String name, String ext, boolean hidden) {
        changeThread(1);
        begin = new SearchThread(path, name, ext, hidden, this);
        begin.start();
    }

    public void changeThread(int amount) {
        lock.lock();
        try {
            threadCount += amount;
        } finally {
            lock.unlock();
        }
        System.out.println(threadCount);
        if (threadCount == 0) {
            user.output();
        }
    }

    public int getThreadCount() {
        return threadCount;
    }

    public int getFileCount() {
        return fileCount;
    }

    public double getTotalSize() {
        if (totalSize > 1000000.0 && measure == "B") {
            totalSize /= 1000000.0;
            measure = "MB";
        }
        if (totalSize > 1000.0 && measure == "MB") {
            totalSize /= 1000.0;
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