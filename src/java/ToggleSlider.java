import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ToggleSlider extends Parent {

    private TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.10));
    private FillTransition fillTransition = new FillTransition(Duration.seconds(0.15));
    public ParallelTransition animate = new ParallelTransition(translateTransition, fillTransition);

    private BooleanProperty switchOn = new SimpleBooleanProperty(false);

    public ToggleSlider() {

        Ellipse background = new Ellipse();
        background.setCenterX(20);
        background.setCenterY(20);
        background.setRadiusX(32);
        background.setRadiusY(18);
        background.setFill(Color.rgb(244, 244, 244));
        background.setStroke(Color.rgb(11, 19, 43));

        Ellipse background2 = new Ellipse();
        background2.setCenterX(20);
        background2.setCenterY(20);
        background2.setRadiusX(30);
        background2.setRadiusY(16);
        background2.setFill(Color.rgb(11, 19, 43));
        background2.setStroke(Color.rgb(11, 19, 43));

        Circle circle = new Circle(13);
        circle.setCenterX(2.2);
        circle.setCenterY(20);
        circle.setFill(Color.rgb(245, 153, 35));
        circle.setStroke(Color.rgb(245, 153, 35));


        Text map = new Text();
        map.setText("Map");
        map.setFill(Color.rgb(11, 19, 43));
        map.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 8));
        map.setX(-2);
        map.setY(18);

        Text mapOn = new Text();
        mapOn.setText("On");
        mapOn.setFill(Color.rgb(11, 19, 43));
        mapOn.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 8));
        mapOn.setX(0);
        mapOn.setY(28);

        Text map2 = new Text();
        map2.setText("Map");
        map2.setFill(Color.rgb(11, 19, 43));
        map2.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 8));
        map2.setX(26);
        map2.setY(18);

        Text mapOff = new Text();
        mapOff.setText("Off");
        mapOff.setFill(Color.rgb(11, 19, 43));
        mapOff.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 8));
        mapOff.setX(26);
        mapOff.setY(28);

        translateTransition.setNode(circle);
        fillTransition.setShape(background);

        getChildren().addAll(background2, background, mapOn, map, mapOff, map2, circle);


        switchOn.addListener((obs, oldState, newState) -> {
            boolean mapOnCheck = newState.booleanValue();
            translateTransition.setToX(mapOnCheck ? 0 + 35.5 : 0);
            animate.play();
        });
    }

    public void setClickHandler(EventHandler<MouseEvent> handler) {
        this.setOnMouseClicked(handler);
    }

    public void runSwitchOn() {
        switchOn.set(!switchOn.get());
    }
}

