package FileSearcher.Searching;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import FileSearcher.Display.FileData;
import FileSearcher.Display.TopPane;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Searcher {

    public static final int maxThreads = 16;
    
    private final ObservableList<FileData> tableResults = FXCollections.observableArrayList();
    private final List<FileData> tempResults = new ArrayList<>();
    private final SearchFileVisitor fileVisitor = new SearchFileVisitor(this);
    private TopPane topPane;
    private long startTime = 0;
    private boolean inProgress = false;

    public void searchDir(String path, String name, String ext, boolean hidden, boolean directories) {
        if (inProgress == false) {
            inProgress = true;
            tempResults.clear();
            Path startDir = new File(path).toPath(); 
            fileVisitor.configure(name, ext, hidden, directories);
            try {
                startTime = System.nanoTime();
                Files.walkFileTree(startDir, fileVisitor);
                finishSearch();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void finishSearch() {
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
        inProgress = false;
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