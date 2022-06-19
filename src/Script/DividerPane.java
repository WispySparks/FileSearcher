package Script;

import javafx.geometry.Insets;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

public class DividerPane extends SplitPane {
    
    Searcher searcher;

    DividerPane(Searcher searcher) {
        this.searcher = searcher;
        setUp();
    }

    private void setUp() {
        this.setBackground(new Background(new BackgroundFill(Color.rgb(54, 57, 63, 1), null, null)));
        this.setPadding(new Insets(0, 0, 0, 0));
        this.getItems().addAll(new FilePane(searcher));
        // name, last modified, file type/extension, size, path
    }

}
