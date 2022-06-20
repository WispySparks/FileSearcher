package Script;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TopPane extends GridPane {    // Split Pane, Scroll Pane, Flow Pane
    
    private Searcher searcher;
    private TextField conField;
    private TextField extField;
    private TextField pathField;
    private CheckBox hiddenBox;
    private Text loading;

    TopPane(Searcher searcher) {
        this.searcher = searcher;
        setUp();
    }

    private void setUp() {
        this.setBackground(new Background(new BackgroundFill(Color.rgb(54, 57, 63, 1), null, null)));
        this.setPadding(new Insets(5, 10, 20, 10));
        this.setHgap(10);
        this.setVgap(5);
        Label conLabel = new Label("Contains:");
        conField = new TextField();
        Label extLabel = new Label("Extension:");
        extField = new TextField();
        Button startButton = new Button("Search");
        Label pathLabel = new Label("Path:");
        pathField = new TextField("C:/Users/Wispy/");
        hiddenBox = new CheckBox("Check Hidden Files");
        loading = new Text("");
        Node[] elements = {conLabel, conField, extLabel, extField, startButton, pathLabel, pathField, hiddenBox, loading};
        conLabel.setTextFill(Color.WHITE);
        pathLabel.setTextFill(Color.WHITE);
        extLabel.setTextFill(Color.WHITE);
        hiddenBox.setTextFill(Color.WHITE);
        loading.setFill(Color.WHITE);
        loading.setFont(new Font(13));
        startButton.setMaxWidth(Double.MAX_VALUE);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searcher.searchDir(pathField.getText(), conField.getText().toLowerCase(), extField.getText().toLowerCase(), hiddenBox.isSelected());
                loading.setText("Searching . . .");
            }});
        conField.setOnKeyPressed(enter);
        extField.setOnKeyPressed(enter);
        pathField.setOnKeyPressed(enter);
        TopPane.setConstraints(conField, 1, 0);
        TopPane.setConstraints(extLabel, 2, 0);
        TopPane.setConstraints(extField, 3, 0);
        TopPane.setConstraints(hiddenBox, 4, 0);
        TopPane.setConstraints(pathLabel, 0, 1);
        TopPane.setConstraints(pathField, 1, 1, 3, 1);
        TopPane.setConstraints(startButton, 4, 1);
        TopPane.setConstraints(loading, 6, 0, 1, 2);
        for (Node node : elements) {
            this.getChildren().add(node);
        }
        searcher.setPanes(null, this);
    }

    public void update() {
        loading.setText("Search Finished");
    }

    EventHandler<KeyEvent> enter = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.ENTER) {
                searcher.searchDir(pathField.getText(), conField.getText().toLowerCase(), extField.getText().toLowerCase(), hiddenBox.isSelected());
                loading.setText("Searching . . .");
            }                
        }
    };

}