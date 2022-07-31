package Script;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.util.Pair;

public class FilePane extends GridPane {

    enum Type {
        NAME,
        DATE,
        EXT,
        SIZE,
        PATH
    }
    private static String alpha = "!._0123456789abcdefghijklmnopqrstuvwxyz";
    private static char[] alphabet = alpha.toCharArray();
    private Searcher searcher = null;
    private ArrayList<Label> names = new ArrayList<Label>();
    private Type purpose = null;
    private Label header = null;
    private SlidePane slidePane = null;
    private ArrayList<File> files = null;
    private ArrayList<File> folders = null;

    FilePane(Searcher searcher, Type purpose, SlidePane slidePane) {
        this.searcher = searcher;
        this.purpose = purpose;
        this.slidePane = slidePane;
        setUp();
    }

    private void setUp() {
        this.setBackground(new Background(new BackgroundFill(Color.rgb(54, 57, 63, 1), null, null)));
        this.setPadding(new Insets(0, 10, 0, 10));
        header = null;
        Label label0 = new Label("Name");
        Label label1 = new Label("Path");
        Label label2 = new Label("Date Modified");
        Label label3 = new Label("Type");
        Label label4 = new Label("Size");
        switch (purpose) {
            case DATE:
                header = label2;
                break;
            case EXT:
                header = label3;
                break;
            case NAME:
                header = label0;
                break;
            case PATH:
                header = label1;
                break;
            case SIZE:
                header = label4;
                break;
        }
        header.setStyle("-fx-background-color: #36393F; -fx-view-order: -1;"); // keep headers on top
        header.setMaxWidth(Double.MAX_VALUE);
        this.getChildren().add(header);
        slidePane.vvalueProperty().addListener(setHeader);
    }

    public void update(ArrayList<File> files, ArrayList<File> folders) {  // update the gui of the window for all of the new labels of the information
        this.files = files;
        this.folders = folders;
        Platform.runLater(new Runnable() {
        int foldFile = 0;
        @Override
        public void run() {
            if (foldFile == 0) getChildren().removeAll(names);     // remove previous labels
            Label label = null;
            ArrayList<File> sorted = null;
            sorted = (foldFile == 0) ? folders : files;
            Pair<Integer, Integer> minMax = getMinMax(sorted);
            if (sorted.size() > 0) {
                for (int j = minMax.getKey(); j<minMax.getValue(); j++) {
                    File file = sorted.get(j);
                    switch (purpose) {  // create all the labels based on its purpose
                        case NAME:
                            label = new Label(file.getName());
                            ImageView img = (foldFile == 0) ? new ImageView(new Image("file:./resources/foldericon.png")) : new ImageView(new Image("file:./resources/fileicon.png"));
                            label.setGraphic(img);
                            Tooltip tp = new Tooltip(file.getAbsolutePath());   // add tooltip to name label for its path
                            tp.setShowDelay(new Duration(100));
                            Tooltip.install(label, tp);
                            break;
                        case DATE:
                            Instant instance = Instant.ofEpochMilli(file.lastModified());
                            LocalDateTime date = LocalDateTime.ofInstant(instance, ZoneId.systemDefault());
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy  h:mm a");
                            label = new Label(date.format(formatter));
                            break;
                        case EXT:
                            label = (foldFile == 0) ? new Label("File folder") : new Label(getExt(file.getName()).toUpperCase() + " File");
                            break;
                        case SIZE:
                            try {
                                Pair<Double, String> p = (foldFile == 0) ? searcher.getFormatSize(getDirSize(file)) : searcher.getFormatSize(Files.size(Paths.get(file.getCanonicalPath())));
                                label = new Label(Double.toString(p.getKey()) + " "  + p.getValue());
                            } catch (IOException e) {
                                System.out.println(e);
                            }
                            break;
                        case PATH:
                            label = new Label(file.getAbsolutePath());
                            break;
                    }
                    setConstraints(label, 0, j); // put the labels in the right cell
                    getChildren().add(label);   // add label to pane
                    names.add(label);   // add label to names so it can be removed for a future search
                }
            }
            foldFile++;
            if (foldFile == 1) run();
        }});
    }

    public static ArrayList<File> quickSortFiles(ArrayList<File> list) {
        QuickSort qs = new QuickSort();
        double[] values = new double[list.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = alphabetIndex(list.get(i), 4, i*0.00000000001);     // get a list of values corresponding to each file
        }
        HashMap<Double, File> hashMap = new HashMap<Double, File>();
        for (int i = 0; i < values.length; i++) {
            hashMap.put(values[i], list.get(i));    // pair each value with its file
        }
        if (values.length > 0) {
            qs.recursiveSort(values, 0, values.length-1);    // sort the values
            // qs.interativeSort(values);
        }
        list.clear();
        for (double i : values) {
            list.add(hashMap.get(i));   // get the files from the sorted values
        }
        return list;
    }

    public static double alphabetIndex(File file, int letterPos, double offset) { // find a strings position along the alphabet, letter checked is one at letterPos
        double index = -1;
        char letter;
        if (file != null) {
            for (int j = 0; j <= letterPos; j++) {
                if (j < file.getName().length()) {
                    letter = file.getName().toLowerCase().charAt(j);
                }
                else {
                    break;
                }
                for (int i = 0; i<alphabet.length; i++) {
                    if (letter == alphabet[i]) {
                        if (j == 0) {
                            index = i/(j+1);
                        }
                        else {
                            index += (double) i/(Math.pow(10, 2+(j*2)));
                        }
                    }
                }
            }
        }
        return index+offset;
    }

    public long getDirSize(File folder) {
        long size = 0;
        try {
            size = Files.walk(Paths.get(folder.getCanonicalPath())).mapToLong(p -> p.toFile().length()).sum();
        } catch (IOException e) {
            System.out.println(e);
        }
        return size;
    }

    public String getExt(String s) {    // get a file's extension
        int dot = s.lastIndexOf(".") + 1;
        if (dot == -1) {
            dot = 0;
        }
        return s.substring(dot, s.length());
    }

    public <T> Pair<Integer, Integer> getMinMax(List<T> list) { // this is supposed to grab the min and max number for the tiles that need to be rendered
        // String equation = "ŷ = 0.05667X + 12.09285"; // linear regression equation
        int renderedTiles = 12; // number of cells in each direction to be rendered
        double x = slidePane.getContent().getBoundsInLocal().getHeight() * slidePane.getVvalue(); // get scrollbar value
        int middle = (int) Math.round((0.05667*x) + 12.09285); // put it in equation to get row index of middle tile for that value
        int min = ((middle-renderedTiles) >= 0) ? middle-renderedTiles : 0; // make sure min doesnt go below zero
        int max = ((middle+renderedTiles) < list.size()) ? middle+renderedTiles : list.size(); // make sure max doesnt go above size
        return new Pair<Integer,Integer>(min, max);
    }

    InvalidationListener setHeader = (o -> {    // keep the header labels at the top of the page even when scrolling down
        final double y = (this.getHeight() - slidePane.getViewportBounds().getHeight()) * slidePane.getVvalue();
        header.setTranslateY(y);
        update(files, folders);
    });

}
