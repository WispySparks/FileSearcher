package FileSearcher.Searching;

import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import FileSearcher.Display.FileData;
import FileSearcher.Display.TopPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Searcher {

    public static final int maxThreads = 16;
    private final ObservableList<FileData> results = FXCollections.observableArrayList();
    private Lock lock = new ReentrantLock();
    private TopPane topPane;
    private int threadCount = 0;
    private long startTime = 0;
    private boolean inProgress = false;

    public void searchDir(String path, String name, String ext, boolean hidden, boolean folders) {
        if (inProgress == false) {
            inProgress = true;
            results.clear();
            threadCount = 0;
            changeThread(1);
            SearchThread firstThread = new SearchThread(path, name, ext, hidden, folders, this, lock);
            startTime = System.nanoTime();
            firstThread.start();
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
            System.out.println("Search has Concluded");
            System.out.println("Search Took: " + (double) (System.nanoTime()-startTime)/1000000000 + " Seconds");
            System.out.println(results.size());
            long start2 = System.nanoTime();
            // List<FileData> sortedFiles = FileTableView.quickSortFiles(results);
            // results.clear();
            // results.addAll(sortedFiles);
            System.out.println("Sorting Took: " + (double) (System.nanoTime()-start2)/1000000000 + " Seconds");
            topPane.update();
        }
    }

    public void setPane(TopPane p) {
        topPane = p;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void addResult(File file) {
        results.add(FileData.createRecord(file));
    }

    public ObservableList<FileData> getResults() {
        return results;
    }

}