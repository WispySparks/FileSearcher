package Script;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

public class FilePane extends FlowPane {    // Split Pane, Scroll Pane, Flow Pane
    
    Searcher searcher = new Searcher();

    FilePane() {
        setUp();
    }

    private void setUp() {
        this.setBackground(new Background(new BackgroundFill(Color.rgb(54, 57, 63, 1), null, null)));
        Label sLabel = new Label("Search:");
        TextField sField = new TextField();
        Button sButton = new Button("Begin");
        Label pLabel = new Label("Path:");
        TextField pField = new TextField();
        sLabel.setTextFill(Color.WHITE);
        pLabel.setTextFill(Color.WHITE);
        sButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searcher.searchDir(pField.getText(), "", sField.getText(), false);                
            }});
        this.getChildren().add(sLabel);
        this.getChildren().add(sField);
        this.getChildren().add(pLabel);
        this.getChildren().add(pField);
        this.getChildren().add(sButton);
    }

}