import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Main extends Application {

    private Color background = Color.rgb(11,19,43);
    private Color blue = Color.rgb(18,126,188);
    private Color green = Color.rgb(20,158,106);
    private Color orange = Color.rgb(245,153,35);
    private Color red = Color.rgb(245,61,61);
    private Color white = Color.rgb(255,255,255);
    private HashMap<String, Color> colorMappings = new HashMap<>();
    private int circleRadius = 10;



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        initialiseColorMappings();


//        Parent root = FXMLLoader.load(getClass().getResource("javafx.fxml"));
        VBox root = new VBox();
        root.setSpacing(20);
        root.setStyle("-fx-background-color: #0B132B;");

        stage.setTitle("Boston Metro System");



        Scene scene = new Scene(root, 600, 600, background);

        Button btn = new Button("Hello");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
//                ArrayList<Text> textStations = printOutColoredStations(getStations());
//                for (Text station : textStations) {
//                    root.getChildren().add(station);
//                }

                HBox line = createLine(getStations());
                root.getChildren().add(line);

            }
        });

        root.getChildren().add(btn);



        stage.setScene(scene);
//        stage.initStyle(StageStyle.UTILITY);


        stage.show();
    }



    public ArrayList<Text> printOutColoredStations(ArrayList<Tuple<String, String>> stations) {
        ArrayList<Text> stationNames = new ArrayList<>();
        for (Tuple<String,String> station : stations) {
            Text text = new Text();
            text.setText(station.first);
            switch (station.second) {
                case "Red":
                    text.setFill(red);
                    break;
                case "Blue":
                    text.setFill(blue);
                    break;
                case "Orange":
                    text.setFill(orange);
                    break;
                default:
                    text.setFill(green);
                    break;
            }
            stationNames.add(text);
        }

        return stationNames;
    }

    public HBox createLine(ArrayList<Tuple<String, String>> stations) {
        Group lines = new Group();
        Group circles = new Group();
        VBox names = new VBox();

        int x = 30;

        if (!stations.isEmpty()) {

            int chunkSize = 0;
            int lineLength = 550 / stations.size();
            Tuple<String, String> previous = stations.get(0);
//            names.setSpacing(lineLength);

            circles.getChildren().add(makeTripleCircle(x,0,previous.second));

            for (int i = 1; i < stations.size(); i++) {

                Tuple<String, String> current = stations.get(i);

                if (!previous.second.equals(current.second) || i == stations.size() - 1) {


                    double start = (i-1)*lineLength - chunkSize;
                    double end = i*lineLength;
                    int arrStart = i - chunkSize/lineLength -1;

                    if (i != stations.size() - 1) {
                        circles.getChildren().add(makeDoubleCircle(x,end,previous.second,current.second));

                    } else {
                        circles.getChildren().add(makeTripleCircle(x,end,previous.second));


                    }


                    VBox chunkOfStations = displayChunkOfStations(stations.subList(arrStart,i), lineLength);
                    names.setMargin(chunkOfStations, new Insets(circleRadius/3,0,0,circleRadius*2));
                    names.getChildren().add(chunkOfStations);

                    Line line = new Line(x,start,x,end);
                    line.setStroke(colorMappings.get(previous.second));
                    line.setStyle("-fx-stroke-width: 5;");
                    lines.getChildren().add(line);


                    chunkSize = 0;
                } else {
                    chunkSize += lineLength;
                }

                previous = current;

            }

            Text name =displayBiggerStationName(stations.get(stations.size()-1).first, lineLength);
            names.setMargin(name, new Insets(-circleRadius/3,0,0,circleRadius*2));
            names.getChildren().add(name);


        }

        Group finalGroup = new Group();
        finalGroup.getChildren().add(lines);
        finalGroup.getChildren().add(circles);

        HBox finalBox = new HBox();
        finalBox.getChildren().add(finalGroup);
        finalBox.getChildren().add(names);

        return finalBox;
    }


    private VBox displayChunkOfStations(List<Tuple<String,String>> stations, int lineHeight) {
        VBox chunk = new VBox();
//        chunk.setSpacing(lineHeight);

        Text title = displayBiggerStationName(stations.get(0).first, lineHeight);
        chunk.getChildren().add(title);

        if (stations.size()>1) {
            chunk.getChildren().add(displaySmallerStationNames(stations, lineHeight));
        }

        return chunk;
    }

    private Text displayBiggerStationName(String name, int lineHeight) {
        int fontHeight = circleRadius*2;

        Text text = new Text();
        text.setText(name.toUpperCase());
        text.setFill(white);
        text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR,fontHeight));

        return text;
    }

    private VBox displaySmallerStationNames(List<Tuple<String,String>> stations, int lineHeight) {
        int fontHeight = 3*circleRadius/2;
        VBox names = new VBox();
        int padding = (lineHeight*(stations.size()) - circleRadius*2 - (stations.size())*fontHeight)/2;

        names.setPadding(new Insets(padding,0,padding,0));

        for (int i = 1; i < stations.size(); i++) {
            Text statName = displaySmallerStationName(stations.get(i).first, fontHeight);
            names.setMargin(statName, new Insets(0,0,0,circleRadius*3));
            names.getChildren().add(statName);
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

        circleGroup.getChildren().add(makeCircle(x,y,circleRadius*1.5,"BG","BG"));
        circleGroup.getChildren().add(makeCircle(x,y,circleRadius*1.05,color1,color2));

        return circleGroup;
    }

    private Group makeTripleCircle(double x, double y, String color) {
        Group circleGroup = new Group();

        circleGroup.getChildren().add(makeCircle(x,y,circleRadius*1.5,color, color));
        circleGroup.getChildren().add(makeCircle(x,y,circleRadius*1.2,"BG","BG"));
        circleGroup.getChildren().add(makeCircle(x,y,circleRadius*0.75,color, color));


        return circleGroup;
    }



    public ArrayList<Tuple<String, String>> getStations() {
        ArrayList<Tuple<String, String>> stations = new ArrayList<>();


        stations.add(new Tuple("Start", "Orange"));
        stations.add(new Tuple("station", "Orange"));

        stations.add(new Tuple("Change 1", "Green"));
        stations.add(new Tuple("station1", "Green"));
        stations.add(new Tuple("station2", "Green"));
        stations.add(new Tuple("station3", "Green"));
//        stations.add(new Tuple("station4", "Green"));
//        stations.add(new Tuple("station5", "Green"));
//        stations.add(new Tuple("station6", "Green"));
//        stations.add(new Tuple("station7", "Green"));
//        stations.add(new Tuple("station8", "Green"));
//        stations.add(new Tuple("station9", "Green"));
//        stations.add(new Tuple("station10", "Green"));
//        stations.add(new Tuple("station11", "Green"));
//        stations.add(new Tuple("station12", "Green"));
//        stations.add(new Tuple("station13", "Green"));
//        stations.add(new Tuple("station14", "Green"));
//        stations.add(new Tuple("station15", "Green"));
//        stations.add(new Tuple("station5", "Green"));
//        stations.add(new Tuple("station6", "Green"));
//        stations.add(new Tuple("station7", "Green"));
//        stations.add(new Tuple("station8", "Green"));
//        stations.add(new Tuple("station9", "Green"));
//        stations.add(new Tuple("station10", "Green"));
//        stations.add(new Tuple("station11", "Green"));
//        stations.add(new Tuple("station12", "Green"));
//        stations.add(new Tuple("station13", "Green"));
//        stations.add(new Tuple("station14", "Green"));
//        stations.add(new Tuple("station15", "Green"));

        stations.add(new Tuple("Change 2", "Blue"));
        stations.add(new Tuple("station", "Blue"));
        stations.add(new Tuple("station", "Blue"));
        stations.add(new Tuple("station", "Blue"));

        stations.add(new Tuple("End", "Blue"));


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


// [(station, color)]

