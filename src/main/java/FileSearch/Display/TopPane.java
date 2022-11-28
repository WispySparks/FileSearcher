package FileSearch.Display;

import java.io.File;

import FileSearch.Searcher;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class TopPane extends GridPane {    // Split Pane, Scroll Pane, Flow Pane
    
    private Searcher searcher;
    private Stage mainStage;
    private TextField conField;
    private TextField extField;
    private TextField pathField;
    private CheckBox hiddenBox;
    private CheckBox folderBox;
    private Text loading;

    public TopPane(Searcher searcher, Stage mainStage) {
        this.searcher = searcher;
        this.mainStage = mainStage;
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
        Button pathButton = new Button();
        Label pathLabel = new Label("Path:");
        pathField = new TextField("C:/Users/Wispy");
        hiddenBox = new CheckBox("Check Hidden Files");
        folderBox = new CheckBox("Include Folders in Results");
        loading = new Text("");
        DirectoryChooser chooser = new DirectoryChooser();
        Node[] elements = {conLabel, conField, extLabel, extField, startButton, pathButton, pathLabel, pathField, hiddenBox, folderBox, loading};
        conLabel.setTextFill(Color.WHITE);
        pathLabel.setTextFill(Color.WHITE);
        extLabel.setTextFill(Color.WHITE);
        hiddenBox.setTextFill(Color.WHITE);
        folderBox.setTextFill(Color.WHITE);
        loading.setFill(Color.WHITE);
        loading.setFont(new Font(14));
        startButton.setMaxWidth(85);
        startButton.setOnAction((event) -> {
            searcher.searchDir(pathField.getText(), conField.getText().toLowerCase(), extField.getText().toLowerCase(), hiddenBox.isSelected(), folderBox.isSelected());
            loading.setText("Searching . . .");
        });
        pathButton.setPadding(new Insets(0));
        pathButton.setMaxHeight(25.6);
        pathButton.setMaxWidth(28);
        pathButton.setId("pathButton");
        ImageView img = new ImageView(new Image("file:./resources/filexplorer.png"));
        img.setPreserveRatio(true);
        img.fitWidthProperty().bind(pathButton.widthProperty());
        img.fitHeightProperty().bind(pathButton.heightProperty());
        pathButton.setGraphic(img);
        pathButton.setOnAction((event) ->  {
            File file;
            chooser.setInitialDirectory(new File(pathField.getText()));
            try {
                if ((file = chooser.showDialog(mainStage)) != null) {
                    pathField.setText(file.toString());
                }
            }
            catch (IllegalArgumentException e) {
                chooser.setInitialDirectory(new File("C:/Users/Wispy"));
                if ((file = chooser.showDialog(mainStage)) != null) {
                    pathField.setText(file.toString());
                }
            }
            
        });
        conField.setOnKeyPressed(enter);
        extField.setOnKeyPressed(enter);
        pathField.setOnKeyPressed(enter);
        setConstraints(conField, 1, 0);
        setConstraints(extLabel, 2, 0);
        setConstraints(extField, 3, 0);
        setConstraints(hiddenBox, 4, 0);
        setConstraints(folderBox, 5, 0);
        setConstraints(pathLabel, 0, 1);
        setConstraints(pathField, 1, 1, 3, 1);
        setConstraints(pathButton, 4, 1);
        setConstraints(startButton, 4, 1);
        setConstraints(loading, 5, 1);
        setHalignment(startButton, HPos.RIGHT);
        setHalignment(loading, HPos.CENTER);
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
                searcher.searchDir(pathField.getText(), conField.getText().toLowerCase(), extField.getText().toLowerCase(), hiddenBox.isSelected(), folderBox.isSelected());
                loading.setText("Searching . . .");
            }                
        }
    };

}