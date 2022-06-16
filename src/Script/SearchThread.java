package Script;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Semaphore;

public class SearchThread extends Thread {

    private String path = null;
    private String ext = null;
    private boolean hidden = false;
    private Searcher searcher = null;
    private Semaphore sem = null;

    SearchThread(String path, String ext, boolean hidden, Searcher searcher, Semaphore sem) {
        this.path = path;
        this.ext = ext;
        this.hidden = hidden;
        this.searcher = searcher;
        this.sem = sem;
    }

    public void run() {
        try {
            sem.acquire();
        } catch (Exception e) {
        }
        File dir = new File(path);
        File[] directories = null;
        File[] files = null;
        if (hidden == false) {
            directories = dir.listFiles((file) -> file.isDirectory() && !file.isHidden());
        }
        else {
            directories = dir.listFiles((file) -> file.isDirectory());
        }
        if (hidden == false) {
            files = dir.listFiles((file) -> file.isFile() && !file.isHidden() && file.getName().endsWith(ext));
        }
        else {
            files = dir.listFiles((file) -> file.isFile() && file.getName().endsWith(ext));
        }
        if (directories != null) { 
            for (File file : directories) {
                searcher.folderInc();
                path = file.getAbsolutePath();
                SearchThread sThread = new SearchThread(path, ext, hidden, searcher, sem);
                searcher.addThread(sThread);
                sThread.start();
            }
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
        searcher.removeThread(this);
        sem.release();
    }

}
