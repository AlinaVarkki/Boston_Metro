import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application{
    Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Boston Metro System");
        Parent root = FXMLLoader.load(getClass().getResource("FirstScreen.fxml"));
        primaryStage.setTitle("Boston Metro System");
        primaryStage.setScene(new Scene(root, 640, 400, Color.WHITE));
        primaryStage.show();


        window.show();
    }
}
