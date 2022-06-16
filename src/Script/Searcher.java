package Script;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Searcher {

    private int fileCount = 0;
    private double totalSize = 0;
    private String measure = "B";
    private int folderCount = 0;
    private ArrayList<Thread> threads = new ArrayList<Thread>();
    private Semaphore sem = new Semaphore(1);
    private User user;
    public SearchThread begin;

    Searcher(User user) {
        this.user = user;
    }

    public void searchDir(String path, String name, String ext, boolean hidden) {
        begin = new SearchThread(path, name, ext, hidden, this, sem);
        addThread(begin);
        begin.start();
    }

    public void addThread(Thread thread) {
        threads.add(thread);
    }

    public void removeThread(Thread thread) {
        threads.remove(thread);
        System.out.println(threads.size());
        if (threads.size() == 0) {
            user.output();
        }
    }

    public ArrayList<Thread> getThreads() {
        return threads;
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