package Script;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

public class FilePane extends FlowPane {     // stage = window/jframe, scene = stuff inside window/jpanels
    
    FilePane() {
        setUp();
    }

    private void setUp() {
        this.setBackground(new Background(new BackgroundFill(Color.rgb(54, 57, 63, 1), null, null)));
        // this.getChildren().add(new Button("Click Me!"));
        Label label = new Label("Search: ");
        label.setVisible(true);
        this.getChildren().add(new TextField());
        this.getChildren().add(label);
    }

}