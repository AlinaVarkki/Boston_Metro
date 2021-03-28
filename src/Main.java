import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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

        GridPane grid = new GridPane();
        //grid.setPadding(new Insets (10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHalignment(HPos.LEFT);
        col1.setPercentWidth(70);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHalignment(HPos.RIGHT);
        col2.setPercentWidth(30);

        grid.getColumnConstraints().addAll(col1,col2);

        HBox hbButtons = new HBox();
        hbButtons.setSpacing(10.0);

        Button btnFind = new Button("FIND ROUTE");
        btnFind.setStyle("-fx-font-size: 15pt;");

        Label lbl1 = new Label("WELCOME TO THE BOSTON METRO");
        Label lbl2 = new Label("Find the fastest away around the city on a hill with ease!");

        Image image = new Image("metro_map.png");
        ImageView iv1 = new ImageView();
        iv1.setImage(image);

        Image image2 = new Image("boston_logo.png");
        ImageView iv2 = new ImageView();
        iv2.setImage(image2);


        window.show();
    }
}
