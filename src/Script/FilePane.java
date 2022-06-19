package Script;

import java.io.File;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class FilePane extends GridPane {

    Searcher searcher;
    ArrayList<Label> names = new ArrayList<Label>();
    static enum Type {
        NAME,
        DATE,
        EXT,
        SIZE,
        PATH
    }
    Type purpose;

    FilePane(Searcher searcher, Type purpose) {
        this.searcher = searcher;
        this.purpose = purpose;
        setUp();
    }

    private void setUp() {
        this.setBackground(new Background(new BackgroundFill(Color.rgb(54, 57, 63, 1), null, null)));
        this.setPadding(new Insets(0, 10, 0, 10));
    }

    public void update() {
        Platform.runLater(new Runnable() {
            int i = 0;
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
                            break;
                        case SIZE:
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

}
