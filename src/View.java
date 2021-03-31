import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class View {

    PathDisplayer pathDisplayer;
    Pane pathDisplayed;
    AnchorPane root ;
    BorderPane container;
    Map<String, String> stationColorMap;

    private Color background = Color.rgb(11,19,43);


    @FXML
    Button findButton;
    @FXML
    ComboBox<String> startDestSelector;
    @FXML
    ComboBox<String> endDestSelector;

    @FXML
    Text startStationErrorMsg;

    @FXML
    Text endStationErrorMsg;

    @FXML
    ImageView titleImage;

    @FXML
    ImageView yellowLine;

    @FXML
    ImageView redLine;

    @FXML
    ImageView greenLine;

    @FXML
    ImageView blueLine;

    @FXML
    ImageView circle;

    @FXML
    Text titleText1;

    @FXML
    Text titleText2;

    @FXML
    ImageView circleStart;

    @FXML
    ImageView circleEnd;

    @FXML
    public void initialize(){
        pathDisplayer = new PathDisplayer();
    }

    public void customizeDropDowns(Map<String, String> stationColorMap){
        this.stationColorMap = stationColorMap;
        setOptionsColours(startDestSelector);
        setOptionsColours(endDestSelector);

        //tittleText1 fade in
        FadeTransition fadeIn1 = new FadeTransition(Duration.seconds(4), titleText1);
        fadeIn1.setFromValue(0.0);
        fadeIn1.setToValue(1.0);
        fadeIn1.play();

        //tittleText2 fade in
        FadeTransition fadeIn2 = new FadeTransition(Duration.seconds(4), titleText2);
        fadeIn2.setFromValue(0.0);
        fadeIn2.setToValue(1.0);
        fadeIn2.play();

        //yellow line fade in
        FadeTransition fadeInY = new FadeTransition(Duration.seconds(2), yellowLine);
        fadeInY.setFromValue(0.0);
        fadeInY.setToValue(1.0);
        fadeInY.play();

        //red line fade in
        FadeTransition fadeInR = new FadeTransition(Duration.seconds(3), redLine);
        fadeInR.setFromValue(0.0);
        fadeInR.setToValue(1.0);
        fadeInR.play();

        //green line fade in
        FadeTransition fadeInG = new FadeTransition(Duration.seconds(4), greenLine);
        fadeInG.setFromValue(0.0);
        fadeInG.setToValue(1.0);
        fadeInG.play();

        //blue line fade in
        FadeTransition fadeInB = new FadeTransition(Duration.seconds(5), blueLine);
        fadeInB.setFromValue(0.0);
        fadeInB.setToValue(1.0);
        fadeInB.play();

        //circle fade in
        FadeTransition fadeInC = new FadeTransition(Duration.seconds(6), circle);
        fadeInC.setFromValue(0.0);
        fadeInC.setToValue(1.0);
        fadeInC.play();

    }

    public void setOptionsColours(ComboBox<String> comboBox){

        comboBox.setCellFactory(
                new Callback<ListView<String>, ListCell<String>>() {
                    @Override public ListCell<String> call(ListView<String> param) {
                        final ListCell<String> cell = new ListCell<>() {
                            {
                                super.setPrefWidth(100);
                            }
                            @Override public void updateItem(String item,
                                                             boolean empty) {
                                super.updateItem(item, empty);
                                if (item != null) {
                                    setText(item + "   🔴");
                                    String label = stationColorMap.get(item);

                                    if (label.equals("Red") || label.equals("RedA") ||label.equals("RedB")) {
                                        setTextFill(Color.RED);
                                    }
                                    else if (label.equals("Orange")){
                                        setTextFill(Color.ORANGE);
                                    }else if (label.equals("Blue")){
                                        setTextFill(Color.BLUE);
                                    }
                                    else if (label.equals("Green") || label.equals("GreenB") || label.equals("GreenC") || label.equals("GreenD") ||label.equals("GreenE")){
                                        setTextFill(Color.GREEN);
                                    }else{
                                        setTextFill(Color.BLACK);
                                    }
                                }
                                else {
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }

                });
    }

//    public void changeSelectorColourStart(){
//        String selectedStation = startDestSelector.getValue();
////        if(selectedStation != null){
////            if(stationColorMap.get(selectedStation).equals("Green")){
////                startDestSelector.setStyle("-fx-background-radius: 10; -fx-background-color: #e1ffdb; -fx-border-color: #0B132B;");
////            }else if(stationColorMap.get(selectedStation).equals("Red")){
////                startDestSelector.setStyle("-fx-background-radius: 10; -fx-background-color: #fcc5ce; -fx-border-color: #0B132B;");
////            }else if(stationColorMap.get(selectedStation).equals("Orange")){
////                startDestSelector.setStyle("-fx-background-radius: 10; -fx-background-color: #fce6c5; -fx-border-color: #0B132B;");
////            }else if(stationColorMap.get(selectedStation).equals("Blue")){
////                startDestSelector.setStyle("-fx-background-radius: 10; -fx-background-color: #9cafff; -fx-border-color: #0B132B;");
////            }
////        }
//    }

//    public void changeSelectorColourEnd(){
//        String selectedStation = endDestSelector.getValue();
//        if(selectedStation != null){
//            if(stationColorMap.get(selectedStation).equals("Green")){
//                endDestSelector.setStyle("-fx-background-radius: 10; -fx-background-color: #e1ffdb; -fx-border-color: #0B132B;");
//            }else if(stationColorMap.get(selectedStation).equals("Red")){
//                endDestSelector.setStyle("-fx-background-radius: 10; -fx-background-color: #fcc5ce; -fx-border-color: #0B132B;");
//            }else if(stationColorMap.get(selectedStation).equals("Orange")){
//                endDestSelector.setStyle("-fx-background-radius: 10; -fx-background-color: #fce6c5; -fx-border-color: #0B132B;");
//            }else if(stationColorMap.get(selectedStation).equals("Blue")){
//                endDestSelector.setStyle("-fx-background-radius: 10; -fx-background-color: #9cafff; -fx-border-color: #0B132B;");
//            }
//        }
//    }

    public void fillStationsOptions(List<String> stations){
        startDestSelector.getItems().addAll(stations);
        endDestSelector.getItems().addAll(stations);
        startDestSelector.setStyle("-fx-font-family: Arial; -fx-background-radius: 10; -fx-background-color: ffffff; -fx-border-color: #0B132B;");
        endDestSelector.setStyle("-fx-font-family: Arial; -fx-background-radius: 10; -fx-background-color: ffffff; -fx-border-color: #0B132B;");


        List<String> sortedStations = new ArrayList<>();
        List<String> greenStations = new ArrayList<>();
        List<String> orangeStations = new ArrayList<>();
        List<String> redStations = new ArrayList<>();
        List<String> blueStations = new ArrayList<>();
        List<String> restStations = new ArrayList<>();

        for (String station: stations){
            if(stationColorMap.get(station).equals("Green")){
                greenStations.add(station);
            }else if(stationColorMap.get(station).equals("Orange")){
                orangeStations.add(station);
            }else if(stationColorMap.get(station).equals("Red")){
                redStations.add(station);
            }else if(stationColorMap.get(station).equals("Blue")){
                blueStations.add(station);
            }else{
                restStations.add(station);
            }
        }

        sortedStations.addAll(blueStations);
        sortedStations.addAll(redStations);
        sortedStations.addAll(greenStations);
        sortedStations.addAll(orangeStations);
        sortedStations.addAll(restStations);

//        startDestSelector.getItems().add("Riverside");
        startDestSelector.getItems().addAll(sortedStations);
        endDestSelector.getItems().addAll(sortedStations);
    }

    @FXML
    public void findPathBtnClicked(){

        if(startDestSelector.getValue() == null || endDestSelector.getValue() == null){
            if(startDestSelector.getValue() == null){
                startStationErrorMsg.setVisible(true);
                startDestSelector.setStyle(" -fx-background-radius: 10; -fx-background-color: #fff0f0; -fx-border-color: #0B132B;");
            }

            if(endDestSelector.getValue() == null){
                endStationErrorMsg.setVisible(true);
                endDestSelector.setStyle(" -fx-background-radius: 10; -fx-background-color: #fff0f0; -fx-border-color: #0B132B;");
            }
        }else{
            //find path
            System.out.println();
            System.out.println(startDestSelector.getValue());
        }

    }

    public void setDefaultStyleEndSelector(){
        endStationErrorMsg.setVisible(false);
        endDestSelector.setStyle("-fx-font-family: Arial; -fx-background-radius: 10; -fx-background-color: ffffff; -fx-border-color: #0B132B;");
    }

    public void setDefaultStyleStartSelector(){
        startStationErrorMsg.setVisible(false);
        startDestSelector.setStyle(" -fx-font-family: Arial; -fx-background-radius: 10; -fx-background-color: ffffff; -fx-border-color: #0B132B;");
    }

    public String getDestinationStation(){
        return endDestSelector.getValue();
    }

    public String getSourceStation(){
        return startDestSelector.getValue();
    }

    public boolean stationsSelected(){
        if(startDestSelector.getValue() == null || endDestSelector.getValue() == null){
            if(startDestSelector.getValue() == null){
                startStationErrorMsg.setVisible(true);
                startDestSelector.setStyle(" -fx-background-radius: 10; -fx-background-color: #fff0f0; -fx-border-color: #0B132B;");
            }

            if(endDestSelector.getValue() == null){
                endStationErrorMsg.setVisible(true);
                endDestSelector.setStyle(" -fx-background-radius: 10; -fx-background-color: #fff0f0; -fx-border-color: #0B132B;");
            }
            return false;
        }else{
            return true;
        }
    }

    public void setFindButtonEventHandler(EventHandler<ActionEvent> eventHandler){
        findButton.setOnAction(eventHandler);
    }

    public void displayFoundPath(List<Tuple<String,List<String>>> path){
        this.setTitleVisibility(false);
        if(this.pathDisplayed != null){
            container.getChildren().remove(pathDisplayed);
        }
        pathDisplayed = pathDisplayer.createLine(path);
//        root.getChildren().add(pathDisplayed);

        container.setCenter(pathDisplayed);

        //yellow line rotation
        RotateTransition rtY = new RotateTransition(Duration.seconds(2), yellowLine);
        rtY.setByAngle(360);
        rtY.setCycleCount(1);
        //rtY.setAutoReverse(true);
        rtY.play();

        //red line rotation
        RotateTransition rtR = new RotateTransition(Duration.seconds(2), redLine);
        rtR.setByAngle(360);
        rtR.setCycleCount(1);
        //rtR.setAutoReverse(true);
        rtR.play();

        //green line rotation
        RotateTransition rtG = new RotateTransition(Duration.seconds(2), greenLine);
        rtG.setByAngle(360);
        rtG.setCycleCount(1);
        //rtG.setAutoReverse(true);
        rtG.play();

        //green line rotation
        RotateTransition rtB = new RotateTransition(Duration.seconds(2), blueLine);
        rtB.setByAngle(360);
        rtB.setCycleCount(1);
        //rtB.setAutoReverse(true);
        rtB.play();

        //circle rotation
        RotateTransition rtC = new RotateTransition(Duration.seconds(2), circle);
        rtC.setByAngle(360);
        rtC.setCycleCount(1);
        //rtC.setAutoReverse(true);
        rtC.play();

    }

    public void setTitleVisibility(boolean visible){
        titleImage.setVisible(visible);
        titleText1.setVisible(visible);
        titleText2.setVisible(visible);
    }

    public void setRoot(AnchorPane root){
        this.root = root;
        container = new BorderPane();
        container.setMinWidth(686);
        container.setMinHeight(550);
        Rectangle bg = new Rectangle(686, 550);
        bg.setFill(background);
        root.getChildren().add(container);
        root.getChildren().add(bg);
        bg.toBack();

    }


    public void mapButtonStartClicked() throws IOException {

        Stage stage = new Stage();

        System.out.println("clicked");
        Parent root = FXMLLoader.load(getClass().getResource("mapView.fxml"));

        Scene scene = new Scene(root,  1171.0, 746.0);

        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void getName(ActionEvent event){
        Button button = (Button) event.getSource();

        String stationName = button.getId();

        //button ids used to identify stations cannot use '/' so names containing it don't have the full name assignes to it
        if(stationName.equals("Hynes")){
            stationName = "Hynes/ICA";
        }else if(stationName.equals("Charles")){
            stationName = "Charles/MGH";
        }else if(stationName.equals("JFK")){
            stationName = "JFK/UMass";
        }else if(stationName.equals("BackBay")){
            stationName = "BackBay/SouthEnd";
        }


        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
        System.out.println(stationName); // prints out button's text
//        startDestSelector.setValue(stationName);
//        startDestSelector.setId(stationName);
    }
}
