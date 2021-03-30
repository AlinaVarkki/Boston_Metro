import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App2 extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Model model = new Model("src/bostonmetro.txt");
        PathDisplayer2 view = new PathDisplayer2();
        VBox root = new VBox();
        root.setSpacing(20);
        root.setStyle("-fx-background-color: #0B132B;");

        Scene scene = new Scene(root, 700, 700);

        Controller controller = new Controller(model,view, root);
        controller.run();
        stage.setScene(scene);

        stage.show();
    }
}
