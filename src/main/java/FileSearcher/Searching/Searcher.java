package FileSearcher.Searching;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import FileSearcher.Display.FileData;
import FileSearcher.Display.FilePane;
import FileSearcher.Display.TopPane;

public class Searcher {

    public static int maxThreads = 16;
    private int threadCount = 0;
    private Lock lock = new ReentrantLock();
    private long start = 0;
    private ArrayList<File> fileResults = new ArrayList<File>();
    private ArrayList<File> folderResults = new ArrayList<File>();
    private List<FileData> results = new ArrayList<>();
    private FilePane[] panes;
    private TopPane tPane;
    private boolean inProgress = false;

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
            ArrayList<File> sortedFiles = FilePane.quickSortFiles(fileResults);
            ArrayList<File> sortedFolders = FilePane.quickSortFiles(folderResults);
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

    public List<FileData> getResults() {
        File f = new File("C:\\Users\\wispy\\Downloads\\ideas.txt");
        results.add(FileData.createRecord(f));
        return results;
    }

}