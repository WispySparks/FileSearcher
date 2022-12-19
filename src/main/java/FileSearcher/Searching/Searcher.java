package FileSearcher.Searching;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import FileSearcher.Display.FileData;
import FileSearcher.Display.TopPane;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Searcher {

    public static final int maxThreads = 16;
    
    private final ObservableList<FileData> tableResults = FXCollections.observableArrayList();
    private final List<FileData> tempResults = new ArrayList<>();
    private List<Thread> threads = new ArrayList<>();
    private Lock lock = new ReentrantLock();
    private TopPane topPane;
    private int threadCount = 0;
    private long startTime = 0;
    private boolean inProgress = false;

    public void searchDir(String path, String name, String ext, boolean hidden, boolean folders) {
        if (inProgress == false) {
            inProgress = true;
            threadCount = 0;
            tempResults.clear();
            SearchThread firstThread = new SearchThread(path, name, ext, hidden, folders, this, lock);
            changeThread(1, firstThread);
            startTime = System.nanoTime();
            firstThread.start();
        }
    }

    public void changeThread(int amount, Thread t) {
        lock.lock();
        try {
            threadCount += amount;
            if (amount == 1) {
                threads.add(t);
            } else {
                threads.remove(t);
            }
        } finally {
            lock.unlock();
        }
        if (threadCount == 0 && inProgress == true) {
            inProgress = false;
            System.out.println("Search has Concluded");
            System.out.println("Search Took: " + (double) (System.nanoTime()-startTime)/1000000000 + " Seconds");
            System.out.println(tempResults.size());
            long start2 = System.nanoTime();
            List<FileData> sortedFiles = Sorter.sortFiles(tempResults);
            Platform.runLater(() -> {
                tableResults.clear();
                tableResults.addAll(sortedFiles);
            });
            System.out.println("Sorting Took: " + (double) (System.nanoTime()-start2)/1000000000 + " Seconds");
            topPane.update();
        }
    }

    public int getThreadCount() {
        return threadCount;
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public void setPane(TopPane p) {
        topPane = p;
    }

    public void addResult(File file) {
        tempResults.add(FileData.createRecord(file));
    }

    public ObservableList<FileData> getTableResults() {
        return tableResults;
    }

}