package Script;

import java.io.File;
import java.util.ArrayList;

public class SearchThread extends Thread {

    private String path = null;
    private String name = null;
    private String ext = null;
    private boolean hidden = false;
    private boolean matchFolders = false;
    private Searcher searcher = null;
    private ArrayList<File> directories = new ArrayList<File>(); // directories to check
    private ArrayList<File> doneDirs = new ArrayList<File>(); // directories that have been done and to remove
    private File[] files = null; // file matches
    private File[] folders = null; 
    private int recursion = 0;

    SearchThread(String path, String name, String ext, boolean hidden, boolean matchFolders, Searcher searcher) {
        this.path = path;
        this.name = name;
        this.ext = ext;
        this.hidden = hidden;
        this.matchFolders = matchFolders;
        this.searcher = searcher;
    }

    public void run() {// I'm using File.listFiles, Files.list seems to have same results and response time but more complicated
        File startDir = new File(path);
        File[] newDirs = null;
        directories.removeAll(doneDirs);
        if (hidden == false) {
            newDirs = startDir.listFiles((file) -> file.isDirectory() && !file.isHidden());
            if (matchFolders == true && ext == "") folders = startDir.listFiles((file) -> file.isDirectory() && !file.isHidden() && file.getName().toLowerCase().contains(name));
        }
        else {
            newDirs = startDir.listFiles((file) -> file.isDirectory());
            if (matchFolders == true && ext == "") folders = startDir.listFiles((file) -> file.isDirectory() && file.getName().toLowerCase().contains(name));
        }
        if (newDirs != null) {
            for (File dir : newDirs) {
                directories.add(dir);
            }
        }
        if (hidden == false) {
            files = startDir.listFiles((file) -> file.isFile() && !file.isHidden() && file.getName().toLowerCase().endsWith(ext) && chopExt(file.getName().toLowerCase()).contains(name));
        }
        else {
            files = startDir.listFiles((file) -> file.isFile() && file.getName().toLowerCase().endsWith(ext) && chopExt(file.getName()).contains(name));
        }
        if (files != null) {
            for (File file : files) {
                searcher.addFile(file);
            }
        }
        if (folders != null) {
            for (File file : folders) {
                searcher.addFolder(file);
            }
        }
        if (directories.size() != 0) { 
            for (File file : directories) {
                this.path = file.getAbsolutePath();
                doneDirs.add(file);
                if (searcher.getThreadCount() < Searcher.maxThreads) {
                    SearchThread sThread = new SearchThread(path, name, ext, hidden, matchFolders, searcher);
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

    public String chopExt(String s) {
        int dot = s.lastIndexOf(".");
        if (dot == -1) {
            dot = s.length();
        }
        return s.substring(0, dot);
    }
}
