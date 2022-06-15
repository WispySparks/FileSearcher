package Script;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        Searcher search = new Searcher();
        System.out.println("Caps don't matter and just use single slashes");
        System.out.println("Write the path you would like to search: ");
        String path = scanner.nextLine();
        System.out.println("Now pick an extension to search for or skip this: ");
        String extension = scanner.nextLine(); 
        System.out.println("Would you like to search for hidden files/directories? true/false: ");
        boolean hidden = Boolean.parseBoolean(scanner.nextLine());
        search.searchDir(path, extension, hidden);
        Thread.sleep(15000);
        System.out.println("File Count: " + search.getFileCount());
    }
}