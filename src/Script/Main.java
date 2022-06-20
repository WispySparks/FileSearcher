package Script;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {     // Application and Stage is the JFrame, Scene and Pane is the JPanel
    public static void main(String[] args){
        launch(args);   // launch the application to start running
    }

    private static Searcher searcher = new Searcher();
    
    @Override
    public void start(Stage mainStage) throws Exception {    // first method that gets called after app is launched
        BorderPane borderPane = new BorderPane(new SlidePane(searcher), new TopPane(searcher), null, null, null);
        Scene mainScene = new Scene(borderPane, 700, 500);
        mainStage.getIcons().add(new Image("file:./resources/Wiggle.png"));
        mainStage.setTitle("File Searcher");
        mainStage.setScene(mainScene);
        mainStage.show();
    }
}