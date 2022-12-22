package FileSearcher.Display;

import java.io.File;

import FileSearcher.Searching.Searcher;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
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
    
    private final Stage mainStage;
    private Searcher searcher;
    private Text loading;

    public TopPane(Searcher searcher, Stage mainStage) {
        this.searcher = searcher;
        this.mainStage = mainStage;
        setBackground(new Background(new BackgroundFill(Color.rgb(54, 57, 63, 1), null, null)));
        setPadding(new Insets(5, 10, 20, 10));
        setHgap(10);
        setVgap(5);
        setup();
    }

    private void setup() {
        Label conLabel = new Label("Contains:");
        TextField conField = new TextField();
        Label extLabel = new Label("Extension:");
        TextField extField = new TextField();
        Button startButton = new Button("Search");
        Button pathButton = new Button();
        Label pathLabel = new Label("Path:");
        TextField pathField = new TextField("C:/Users/Wispy");
        CheckBox hiddenBox = new CheckBox("Check Hidden Files");
        CheckBox folderBox = new CheckBox("Include Folders in Results");
        loading = new Text("");
        DirectoryChooser chooser = new DirectoryChooser();
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
        ImageView img = new ImageView(new Image(getClass().getResourceAsStream("/images/fileexplorer.png")));
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
        EventHandler<KeyEvent> enter = (event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                searcher.searchDir(pathField.getText(), conField.getText().toLowerCase(), extField.getText().toLowerCase(), hiddenBox.isSelected(), folderBox.isSelected());
                loading.setText("Searching . . .");
            }                
        };
        conField.setOnKeyPressed(enter);
        extField.setOnKeyPressed(enter);
        pathField.setOnKeyPressed(enter);
        add(conLabel, 0, 0);
        add(conField, 1, 0);
        add(extLabel, 2, 0);
        add(extField, 3, 0);
        add(hiddenBox, 4, 0);
        add(folderBox, 5, 0);
        add(pathLabel, 0, 1);
        add(pathField, 1, 1, 3, 1);
        add(pathButton, 4, 1);
        add(startButton, 4, 1);
        add(loading, 5, 1);
        setHalignment(startButton, HPos.RIGHT);
        setHalignment(loading, HPos.CENTER);
    }

    public void setFinished() {
        Platform.runLater(() -> loading.setText("Search Finished"));
    }

}