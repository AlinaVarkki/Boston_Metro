import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.IOException;
import java.util.*;

public class View {

    PathDisplayer pathDisplayer;
    Pane pathDisplayed;
    AnchorPane root ;
    BorderPane container;
    Map<String, List<String>> stationColorMap;
    String algorithmSelected;


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
    Text matchingStationErrorMsg;

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
    Button searchLength;

    @FXML
    Button searchTransitions;

    @FXML
    HBox startingCircles;

    @FXML
    HBox endingCircles;

    @FXML
    public void initialize(){
        pathDisplayer = new PathDisplayer();
        algorithmSelected = "Length";
        this.setStandardStyles();
    }

    public void customizeDropDowns(Map<String, List<String>> stationColorMap){
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
                                    setText(item);
                                    String label = stationColorMap.get(item).get(0);

                                    switch (label.charAt(0)) {
                                        case 'R','M' -> setTextFill(Color.RED);
                                        case 'O' -> setTextFill(Color.ORANGE);
                                        case 'B' -> setTextFill(Color.BLUE);
                                        case 'G' -> setTextFill(Color.GREEN);
                                        default -> setTextFill(Color.BLACK);
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

    public void changeSelectorColourStart() {
        String selectedStation = startDestSelector.getValue();
        if (selectedStation != null) {
            startingCircles.getChildren().add(displayCircles(selectedStation, startingCircles));
        }
    }

    public void changeSelectorColourEnd(){
        String selectedStation = endDestSelector.getValue();
        if (selectedStation != null) {
            endingCircles.getChildren().add(displayCircles(selectedStation, endingCircles));
        }
    }


    /**
     * @param circles place where you want your circles to be displayed
     * @return flowpane with the required circles
     */
    private Pane displayCircles(String selectedStation, HBox circles) {

        if (circles != null) {
            circles.getChildren().remove(0, circles.getChildren().size());
        }

        FlowPane pane = new FlowPane();

        for (String color : stationColorMap.get(selectedStation)) {
            pane.getChildren().add(pathDisplayer.makeTripleCircle(0,0,color,"White",true));
        }

        pane.setAlignment(Pos.CENTER);
        pane.setPrefWrapLength(60);
        pane.setPrefHeight(60);
        pane.setHgap(3.5);
        pane.setVgap(3.5);

        return pane;
    }

    public void fillStationsOptions(List<String> stations){

        List<String> sortedStations = new ArrayList<>();
        List<String> greenStations = new ArrayList<>();
        List<String> orangeStations = new ArrayList<>();
        List<String> redStations = new ArrayList<>();
        List<String> blueStations = new ArrayList<>();
        List<String> restStations = new ArrayList<>();

        for (String station: stations){
            switch (stationColorMap.get(station).get(0).charAt(0)) {
                case 'G' -> greenStations.add(station);
                case 'O' -> orangeStations.add(station);
                case 'R', 'M' -> redStations.add(station);
                case 'B' -> blueStations.add(station);
                default -> restStations.add(station);
            }
        }

        sortByIds(blueStations);
        sortByIds(redStations);
        sortByIds(greenStations);
        sortByIds(orangeStations);
        sortByIds(restStations);

        sortedStations.addAll(blueStations);
        sortedStations.addAll(redStations);
        sortedStations.addAll(greenStations);
        sortedStations.addAll(orangeStations);
        sortedStations.addAll(restStations);

//        startDestSelector.getItems().add("Riverside");
        startDestSelector.getItems().addAll(sortedStations);
        endDestSelector.getItems().addAll(sortedStations);

//        Collections.sort(stations);
//        startDestSelector.getItems().addAll(stations);
//        endDestSelector.getItems().addAll(stations);
    }

    private void sortByIds(List<String> stations) {
        Collections.sort(stations, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {

                Integer id1 = Integer.parseInt(o1.split(" ")[0]);
                Integer id2 = Integer.parseInt(o2.split(" ")[0]);
                if (id1 > id2)
                    return 1;
                return -1;
            }
        });
    }

    @FXML
    public void findPathBtnClicked(){

        if(startDestSelector.getValue() == null || endDestSelector.getValue() == null){
            if(startDestSelector.getValue() == null){
                startStationErrorMsg.setVisible(true);
                this.changeStyle(startDestSelector,"-fx-background-color","#fff0f0");
            }

            if(endDestSelector.getValue() == null){
                matchingStationErrorMsg.setVisible(false);
                endStationErrorMsg.setVisible(true);
                this.changeStyle(endDestSelector,"-fx-background-color","#fff0f0");
            }
        } else if (startDestSelector.getValue().equals(endDestSelector.getValue())) {
            System.out.println("startDestSelector.getValue()");
            endStationErrorMsg.setVisible(false);
            matchingStationErrorMsg.setVisible(true);
            this.changeStyle(endDestSelector,"-fx-background-color","#fff0f0");
        } else {
            //find path
            System.out.println();
            System.out.println(startDestSelector.getValue());
        }

    }

    public void setDefaultStyleEndSelector(){
        endStationErrorMsg.setVisible(false);
        matchingStationErrorMsg.setVisible(false);
        this.changeStyle(endDestSelector,"-fx-background-color", "#ffffff");
    }

    public void setDefaultStyleStartSelector(){
        startStationErrorMsg.setVisible(false);
        this.changeStyle(startDestSelector,"-fx-background-color","#ffffff");
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
                this.changeStyle(startDestSelector,"-fx-background-color", "#fff0f0");
            }

            if(endDestSelector.getValue() == null){
                matchingStationErrorMsg.setVisible(false);
                endStationErrorMsg.setVisible(true);
                this.changeStyle(endDestSelector,"-fx-background-color", "#fff0f0");
            }
            return false;
        }
        else if(startDestSelector.getValue().equals(endDestSelector.getValue())){
            endStationErrorMsg.setVisible(false);
            matchingStationErrorMsg.setVisible(true);
            this.changeStyle(endDestSelector,"-fx-background-color", "#fff0f0");
            return false;
        }
        else{
            return true;
        }
    }

    public void setFindButtonEventHandler(EventHandler<ActionEvent> eventHandler){
        findButton.setOnAction(eventHandler);
    }

    public void displayFoundPath(List<Pair<String,List<String>>> path){
        this.setTitleVisibility(false);
        if(this.pathDisplayed != null){
            container.getChildren().remove(pathDisplayed);
        }
        pathDisplayed = pathDisplayer.createLine(path);
        container.setCenter(pathDisplayed);

        runDisplayPathAnimation();
    }

    /**
     * twists the logo when path is found
     */
    private void runDisplayPathAnimation() {
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

        Stage window = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("mapView.fxml"));
        AnchorPane root = loader.load();
        window.setScene(new Scene(root, 1171.0, 746.0, Color.WHITE));
        window.show();

        mapController mapController = loader.getController();

        mapController.setView(this);
        mapController.setDestinationDirection("START");

    }

    public void mapButtonEndClicked() throws IOException {

        Stage window = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("mapView.fxml"));
        AnchorPane root = loader.load();
        window.setScene(new Scene(root, 1171.0, 746.0, Color.WHITE));
        window.show();

        mapController mapController = loader.getController();

        mapController.setView(this);
        mapController.setDestinationDirection("END");
    }

    public void setStartDest(String stationName){
        System.out.println("setting start dest" + stationName);
        startDestSelector.setValue(stationName);
    }

    public void setEndDest(String stationName){
        System.out.println("setting start dest" + stationName);
        endDestSelector.setValue(stationName);
    }

    public void setupAlgorithmSelectorEventHandler(){
        searchLength.setOnAction(actionEvent ->
        {algorithmSelected = "Length";
        this.changeStyle(searchTransitions,"-fx-background-color","#fff");
        searchTransitions.setTextFill(background);
        this.changeStyle(searchLength,"-fx-background-color","#0B132B");
        searchLength.setTextFill(Color.WHITE);

        });
        searchTransitions.setOnAction(actionEvent -> {
            algorithmSelected = "Transitions";
            this.changeStyle(searchLength,"-fx-background-color","#fff");
            searchLength.setTextFill(background);
            this.changeStyle(searchTransitions,"-fx-background-color","#0B132B");
            searchTransitions.setTextFill(Color.WHITE);
        });
    }

    public String getAlgorithmSelected(){
        return algorithmSelected;
    }

    /**
     * This method is used during initialization to standardize all styling in one place
     * **/
    private void setStandardStyles(){
        //Selection Toggle
        searchLength.setStyle("-fx-background-color: #fff; -fx-background-radius: 10;-fx-border-color: #0B132B;-fx-border-radius: 10;");
        searchLength.setTextFill(background);
        searchTransitions.setStyle("-fx-background-color: #0B132B; -fx-background-radius: 10;-fx-border-color: #0B132B;-fx-border-radius: 10;");
        searchTransitions.setTextFill(Color.WHITE);
        searchTransitions.setFont(Font.font("Arial"));
        searchLength.setFont(Font.font("Arial"));

        //Station Selectors
        endStationErrorMsg.setVisible(false);
        matchingStationErrorMsg.setVisible(false);
        startStationErrorMsg.setVisible(false);
        startDestSelector.setStyle(" -fx-font-family: Arial; -fx-background-radius: 10; -fx-background-color: ffffff; -fx-border-color: #0B132B;-fx-border-radius: 10;");
        endDestSelector.setStyle(" -fx-font-family: Arial; -fx-background-radius: 10; -fx-background-color: ffffff; -fx-border-color: #0B132B;-fx-border-radius: 10;");


    }

    /**
     * This method is used to change FXML style property while keeping the rest of the style unchanged
     * @param elem Control - FXML element we are changing the style
     * @param newProperty String - This is the FXML style tag value of which we are changing
     * @param newValue String - The new value we give to the FXML style tag
     * **/
    private void changeStyle(Control elem,String newProperty,String newValue){
        HashMap<String,String> properties = new HashMap<>();
        String style = elem.getStyle();
        String[] styleProperties = style.split(";");
        for(String pair : styleProperties){
            String[] styleProperty = pair.split(":");
            properties.put(styleProperty[0].trim(),styleProperty[1].trim());
        }

        properties.put(newProperty,newValue);

        StringBuilder newStyle =  new StringBuilder();
        for(String key : properties.keySet()){
            newStyle.append(key);
            newStyle.append(":");
            newStyle.append(properties.get(key));
            newStyle.append(";");
        }
        elem.setStyle(newStyle.toString());
        System.out.println(newStyle.toString());

    }
}
