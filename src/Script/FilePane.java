package Script;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class FilePane extends Pane {     // stage = window/jframe, scene = stuff inside window/jpanels
    
    FilePane() {
        setUp();
    }

    private void setUp() {
        this.getChildren().add(new Button("Click Me!"));
    }

}