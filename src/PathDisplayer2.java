import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

public class PathDisplayer2 {

    private Color background = Color.rgb(11,19,43);
    private Color blue = Color.rgb(18,126,188);
    private Color green = Color.rgb(20,158,106);
    private Color orange = Color.rgb(245,153,35);
    private Color red = Color.rgb(245,61,61);
    private Color white = Color.rgb(255,255,255);

    private HashMap<String, Color> colorMappings = new HashMap<>();
    private int circleRadius = 15;

    public PathDisplayer2(){

        initialiseColorMappings();

    }

    /**
     * @param stations contains the Best Suited Path Found By the Model
     *
     * @return Hbox with the Line element and all corresponding labels appropriately Styled and Sized
     * */
    public HBox showRouteWithoutStationNames(ArrayList<Tuple<String, ArrayList<String>>> stations) {
        Group lines = new Group();
        Group circles = new Group();
        VBox names = new VBox();
        int x = 30;

        if (!stations.isEmpty()) {
            int currentEnd = 0;
            int chunkSize;
            int countLen = 0;

            int lineLength = 600 / countLen;
            String currentColor = stations.get(0).first;
            ArrayList<String> currentLine = stations.get(0).second;

            circles.getChildren().add(makeTripleCircle(x,0,currentColor));

            HBox titleLine = displayLineLabel(currentLine.get(0),currentColor,"Take");

            names.setMargin(titleLine, new Insets(0,0,0,circleRadius*2));
            names.getChildren().add(titleLine);

            stations.get(0).second.remove(0);

            for (int i = 0; i < stations.size(); i++) {

                //gets current color and arraylist of stations
                currentColor = stations.get(i).first;
                currentLine = stations.get(i).second;

                //figures out the length of line to draw
                if (currentLine.size()>10) {
                    chunkSize = 10;
                } else {
                    chunkSize = currentLine.size();
                }

                //sets up the points
                int start = currentEnd;
                int end = start + chunkSize*lineLength;
                currentEnd = end;

                //creates line
                Line line = new Line(x,start,x,end);
                line.setStroke(colorMappings.get(currentColor));
                line.setStyle("-fx-stroke-width: 5;");
                lines.getChildren().add(line);

                String nextColor;
                //creates circle
                if (i+1 < stations.size()) {
                    circles.getChildren().add(makeDoubleCircle(x,end,currentColor,stations.get(i+1).first));
                    nextColor = stations.get(i+1).first;
                } else {
                    circles.getChildren().add(makeTripleCircle(x,end,currentColor));
                    nextColor = "";
                }

                //creates text
                VBox chunkOfStations = displayChunkOfStations(currentLine, lineLength, nextColor);
                names.setMargin(chunkOfStations, new Insets(0,0,0,circleRadius*2));
                names.getChildren().add(chunkOfStations);

            }
        }
        Group finalGroup = new Group();
        finalGroup.getChildren().add(lines);
        finalGroup.getChildren().add(circles);

        HBox finalBox = new HBox();
        finalBox.getChildren().add(finalGroup);
        finalBox.getChildren().add(names);

        return finalBox;
    }




    /**
     * @param name,previousColor,label
     * Calls displayBiggerStationName to get final Size and Style for key Station name
     * Calls displaySwitchLine to get the required label style and content for train transitions
     * @return Hbox with elements added
     * */
    private HBox displayLineLabel(String name, String previousColor, String label) {
        Text title = displayBiggerStationName(name);
        Text switchLine = displaySwitchLine(label,previousColor);

        HBox titleLine = new HBox();
        titleLine.setSpacing(circleRadius*2);
        titleLine.getChildren().addAll(title, switchLine);
        titleLine.setAlignment(Pos.BASELINE_LEFT);

        titleLine.setPadding(new Insets(circleRadius/3,0,circleRadius/3,0));

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
     * @param stations,lineHeight,nextColor
     * Method sets the standard Size of the Line and Creates it.
     * @return Vbox with the columns of station names from optimal path
     * */
    private VBox displayChunkOfStations(ArrayList<String> stations, int lineHeight, String nextColor) {
        VBox chunk = new VBox();

        if (stations.size()>1) {
            chunk.getChildren().add(displaySmallerStationNamesImproved(stations, lineHeight));
        } else {

            Text text = new Text();
            text.setText("Empty");
            text.setFill(white);
            text.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR,0));
            int padding =lineHeight/2 - circleRadius;
            chunk.setMargin(text, new Insets(padding,0,padding ,0));

            chunk.getChildren().add(text);
        }

        if (nextColor.equals("")) {
            Text title = displayBiggerStationName(stations.get(stations.size()-1));
            chunk.getChildren().add(title);
        } else {
            HBox titleLine = displayLineLabel(stations.get(stations.size()-1),nextColor,"Switch to");
            chunk.getChildren().add(titleLine);

        }

        return chunk;
    }

    /**
     * @param stations,lineHeight passed form displayChunkOfStations
     * Calls displaySmallerStationNames if their are more then 10 station names to display
     * @return HBox of station names
     * */
    private HBox displaySmallerStationNamesImproved(List<String> stations, int lineHeight) {
        HBox names = new HBox();
        int fontHeight = circleRadius;

        int height = Math.min(stations.size(),10);
        int padding = (lineHeight*(height) - circleRadius*2 - (height)*fontHeight)/2 ;
        names.setPadding(new Insets(padding,0,padding ,0));

        int numberOfLines = 10;

        if (stations.size() > numberOfLines) {
            int diff = stations.size() - numberOfLines;
            int index = 0;
            while (diff > 0) {
                VBox column = displaySmallerStationNames(stations.subList(index, index+numberOfLines),lineHeight);
                names.getChildren().add(column);
                index += numberOfLines-1;
                diff -= index;
            }
            VBox column = displaySmallerStationNames(stations.subList(index, stations.size()),lineHeight);
            names.getChildren().add(column);

        } else {
            names.getChildren().add(displaySmallerStationNames(stations,lineHeight));
        }

        return names;
    }

    /**
     * @param stations,lineHeight passed from displaySmallerStationNamesImproved
     * Calculate the display size and spacing of overflow stations to maintain clean display
     * Calls displaySmallerStationName to format each line for display
     * @return VBox of station names
     * */
    private VBox displaySmallerStationNames(List<String> stations, int lineHeight) {
        int fontHeight = circleRadius;
        VBox names = new VBox();

        for (int i = 0; i < stations.size()-1; i++) {
            Text statName = displaySmallerStationName(stations.get(i), fontHeight);
            names.setMargin(statName, new Insets(0,0,0,circleRadius*3));
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
