package Script;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class TopPane extends GridPane {    // Split Pane, Scroll Pane, Flow Pane
    
    Searcher searcher;

    TopPane(Searcher searcher) {
        this.searcher = searcher;
        setUp();
    }

    private void setUp() {
        this.setBackground(new Background(new BackgroundFill(Color.rgb(54, 57, 63, 1), null, null)));
        this.setPadding(new Insets(5, 10, 5, 10));
        this.setHgap(10);
        this.setVgap(5);
        Label conLabel = new Label("Contains:");
        TextField conField = new TextField();
        Label extLabel = new Label("Extension:");
        TextField extField = new TextField();
        Button startButton = new Button("Search");
        Label pathLabel = new Label("Path:");
        TextField pathField = new TextField("C:/Users/Wispy/");
        CheckBox hiddenBox = new CheckBox("Check Hidden Files");
        Node[] elements = {conLabel, conField, extLabel, extField, startButton, pathLabel, pathField, hiddenBox};
        conLabel.setTextFill(Color.WHITE);
        pathLabel.setTextFill(Color.WHITE);
        extLabel.setTextFill(Color.WHITE);
        hiddenBox.setTextFill(Color.WHITE);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searcher.searchDir(pathField.getText(), conField.getText().toLowerCase(), extField.getText().toLowerCase(), hiddenBox.isSelected());
            }});
        TopPane.setConstraints(conField, 1, 0);
        TopPane.setConstraints(extLabel, 2, 0);
        TopPane.setConstraints(extField, 3, 0);
        TopPane.setConstraints(hiddenBox, 4, 0);
        TopPane.setConstraints(pathLabel, 0, 1);
        TopPane.setConstraints(pathField, 1, 1, 3, 1);
        TopPane.setConstraints(startButton, 4, 1);
        for (Node node : elements) {
            this.getChildren().add(node);
        }
    }

}