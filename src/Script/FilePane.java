package Script;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Pair;

public class FilePane extends GridPane {

    enum Type {
        NAME,
        DATE,
        EXT,
        SIZE,
        PATH
    }
    private Searcher searcher = null;
    private ArrayList<Label> names = new ArrayList<Label>();
    private Type purpose = null;
    private Label header = null;
    private SlidePane slidePane = null;
    private String alpha = "abcdefghijklmnopqrstuvwxyz";
    private char[] alphabet = alpha.toCharArray();

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
        header.setStyle("-fx-background-color: #36393F; -fx-view-order: -1;");
        header.setMaxWidth(Double.MAX_VALUE);
        this.getChildren().add(header);
        slidePane.vvalueProperty().addListener(setHeader);
    }

    public void update() {
        Platform.runLater(new Runnable() {
            int i = 1;
            @Override
            public void run() {
                getChildren().removeAll(names);
                Label label = null;
                ArrayList<File> sorted = sortFiles(searcher.getFiles());
                for (File file : sorted) {
                    switch (purpose) {
                        case NAME:
                            label = new Label(file.getName());
                            break;
                        case DATE:
                            break;
                        case EXT:
                            label = new Label(getExt(file.getName()).toUpperCase() + " File");
                            break;
                        case SIZE:
                            try {
                                Pair<Double, String> p = searcher.getFormatSize(Files.size(Paths.get(file.getCanonicalPath())));
                                label = new Label(Double.toString(p.getKey()) + " "  + p.getValue());
                            } catch (IOException e) {
                                System.out.println(e);
                            }
                            break;
                        case PATH:
                            label = new Label(file.getAbsolutePath());
                            break;
                    }
                    setConstraints(label, 0, i);
                    getChildren().add(label);
                    i++;
                    names.add(label);
                }
            }
        });
    }

    public String getExt(String s) {
        int dot = s.lastIndexOf(".") + 1;
        if (dot == -1) {
            dot = 0;
        }
        return s.substring(dot, s.length());
    }

    public ArrayList<File> sortFiles(ArrayList<File> list) {
        ArrayList<File> list2 = new ArrayList<File>();
        for (int j = 0; j<5; j++) {
            for (int i = 0; i<list.size()-1; i++) {
                int prevIndex = alphabetIndex(list.get(i));
                int index = alphabetIndex(list.get(i+1));
                if (index < prevIndex) {
                    list2.add(list.get(i+1));
                    list2.add(list.get(i));
                }
                else {
                    list2.add(list.get(i));
                    list2.add(list.get(i+1));
                }
            }
            list = list2;
        }
        return list2;
    }

    public int alphabetIndex(File file) {
        int index = 0;
        char letter = file.getName().toLowerCase().charAt(0);
        for (int i = 0; i<alphabet.length; i++) {
            if (letter == alphabet[i]) {
                index = i;
                return index;
            }
        }
        return index;
    }

    InvalidationListener setHeader = (o -> {
        final double y = (this.getHeight() - slidePane.getViewportBounds().getHeight()) * slidePane.getVvalue();
        header.setTranslateY(y);
    });
}
