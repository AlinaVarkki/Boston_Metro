import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import javafx.util.Duration;
import javafx.util.Pair;

import java.awt.*;
import java.util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PathDisplayer {

    private Color background = Color.rgb(11,19,43);
    private Color blue = Color.rgb(18,126,188);
    private Color green = Color.rgb(20,158,106);
    private Color orange = Color.rgb(245,153,35);
    private Color red = Color.rgb(245,61,61);
    private Color white = Color.rgb(255,255,255);

    private HashMap<String, Color> colorMappings = new HashMap<>();

    private int circleRadius = 10;
    private int x = 0;
    private int y = 0;
    private String showingStations;
    private HBox finalBox;
    private Pane sideStations;
    private Random random = new Random();
    private ToggleSlider toggleSlider;
    private Image image;
    private Rectangle train;

    public PathDisplayer(){

        initialiseColorMappings();

    }

    /**
     * @param stations contains the Best Suited Path Found By the Model
     *
     * @return Hbox with the Line element and all corresponding labels appropriately Styled and Sized
     * */

    public Pane createLine(List<Pair<String, List<String>>> stations) {

        toggleSlider = new ToggleSlider();
        VBox almostFinalBox = new VBox();
        HBox box = new HBox();
        finalBox = new HBox();

        StackPane animation = new StackPane();
        BorderPane border = new BorderPane();
        showingStations = "";

        VBox thingy = new VBox();

        if (!stations.isEmpty()) {
            int currentEnd = 2*circleRadius;
            int chunkSize;

            String currentColor = stations.get(0).getKey();
            List<String> currentLine = stations.get(0).getValue();

            train = new Rectangle(48,20);
            trainSelector(currentColor);
            ImagePattern imagePattern = new ImagePattern(image);
            train.setFill(imagePattern);

            thingy.getChildren().add(createStartingStation(currentLine.get(0),currentColor,x,y));

            stations.get(0).getValue().remove(0);

            for (int i = 0; i < stations.size(); i++) {

                //gets current color and arraylist of stations
                currentColor = stations.get(i).getKey();
                currentLine = stations.get(i).getValue();

                //figures out the length of line to draw
                chunkSize = Math.min(currentLine.size()-1, 5);

                //sets up the points
                int start = currentEnd;
                int end = start + 2*circleRadius + chunkSize*circleRadius;  //start + padding + space for station names
                currentEnd = end;

                //creates line with stations
                thingy.getChildren().add(createLineWithMiniStations(currentLine,currentColor,start,end));

//                String nextColor;
                String lastStation = currentLine.get(currentLine.size()-1);
                //creates circle depending on whether this is the last station or nah
                if (i+1 < stations.size()) {
                    thingy.getChildren().add(createMiddleStation(lastStation,currentColor,stations.get(i+1).getKey(),x,y+start));

                } else {
                    thingy.getChildren().add(createEndingStation(lastStation,currentColor,x,y+start));

                }

            }
        }

        almostFinalBox.getChildren().add(thingy);
        almostFinalBox.setAlignment(Pos.CENTER);
        almostFinalBox.setPadding(new Insets(0,0,0, 5*circleRadius));

        finalBox.getChildren().add(almostFinalBox);
        finalBox.setAlignment(Pos.CENTER_LEFT);

        animation.getChildren().add(train);
        animation.setAlignment(Pos.BASELINE_LEFT);
        StackPane.setMargin(train,new Insets(10,5,5,10));
        animationActive(train);

        box.setPadding(new Insets(15,15,15,15));
        box.getChildren().add(toggleSlider);
        box.setAlignment(Pos.TOP_RIGHT);
        border.setRight(box);
        border.setCenter(finalBox);
        border.setBottom(animation);


        return border;
    }

    private void animationActive(Rectangle train) {
        Duration duration = Duration.seconds(7);
        TranslateTransition transition = new TranslateTransition(duration,train);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2.9), ev -> {
            transition.setByX(622);
            transition.setNode(train);
            transition.setAutoReverse(true);
            transition.setCycleCount(10);
            transition.play();
        }));
        timeline.play();
    }

    private Image trainSelector(String colour) {
        switch (colour.charAt(0)) {
            case 'B' -> image = new Image("Images/bluesubwayboii.png");
            case 'R', 'M' -> image = new Image("Images/redsubwayboii.png");
            case 'O' -> image = new Image("Images/yellowsubwayboii.png");
            case 'G' -> image = new Image("Images/greensubwayboii.png");
            default -> image = new Image("Images/subwayboii.png");
        }
        return image;
    }

    private HBox createStartingStation(String name, String color, double x, double y) {

        return displayLineLabel(name, makeTripleCircle(x,y,color,"BG", true));

    }

    private HBox createEndingStation(String name, String color, double x, double y) {

        return displayLineLabel(name, makeTripleCircle(x,y,color,"BG", false));

    }

    private HBox createMiddleStation(String name, String color1, String color2, double x, double y) {

        return displayLineLabel(name, makeDoubleCircle(x,y,color1,color2));

    }

    private HBox createLineWithMiniStations(List<String> stations, String color, int start, int end) {
        HBox linebox = new HBox();

        Line line = new Line(x,y+start,x,y+end);
        line.setStroke(colorMappings.get(color));
        line.setStyle("-fx-stroke-width: 4;");


        linebox.getChildren().addAll(line, miniStationsWithButton(stations));
        linebox.setMargin(line, new Insets(0,4*circleRadius,0,circleRadius*1.25-2));   //- half line width
        linebox.setAlignment(Pos.CENTER_LEFT);

        return linebox;
    }

    private VBox miniStationsWithButton(List<String> stations) {

        VBox stats;
        if (stations.size() <= 4) {
            stats = displaySmallerStationNames(stations);
        } else {
            stats = displaySmallerStationNames(stations.subList(0,3));
            String buttonLabel = "... " + (stations.size()-3) + " more stations";
            Button button = new Button(buttonLabel);
            button.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC,circleRadius));
            button.setStyle("-fx-background-color: #0B132B; -fx-text-fill: #FFFFFF");
            this.setMoreButton(finalBox, button, stations);

            stats.getChildren().add(button);
            stats.setMargin(button, new Insets(0, 0,0,circleRadius));

        }
        stats.setAlignment(Pos.CENTER_LEFT);

        return stats;
    }

    private Pane showAllSmallStations(List<String> stations) {

        VBox stats = displaySmallerStationNames(stations);

        Text title = new Text();
        title.setText("Intermediate stations:");
        title.setFill(white);
        title.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC,4*circleRadius/3));


        VBox boxy = new VBox();
        boxy.getChildren().addAll(title,stats);

        boxy.setAlignment(Pos.CENTER_LEFT);
        boxy.setPadding(new Insets(0,0,0,4*circleRadius));
        boxy.setMargin(stats, new Insets(circleRadius,0,0,circleRadius));

        return boxy;

    }

    /**
     * @param name,previousColor,label
     * Calls displayBiggerStationName to get final Size and Style for key Station name
     * Calls displaySwitchLine to get the required label style and content for train transitions
     * @return Hbox with elements added
     * */
    private HBox displayLineLabel(String name, StackPane circle) {
        Text title = displayBiggerStationName(name);
        //Text title = displayBiggerStationName(name + " in " +timeStart+"mins");
        //Text switchLine = displaySwitchLine(label,previousColor);

        HBox titleLine = new HBox();
        titleLine.setSpacing(circleRadius*2);
        titleLine.getChildren().addAll(circle, title);

        titleLine.setAlignment(Pos.CENTER_LEFT);


        return titleLine;
    }


    /**
     * @param name of major stations to display in a more eye catching format
     * Styles name and sizes it
     * @return Text
     * */
    private Text displayBiggerStationName(String name) {
        int fontHeight = 3*circleRadius/2;

        Text text = new Text();
        text.setText(name.toUpperCase());
        text.setFill(white);
        text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR,fontHeight));

        return text;
    }

    /**
     * @param stations passed from displaySmallerStationNamesImproved
     * Calculate the display size and spacing of overflow stations to maintain clean display
     * Calls displaySmallerStationName to format each line for display
     * @return VBox of station names
     * */
    private VBox displaySmallerStationNames(List<String> stations) {
        VBox names = new VBox();
        names.setSpacing(circleRadius/2);

        for (int i = 0; i < stations.size()-1; i++) {
            Text statName = displaySmallerStationName(stations.get(i));
            names.getChildren().add(statName);
        }
        return names;
    }

    /**
     * @param name passed form displaySmallerStationNames
     * Formats each station name to same space and style for displaying
     * @return Text of station name in standardised format
     * */
    private Text displaySmallerStationName(String name) {
        int fontHeight = circleRadius;

        Text text = new Text();
        text.setText(name);
        text.setFill(white);
        text.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR,fontHeight));

        return text;
    }

    /**
     * @param x,y,color are the Dimensions and Colour of desired Circle's
     * Generates circleGroup and fills with 3 Circles for the Start and End Stations
     * Calls makeCircle
     * @return Group containing the standardised Circle style
     * */
    public StackPane makeTripleCircle(double x, double y, String color, String bgColor, boolean start) {
        StackPane circleGroup = new StackPane();

        //1 0.8 0.5
        circleGroup.getChildren().add(makeCircle(x,y,circleRadius*1.25,color, color));
        circleGroup.getChildren().add(makeCircle(x,y,circleRadius*0.95,bgColor,bgColor));
        circleGroup.getChildren().add(makeCircle(x,y,circleRadius*0.65,color, color));
        if (start)
            circleGroup.getChildren().add(makeLetter(color, bgColor));

        return circleGroup;
    }

    /**
     * @param x,y,color1,color2 are the Dimensions and Colour of desired Circle's
     * Generates circleGroup and fills with 2 Circles for Transition Stations
     * Calls makeCircle
     * @return Group containing the standardised Circle style for display
     * */
    private StackPane makeDoubleCircle(double x, double y, String color1, String color2) {
        StackPane circleGroup = new StackPane();

        //1 0.75
        circleGroup.getChildren().add(makeCircle(x,y,circleRadius*1.25,"BG","BG"));
        circleGroup.getChildren().add(makeCircle(x,y,circleRadius*0.95,color1,color2));
        circleGroup.getChildren().add(makeLetter(color2, "BG"));

        return circleGroup;
    }

    /**
     * @param x,y,radius,color1,color2 are the Dimensions,Radius and Colour/s of desired Circle
     * Generates Standardised Circle with desired properties form parameters
     * Adds Colour Gradient for transition between Line Colours
     * @return Circle
     * */
    private Circle makeCircle(double x, double y, double radius, String color1, String color2) {

        Circle circle = new Circle();
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(radius);
        Stop[] stops = new Stop[] { new Stop(0, colorMappings.get(color1)), new Stop(1, colorMappings.get(color2))};
        LinearGradient linear = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
        circle.setFill(linear);


        return circle;
    }

    /**
     * @param color2 name of the line
     * creates a letter to show what line it is in the circle
     * @return Text field with a single or no letters
     */
    private Text makeLetter(String color2, String fill) {

        Text letter = new Text();

        char last = color2.charAt(color2.length()-1);
        if (Character.isUpperCase(last)) {
            letter.setText(Character.toString(last));
        } else if (color2.equals("Mattapan")) {
            letter.setText("M");
        } else {
//            letter.setText(Character.toString(color2.charAt(0)));
            letter.setText("");
        }

        letter.setFill(colorMappings.get(fill));
        letter.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 1.1*circleRadius)); //4/5

        return letter;

    }


    /**
     * @param pane - place where the intermediate stations will be rendered
     * @param button - the appropriate "more stations" button
     * @param stations - list of station names to be displayed
     * sets up event handlers for "more stations" buttons by calling showStations
     */
    private void setMoreButton(Pane pane,Button button,List<String> stations){
        button.setOnAction(showStations(pane, stations));
    }

    /**
     * @param pane - place where the intermediate stations will be rendered
     * @param stations - list of station names to be displayed
     * actual logic of handling the button clicks and toggling the sideStations correctly
     * @return the event handler
     */
    private EventHandler<ActionEvent> showStations(Pane pane,List<String> stations) {
        return actionEvent -> {

            if (showingStations.equals(stations.get(0))) {
                pane.getChildren().remove(sideStations);
                showingStations = "";

            } else {
                if (sideStations != null) {
                    pane.getChildren().remove(sideStations);
                }
                sideStations = showAllSmallStations(stations);
                showingStations = stations.get(0);
                pane.getChildren().add(sideStations);
            }

        };
        }

    /**
     * Initializes mappings of line name to Color for rendering
     */
    private void initialiseColorMappings() {
        colorMappings.put("White",white);
        colorMappings.put("Transparent", Color.TRANSPARENT);
        colorMappings.put("BG",background);
        colorMappings.put("Red",red);
        colorMappings.put("RedA",red);
        colorMappings.put("RedB",red);
        colorMappings.put("Mattapan", red);
        colorMappings.put("Orange",orange);
        colorMappings.put("Blue",blue);
        colorMappings.put("Green",green);
        colorMappings.put("GreenB",green);
        colorMappings.put("GreenC",green);
        colorMappings.put("GreenD",green);
        colorMappings.put("GreenE",green);
    }

}
