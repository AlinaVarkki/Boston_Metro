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
            background.setRadiusX(32);
            background.setRadiusY(22);
            background.setFill(Color.rgb(245,61,61));
            background.setStroke(Color.rgb(11,19,43));

            Ellipse background2 = new Ellipse();
            background2.setCenterX(20);
            background2.setCenterY(20);
            background2.setRadiusX(30);
            background2.setRadiusY(20);
            background2.setFill(Color.rgb(11,19,43));
            background2.setStroke(Color.rgb(11,19,43));

            Circle circle = new Circle(14);
            circle.setCenterX(2);
            circle.setCenterY(20);
            circle.setFill(Color.SILVER);
            circle.setStroke(Color.SILVER);


            Text map = new Text();
            map.setText("Map");
            map.setFill(Color.WHITE);
            map.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR,9));
            map.setX(-6);
            map.setY(18);

            Text mapOn = new Text();
            mapOn.setText("On");
            mapOn.setFill(Color.WHITE);
            mapOn.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR,9));
            mapOn.setX(-4);
            mapOn.setY(28);

            Text map2 = new Text();
            map2.setText("Map");
            map2.setFill(Color.WHITE);
            map2.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR,9));
            map2.setX(27);
            map2.setY(18);

            Text mapOff = new Text();
            mapOff.setText("Off");
            mapOff.setFill(Color.WHITE);
            mapOff.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR,9));
            mapOff.setX(27);
            mapOff.setY(28);

            translateTransition.setNode(circle);
            fillTransition.setShape(background);

            getChildren().addAll(background2,background,mapOn,map,mapOff,map2,circle);

            switchOn.addListener((obs,oldState,newState) -> {
                boolean mapOnCheck = newState.booleanValue();
                translateTransition.setToX(mapOnCheck ? 0+35 : 0);
                fillTransition.setFromValue(mapOnCheck ? Color.rgb(245,61,61) : Color.rgb(20,158,106));
                fillTransition.setFromValue(mapOnCheck ? Color.rgb(20,158,106) : Color.rgb(245,61,61));
                animate.play();
            });

            setOnMouseClicked(event -> {
                switchOn.set(!switchOn.get());
            });
        }

}

