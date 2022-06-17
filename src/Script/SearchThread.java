package Script;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class SearchThread extends Thread {

    private String path = null;
    private String name = null;
    private String ext = null;
    private boolean hidden = false;
    private Searcher searcher = null;
    private ArrayList<File> directories = new ArrayList<File>();
    private ArrayList<File> doneDirs = new ArrayList<File>();
    private File[] files = null;

    SearchThread(String path, String name, String ext, boolean hidden, Searcher searcher) {
        this.path = path;
        this.name = name;
        this.ext = ext;
        this.hidden = hidden;
        this.searcher = searcher;
    }

    public void run() {
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
        if (directories != null) { 
            for (File file : directories) {
                searcher.folderInc();
                this.path = file.getAbsolutePath();
                if (searcher.getThreadCount() < 8) {
                    doneDirs.add(file);
                    SearchThread sThread = new SearchThread(path, name, ext, hidden, searcher);
                    searcher.changeThread(1);
                    sThread.start();
                }
                else {
                    run();
                }
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
    }

}
