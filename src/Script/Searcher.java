package Script;

import java.io.File;

public class Searcher {

    private String path = null;
    private String ext = null;
    private boolean hidden = false;
    private int fileCount = 0;

    Searcher() {
    }

    public void searchDir(String path, String ext, boolean hidden) {
        this.path = path;
        this.ext = ext;
        this.hidden = hidden;
        split.start();
    }

    public int getFileCount() {
        return fileCount;
    }

    private Thread split = new Thread() {
        public void run() {
            File dir = new File(path);
            File[] directories = null;
            File[] files = null;
            if (hidden == false) {
                directories = dir.listFiles((file) -> file.isDirectory() && !file.isHidden());
            }
            else {
                directories = dir.listFiles((file) -> file.isDirectory());
            }
            if (ext != null) {
                if (hidden == false) {
                    files = dir.listFiles((file) -> file.isFile() && !file.isHidden() && file.getName().endsWith(ext));
                }
                else {
                    files = dir.listFiles((file) -> file.isFile() && file.getName().endsWith(ext));
                }
            }
            else {
                if (hidden == false ) {
                    files = dir.listFiles((file) -> file.isFile() && !file.isHidden());
                }
                else {
                    files = dir.listFiles((file) -> file.isFile());
                }
            }
            if (directories != null) { 
                for (File file : directories) {
                    path = file.getAbsolutePath();
                    run();
                }
            }
            if (files != null) {
                for (File file : files) {
                    System.out.println(file.getName());
                    fileCount++;
                }
            }
        }
    };

}