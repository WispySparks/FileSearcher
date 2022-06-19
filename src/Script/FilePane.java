package Script;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Pair;

public class FilePane extends GridPane {

    private Searcher searcher;
    private ArrayList<Label> names = new ArrayList<Label>();
    enum Type {
        NAME,
        DATE,
        EXT,
        SIZE,
        PATH
    }
    private Type purpose;

    FilePane(Searcher searcher, Type purpose) {
        this.searcher = searcher;
        this.purpose = purpose;
        setUp();
    }

    private void setUp() {
        this.setBackground(new Background(new BackgroundFill(Color.rgb(54, 57, 63, 1), null, null)));
        this.setPadding(new Insets(0, 10, 0, 10));
        Label label = null;
        Label label0 = new Label("Name");
        Label label1 = new Label("Path");
        Label label2 = new Label("Date Modified");
        Label label3 = new Label("Type");
        Label label4 = new Label("Size");
        switch (purpose) {
            case DATE:
                label = label2;
                break;
            case EXT:
                label = label3;
                break;
            case NAME:
                label = label0;
                break;
            case PATH:
                label = label1;
                break;
            case SIZE:
                label = label4;
                break;
        }
        this.getChildren().add(label);
    }

    public void update() {
        Platform.runLater(new Runnable() {
            int i = 1;
            @Override
            public void run() {
                getChildren().removeAll(names);
                Label label = null;
                for (File file : searcher.getFiles()) {
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

}
