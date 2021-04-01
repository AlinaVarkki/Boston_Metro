import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Parent;
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
        public ParallelTransition animate = new ParallelTransition(translateTransition,fillTransition);

        private BooleanProperty switchOn = new SimpleBooleanProperty(false);

        public ToggleSlider() {

            Ellipse background = new Ellipse();
            background.setCenterX(20);
            background.setCenterY(20);
            background.setRadiusX(52);
            background.setRadiusY(36);
            background.setFill(Color.rgb(245,61,61));
            background.setStroke(Color.rgb(11,19,43));

            Ellipse background2 = new Ellipse();
            background2.setCenterX(20);
            background2.setCenterY(20);
            background2.setRadiusX(54);
            background2.setRadiusY(38);
            background2.setFill(Color.rgb(11,19,43));
            background2.setStroke(Color.rgb(11,19,43));

            Circle circle = new Circle(26);
            circle.setCenterX(-5);
            circle.setCenterY(20);
            circle.setFill(Color.SILVER);
            circle.setStroke(Color.SILVER);


            Text map = new Text();
            map.setText("Map");
            map.setFill(Color.WHITE);
            map.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR,12));
            map.setX(-18);
            map.setY(18);

            Text mapOn = new Text();
            mapOn.setText("On");
            mapOn.setFill(Color.WHITE);
            mapOn.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR,12));
            mapOn.setX(-16);
            mapOn.setY(32);

            Text map2 = new Text();
            map2.setText("Map");
            map2.setFill(Color.WHITE);
            map2.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR,12));
            map2.setX(34);
            map2.setY(17);

            Text mapOff = new Text();
            mapOff.setText("Off");
            mapOff.setFill(Color.WHITE);
            mapOff.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR,12));
            mapOff.setX(34);
            mapOff.setY(32);

            translateTransition.setNode(circle);
            fillTransition.setShape(background);

            getChildren().addAll(background2,background,mapOn,map,mapOff,map2,circle);

            switchOn.addListener((obs,oldState,newState) -> {
                boolean mapOnCheck = newState.booleanValue();
                translateTransition.setToX(mapOnCheck ? -2+52 : 0);
                fillTransition.setFromValue(mapOnCheck ? Color.rgb(245,61,61) : Color.rgb(20,158,106));
                fillTransition.setFromValue(mapOnCheck ? Color.rgb(20,158,106) : Color.rgb(245,61,61));
                animate.play();
            });

            setOnMouseClicked(event -> {
                switchOn.set(!switchOn.get());
            });
        }

}

