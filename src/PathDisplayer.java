import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

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

    private int circleRadius = 15;
    private int x = 0;
    private int y = 0;

    public PathDisplayer(){

        initialiseColorMappings();

    }

    /**
     * @param stations contains the Best Suited Path Found By the Model
     *
     * @return Hbox with the Line element and all corresponding labels appropriately Styled and Sized
     * */


    public Pane createLineTest(List<Tuple<String, List<String>>> stations) {
        VBox box = new VBox();

        HBox linebox = new HBox();
        Line l = new Line(circleRadius,10,circleRadius,100);
        l.setStroke(white);
        Text text = new Text("hello");
        text.setFill(white);

        linebox.getChildren().addAll(l, text);
        linebox.setMargin(l, new Insets(0,5*circleRadius,0,circleRadius));
        linebox.setAlignment(Pos.CENTER_LEFT);

        box.getChildren().add(displayLineLabel("Line name", "Red", "Switch to", makeTripleCircle(0,10,"Red")));
        box.getChildren().add(linebox);
        box.getChildren().add(displayLineLabel("Line name", "Green", "Switch to", makeTripleCircle(0,100,"Green")));
        box.getChildren().add(displayLineLabel("Lineeeeeeeee name", "Red", "Switch to", makeTripleCircle(0,200,"Red")));
        box.getChildren().add(displayLineLabel("Line name", "Red", "Switch to", makeTripleCircle(0,300,"Red")));

        box.setAlignment(Pos.CENTER_LEFT);


        box.setStyle("-fx-background-color: #0B132B;");



        return box;
    }

    public Pane createLine(List<Tuple<String, List<String>>> stations) {

        VBox thingy = new VBox();

        if (!stations.isEmpty()) {
            int currentEnd = 2*circleRadius;
            int chunkSize;

            String currentColor = stations.get(0).first;
            List<String> currentLine = stations.get(0).second;


            thingy.getChildren().add(createStartingStation(currentLine.get(0),currentColor,x,y));

            stations.get(0).second.remove(0);

            for (int i = 0; i < stations.size(); i++) {

                //gets current color and arraylist of stations
                currentColor = stations.get(i).first;
                currentLine = stations.get(i).second;

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
                    thingy.getChildren().add(createMiddleStation(lastStation,currentColor,stations.get(i+1).first,x,y+start));

                } else {
                    thingy.getChildren().add(createEndingStation(lastStation,currentColor,x,y+start));

                }

            }
        }

        VBox almostFinalBox = new VBox();
        almostFinalBox.getChildren().add(thingy);
        almostFinalBox.setAlignment(Pos.CENTER);

        HBox finalBox = new HBox();
//        finalBox.setCenter(thingy);
        finalBox.getChildren().add(almostFinalBox);
        finalBox.setAlignment(Pos.CENTER);




//        stack.setStyle("-fx-background-color: #0B132B;");

//        StackPane stack = new StackPane();
//        stack.getChildren().add(thingy);
//        stack.setStyle("-fx-background-color: #0B132B;");
//        stack.setAlignment(Pos.CENTER);


        return finalBox;
    }

    private HBox createStartingStation(String name, String color, double x, double y) {

        return displayLineLabel(name, color, "Take", makeTripleCircle(x,y,color));

    }

    private HBox createEndingStation(String name, String color, double x, double y) {

        return displayLineLabel(name, color, "", makeTripleCircle(x,y,color));

    }

    private HBox createMiddleStation(String name, String color1, String color2, double x, double y) {

        return displayLineLabel(name, color1, "Switch to", makeDoubleCircle(x,y,color1,color2));

    }

    private HBox createLineWithMiniStations(List<String> stations, String color, int start, int end) {
        HBox linebox = new HBox();

        Line line = new Line(x,y+start,x,y+end);
        line.setStroke(colorMappings.get(color));
        line.setStyle("-fx-stroke-width: 5;");


        linebox.getChildren().addAll(line, miniStationsWithButton(stations));
        linebox.setMargin(line, new Insets(0,4*circleRadius,0,circleRadius-2.5));   //- half line width
        linebox.setAlignment(Pos.CENTER_LEFT);

        return linebox;
    }

    private VBox miniStationsWithButton(List<String> stations) {

        VBox stats;
        if (stations.size() <= 3) {
            stats = displaySmallerStationNames(stations);
        } else {
            stats = displaySmallerStationNames(stations.subList(0,3));
            String buttonLabel = "... " + (stations.size()-3) + " more " + (stations.size()-3 == 1 ? "station" : "stations");
            Button button = new Button(buttonLabel);
            button.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC,circleRadius));
            button.setStyle("-fx-background-color: #0B132B; -fx-text-fill: #FFFFFF");

            stats.getChildren().add(button);
            stats.setMargin(button, new Insets(0, 0,0,circleRadius));


        }
        stats.setAlignment(Pos.CENTER_LEFT);

        return stats;

    }


    /**
     * @param name,previousColor,label
     * Calls displayBiggerStationName to get final Size and Style for key Station name
     * Calls displaySwitchLine to get the required label style and content for train transitions
     * @return Hbox with elements added
     * */
    private HBox displayLineLabel(String name, String previousColor, String label, Group circle) {
        Text title = displayBiggerStationName(name);
        Text switchLine = displaySwitchLine(label,previousColor);

        HBox titleLine = new HBox();
        titleLine.setSpacing(circleRadius*2);
        titleLine.getChildren().addAll(circle, title, switchLine);

        titleLine.setAlignment(Pos.CENTER_LEFT);


        return titleLine;
    }

    /**
     * @param label,lineColor
     * completes the "Take"/"Switch to" label with the corresponding line colour
     * @return Text in standardised format and required colour for users readability
     * */
    private Text displaySwitchLine(String label, String lineColor) {
        int fontHeight = circleRadius;

        Text text = new Text();
        text.setText(label + " " + lineColor);
        text.setFill(colorMappings.get(lineColor));

        text.setFont(Font.font("Arial", FontWeight.EXTRA_LIGHT, FontPosture.ITALIC,fontHeight));


        return text;
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
     * @param stations,lineHeight passed from displaySmallerStationNamesImproved
     * Calculate the display size and spacing of overflow stations to maintain clean display
     * Calls displaySmallerStationName to format each line for display
     * @return VBox of station names
     * */
    private VBox displaySmallerStationNames(List<String> stations) {
        int fontHeight = circleRadius;
        VBox names = new VBox();
        names.setSpacing(circleRadius/2);

        for (int i = 0; i < stations.size()-1; i++) {
            Text statName = displaySmallerStationName(stations.get(i), fontHeight);
            names.getChildren().add(statName);
        }

        return names;
    }

    /**
     * @param name,lineHeight passed form displaySmallerStationNames
     * Formats each station name to same space and style for displaying
     * @return Text of station name in standardised format
     * */
    private Text displaySmallerStationName(String name, int fontHeight) {
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
    private Group makeTripleCircle(double x, double y, String color) {
        Group circleGroup = new Group();

        circleGroup.getChildren().add(makeCircle(x,y,circleRadius,color, color));
        circleGroup.getChildren().add(makeCircle(x,y,circleRadius*0.8,"BG","BG"));
        circleGroup.getChildren().add(makeCircle(x,y,circleRadius*0.5,color, color));

        return circleGroup;
    }

    /**
     * @param x,y,color1,color2 are the Dimensions and Colour of desired Circle's
     * Generates circleGroup and fills with 2 Circles for Transition Stations
     * Calls makeCircle
     * @return Group containing the standardised Circle style for display
     * */
    private Group makeDoubleCircle(double x, double y, String color1, String color2) {
        Group circleGroup = new Group();

        circleGroup.getChildren().add(makeCircle(x,y,circleRadius,"BG","BG"));
        circleGroup.getChildren().add(makeCircle(x,y,circleRadius*0.75,color1,color2));

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
     * Initializes mappings of line name to Color for rendering
     */
    private void initialiseColorMappings() {
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
