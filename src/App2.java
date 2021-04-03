import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App2 extends Application {
    Stage window;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {

        window.setTitle("Boston Metro System");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("firstScreenView.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root, 1030, 700, Color.WHITE);
        scene.getStylesheets().add("style.css");
        window.setScene(scene);
        window.show();

        View view = loader.<View>getController();
        view.setRoot(root);
        Model model = new Model("resources/bostonmetro.txt");
        Controller controller = new Controller(model, view);

        controller.run();
    }
}
