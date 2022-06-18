package Script;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SearchThread extends Thread {

    private String path = null;
    private String name = null;
    private String ext = null;
    private boolean hidden = false;
    private Searcher searcher = null;
    private ArrayList<File> directories = new ArrayList<File>();
    private ArrayList<File> doneDirs = new ArrayList<File>();
    private File[] files = null;
    private int recursion = 0;

    SearchThread(String path, String name, String ext, boolean hidden, Searcher searcher) {
        this.path = path;
        this.name = name;
        this.ext = ext;
        this.hidden = hidden;
        this.searcher = searcher;
    }

    public void run() {// I'm using File.listFiles, Files.list seems to have same results and response time but more complicated
        File startDir = new File(path);
        File[] newDirs = null;
        directories.removeAll(doneDirs);
        if (hidden == false) {
            newDirs = startDir.listFiles((file) -> file.isDirectory() && !file.isHidden());
        }
        else {
            newDirs = startDir.listFiles((file) -> file.isDirectory());
        }
        if (newDirs != null) {
            for (File dir : newDirs) {
                directories.add(dir);
            }
        }
        if (hidden == false) {
            files = startDir.listFiles((file) -> file.isFile() && !file.isHidden() && file.getName().endsWith(ext));
        }
        else {
            files = startDir.listFiles((file) -> file.isFile() && file.getName().endsWith(ext));
        }
        if (files != null) {
            for (File file : files) {
                // System.out.println(file.getName());
                searcher.fileInc();
                try {
                searcher.sizeInc((double) Files.size(Paths.get(file.getAbsolutePath())));
                } catch (IOException e) {
                System.out.println(e);
                }
            }
        }
        if (directories.size() != 0) { 
            for (File file : directories) {
                searcher.folderInc();
                this.path = file.getAbsolutePath();
                doneDirs.add(file);
                if (searcher.getThreadCount() < Searcher.maxThreads) {
                    SearchThread sThread = new SearchThread(path, name, ext, hidden, searcher);
                    searcher.changeThread(1);
                    sThread.start();
                }
                else {
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
}
