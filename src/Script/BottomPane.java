package Script;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;

public class BottomPane extends ScrollPane {
    
    Searcher searcher;

    BottomPane(Searcher searcher) {
        this.searcher = searcher;
        setUp();
    }

    private void setUp() {
        this.setStyle("-fx-background: #36393F;");
        this.setPadding(new Insets(0, 0, 0, 0));
        this.setContent(new FilePane(searcher));
    }

}
