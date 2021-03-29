import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
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

public class View {

    private Color background = Color.rgb(11,19,43);
    private Color blue = Color.rgb(18,126,188);
    private Color green = Color.rgb(20,158,106);
    private Color orange = Color.rgb(245,153,35);
    private Color red = Color.rgb(245,61,61);
    private Color white = Color.rgb(255,255,255);

    private HashMap<String, Color> colorMappings = new HashMap<>();
    private int circleRadius = 15;

    public View(){

        initialiseColorMappings();

    }

    public HBox createLine(ArrayList<Tuple<String, ArrayList<String>>> stations) {
        Group lines = new Group();
        Group circles = new Group();
        VBox names = new VBox();

        int x = 30;


        if (!stations.isEmpty()) {

            int currentEnd = 0;
            int chunkSize;

            int countLen = 0;

            for (Tuple<String, ArrayList<String>> line : stations) {
                if (line.second.size() > 10) {
                    countLen += 10;
                } else {
                    countLen += line.second.size();
                }

            }

            int lineLength = 500 / countLen;

            String currentColor = stations.get(0).first;
            ArrayList<String> currentLine = stations.get(0).second;

            circles.getChildren().add(makeTripleCircle(x,0,currentColor));

            HBox titleLine = displayLineLabel(currentLine.get(0),currentColor,"Take");

            names.setMargin(titleLine, new Insets(circleRadius/3,0,0,circleRadius*2));
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


    //text methods
    private VBox displayChunkOfStations(ArrayList<String> stations, int lineHeight, String nextColor) {
        VBox chunk = new VBox();

        if (stations.size()>1) {
            chunk.getChildren().add(displaySmallerStationNamesImproved(stations, lineHeight));
        } else {


            Text text = new Text();
            text.setText("Empty");
            text.setFill(white);
            text.setFont(Font.font(0));
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

    private HBox displayLineLabel(String name, String previousColor, String label) {
        Text title = displayBiggerStationName(name);
        Text switchLine = displaySwitchLine(label,previousColor);

        HBox titleLine = new HBox();
        titleLine.setSpacing(circleRadius*2);
        titleLine.getChildren().addAll(title, switchLine);
        titleLine.setAlignment(Pos.BASELINE_LEFT);

        return titleLine;
    }

    private Text displaySwitchLine(String label, String lineColor) {
        int fontHeight = circleRadius;

        Text text = new Text();
        text.setText(label + " " + lineColor);
        text.setFill(colorMappings.get(lineColor));

        text.setFont(Font.font("Arial", FontWeight.EXTRA_LIGHT, FontPosture.ITALIC,fontHeight));


        return text;
    }

    private Text displayBiggerStationName(String name) {
        int fontHeight = 3*circleRadius/2;

        Text text = new Text();
        text.setText(name.toUpperCase());
        text.setFill(white);
        text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR,fontHeight));

        return text;
    }

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

    private HBox displaySmallerStationNamesImproved(List<String> stations, int lineHeight) {
        HBox names = new HBox();

        int fontHeight = circleRadius;

        int height = Math.min(stations.size(),10);
        int padding = (lineHeight*(height) - circleRadius*2 - (height)*fontHeight)/2 + +circleRadius/height;
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

    private Text displaySmallerStationName(String name, int fontHeight) {
        Text text = new Text();
        text.setText(name);
        text.setFill(white);
        text.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR,fontHeight));

        return text;
    }

    //circle methods
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

    private Group makeDoubleCircle(double x, double y, String color1, String color2) {
        Group circleGroup = new Group();

        circleGroup.getChildren().add(makeCircle(x,y,circleRadius,"BG","BG"));
        circleGroup.getChildren().add(makeCircle(x,y,circleRadius*0.75,color1,color2));

        return circleGroup;
    }

    private Group makeTripleCircle(double x, double y, String color) {
        Group circleGroup = new Group();

        circleGroup.getChildren().add(makeCircle(x,y,circleRadius,color, color));
        circleGroup.getChildren().add(makeCircle(x,y,circleRadius*0.8,"BG","BG"));
        circleGroup.getChildren().add(makeCircle(x,y,circleRadius*0.5,color, color));


        return circleGroup;
    }



    public ArrayList<Tuple<String, ArrayList<String>>> getStations() {
        ArrayList<Tuple<String, ArrayList<String>>> stations = new ArrayList<>();

        ArrayList<String> s = new ArrayList<>();
        for (int i = 0; i< 23; i++) {
            s.add("Station GreenB "+ i);
        }

        stations.add(new Tuple<>("GreenB", s));

        ArrayList<String> s3 = new ArrayList<>();
        for (int i = 0; i< 2; i++) {
            s3.add("Station Blue "+i);
        }

//        stations.add(new Tuple<>("Blue", s3));

        ArrayList<String> s2 = new ArrayList<>();
        for (int i = 0; i< 5; i++) {
            s2.add("Station Mattapan "+i);
        }

        stations.add(new Tuple<>("Mattapan", s2));


        return stations;
    }

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
