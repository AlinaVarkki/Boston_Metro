import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {

        window.setTitle("Boston Metro System");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("firstScreenView.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root, 1100, 700, Color.WHITE);
        scene.getStylesheets().add("style.css");
        window.setScene(scene);
        window.show();

        View view = loader.getController();
        view.setRoot(root);
        Model model = new Model("src/resources/bostonMetroStations.txt");
        Controller controller = new Controller(model, view);

        controller.run();
    }

    //  method used for tests
    public static Node getStationByName(List<Node> stations, String name) {
        for (Node s : stations) {
            if (s.getName().equals(name)) return s;
        }
        return null;
    }
}
