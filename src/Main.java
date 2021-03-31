import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mapView.fxml"));

        Scene scene = new Scene(root,  1171.0, 746.0);

        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
    }
}
