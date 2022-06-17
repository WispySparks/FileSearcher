package Script;

import java.util.Scanner;

public class User {

    Searcher search;
    long start;

    User() {}

    public void input() {
        Scanner scanner = new Scanner(System.in);
        search = new Searcher(this);
        System.out.println("Caps don't matter and just use single slashes");
        System.out.println("Write the path you would like to search: ");
        String path = scanner.nextLine();
        System.out.println("Now pick an extension to search for or skip this: ");
        String extension = scanner.nextLine(); 
        System.out.println("Would you like to search for hidden files/directories? true/false: ");
        boolean hidden = Boolean.parseBoolean(scanner.nextLine());
        scanner.close();
        start = System.nanoTime();
        search.searchDir(path, "", extension, hidden);
        // 138.173 sec  374658 files  104226 folders,      93.637 seconds 1 thread, 4 core 8 logical processor, use 8 threads
    }

    public void output() {
        long end = System.nanoTime();
        System.out.println("Search has Concluded");
        System.out.println("Time Took: " + (double) (end-start)/1000000000 + " Seconds");
        System.out.println("File Count: ~" + search.getFileCount());
        System.out.println("Folder Count: ~" + search.getFolderCount());
        System.out.println("Total Size: " + search.getTotalSize() + " " + search.getMeasure());
    }
    
}
