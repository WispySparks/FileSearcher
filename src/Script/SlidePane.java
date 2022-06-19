package Script;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;

public class SlidePane extends ScrollPane {
    
    private Searcher searcher;

    SlidePane(Searcher searcher) {
        this.searcher = searcher;
        setUp();
    }

    private void setUp() {
        this.setStyle("-fx-background: #36393F;");
        this.setPadding(new Insets(0, 0, 0, 0));
        this.setFitToWidth(true);
        this.setContent(new DividerPane(searcher));
    }

}
