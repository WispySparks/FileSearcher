package FileSearcher.Display;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;

public class SlidePane extends ScrollPane {
    
    public SlidePane() {
        setUp();
    }

    private void setUp() {
        this.setStyle("-fx-background: #36393F;");
        this.setPadding(new Insets(0, 0, 0, 0));
        this.setFitToWidth(true);
        this.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.setContent(null);
    }

}
