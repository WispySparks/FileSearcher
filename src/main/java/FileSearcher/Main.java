package FileSearcher;

import FileSearcher.Display.SlidePane;
import FileSearcher.Display.TopPane;
import FileSearcher.Searching.Searcher;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {   

    private static Searcher searcher = new Searcher();

    public static void main(String[] args){
        launch(args);  
    }

    @Override
    public void start(Stage mainStage) throws Exception {  
        BorderPane borderPane = new BorderPane(new SlidePane(searcher), new TopPane(searcher, mainStage), null, null, null);
        Scene mainScene = new Scene(borderPane, 775, 500);
        mainScene.getStylesheets().add("/stylesheet.css");
        mainStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        mainStage.setTitle("File Searcher");
        mainStage.setScene(mainScene);
        mainStage.show();
    }
}