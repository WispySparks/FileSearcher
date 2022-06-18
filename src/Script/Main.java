package Script;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {     // Application and Stage is the JFrame, Scene and Pane is the JPanel
    public static void main(String[] args){
        launch(args);   // launch the application to start running
        // User user = new User();
        // user.input();
    }

    @Override
    public void start(Stage mainStage) throws Exception {    // first method that gets called after app is launched
        FilePane layout = new FilePane();
        Scene mainScene = new Scene(layout, 600, 400);
        mainStage.getIcons().add(new Image("file:./resources/Wiggle.png"));
        mainStage.setTitle("File Searcher");
        mainStage.setScene(mainScene);
        mainStage.show();
    }
}