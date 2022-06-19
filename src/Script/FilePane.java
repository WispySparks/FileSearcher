package Script;

import java.io.File;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
// import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class FilePane extends GridPane {

    Searcher searcher;
    ArrayList<Label> names = new ArrayList<Label>();

    FilePane(Searcher searcher) {
        this.searcher = searcher;
        setUp();
    }

    private void setUp() {
        this.setBackground(new Background(new BackgroundFill(Color.rgb(54, 57, 63, 1), null, null)));
        this.setPadding(new Insets(0, 10, 0, 10));
        searcher.setPane(this);
    }

    public void update() {
        Platform.runLater(new Runnable() {
            int i = 0;
            @Override
            public void run() {
                getChildren().removeAll(names);
                for (File file : searcher.getFiles()) {
                    Label label = new Label(file.getName());
                    setConstraints(label, 0, i);
                    getChildren().add(label);
                    i++;
                    names.add(label);
                }
            }
        });
    }

}
