import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    List<String> sortedStations;
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
    ImageView switchButton;

    @FXML
    ImageView circle;

    @FXML
    Text titleText1;

    @FXML
    Text titleText2;

    @FXML
    Button searchLength;

    @FXML
    Button searchTransitions;

    @FXML
    HBox startingCircles;

    @FXML
    HBox endingCircles;

    @FXML
    BorderPane displayArea;

    @FXML
    public void initialize(){
        this.pathDisplayer = new PathDisplayer();
        this.algorithmSelected = "Length";
        this.setStandardStyles();
        this.sortedStations = new ArrayList<>();
        this.setupAutofill(endDestSelector);
        this.setupAutofill(startDestSelector);

    }

    /**
     * Called by Controller setUpDropDowns()
     * @param stationColorMap initialised in Model, contains all possible Line colours
     * Activates Fade Animation of Circular Logo
     */
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

    /**
     * Called by View's customizeDropDowns()
     * @param comboBox input from firstScreenView.fxml
     * Sets the Combobox's colour according to corresponding colour in stationColourMap
     */
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

    /**
     * Enables Changing Icon Circles next to ComboBox's for startDestSelector
     */
    public void changeSelectorColourStart() {

        if (startingCircles != null ) {
            startingCircles.getChildren().remove(0, startingCircles.getChildren().size());
        }
        String selectedStation = startDestSelector.getValue();
        if (selectedStation != null && stationColorMap.containsKey(selectedStation)) {
            startingCircles.getChildren().add(displayCircles(selectedStation));
        }
    }

    /**
     * Enables Changing Icon Circles next to ComboBox's for endDestSelector
     */
    public void changeSelectorColourEnd(){
        if (endingCircles != null ) {
            endingCircles.getChildren().remove(0, endingCircles.getChildren().size());
        }

        String selectedStation = endDestSelector.getValue();
        if (selectedStation != null && stationColorMap.containsKey(selectedStation)) {
            endingCircles.getChildren().add(displayCircles(selectedStation));
        }
    }

    /**
     * Activates animation of switch Button, swaps Source and Destination fields
     */
    public void reverseButton() {
        // Button rotation
        RotateTransition rotate = new RotateTransition(Duration.seconds(1.3), switchButton);
        rotate.setByAngle(180);
        rotate.setCycleCount(1);
        rotate.play();

        String newDestination = getSourceStation();
        String newSource = getDestinationStation();

        setStartDest(newSource);
        setEndDest(newDestination);

        changeSelectorColourStart();
        changeSelectorColourEnd();

    }

    /**
     * @return flowpane with the required circles with respective styling
     */
    private Pane displayCircles(String selectedStation) {

        FlowPane pane = new FlowPane();

        for (String color : stationColorMap.get(selectedStation)) {
            pane.getChildren().add(pathDisplayer.makeTripleCircle(0,0,color,"White",true));
        }

        pane.setAlignment(Pos.CENTER);
        pane.setPrefWrapLength(70);
        pane.setPrefHeight(70);
        pane.setHgap(3.5);
        pane.setVgap(3.5);

        return pane;
    }

    public void fillStationsOptions(List<String> stations){

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

        startDestSelector.getItems().addAll(sortedStations);
        endDestSelector.getItems().addAll(sortedStations);

    }

    private void sortByIds(List<String> stations) {
        stations.sort((o1, o2) -> {
            int id1 = Integer.parseInt(o1.split(" ")[0]);
            int id2 = Integer.parseInt(o2.split(" ")[0]);
            if (id1 > id2)
                return 1;
            return -1;
        });
    }

    public void setDefaultStyleEndSelector(){
        endStationErrorMsg.setVisible(false);
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

    /**
     * Sets and Checks for appropriate Error Messages to display
     */
    public boolean stationsSelected(){
        boolean valid = true;
        String startStation = startDestSelector.getValue();
        String endStation = endDestSelector.getValue();
        String endStationError = "empty";
        String startStationError = "empty";
        startStationErrorMsg.setVisible(false);
        endStationErrorMsg.setVisible(false);



        if (startStation.equals(endStation)){
            endStationError = "Sorry, you're already there.";
        }

        if (!sortedStations.contains(startStation)) {
            startStationError = "That station doesn't exist.";
        }

        if (!sortedStations.contains(endStation)) {
            endStationError ="That station doesn't exist.";
        }

        if(startStation == null || startStation.equals("")) {
            startStationError = "Please select a starting station.";
            }

        if(endStation == null || endStation.equals("")){
            endStationError = "Please select a final station.";
        }

        if (!startStationError.equals("empty")) {
            startStationErrorMsg.setText(startStationError);
            startStationErrorMsg.setVisible(true);
            this.changeStyle(startDestSelector,"-fx-background-color", "#fff0f0");
            valid = false;
        }

        if (!endStationError.equals("empty")) {
            endStationErrorMsg.setText(endStationError);
            endStationErrorMsg.setVisible(true);
            this.changeStyle(endDestSelector,"-fx-background-color", "#fff0f0");
            valid = false;
        }

        return valid;

    }

    public void setFindButtonEventHandler(EventHandler<ActionEvent> eventHandler){
        findButton.setOnAction(eventHandler);
    }

    /**
     * Hides left hand Home screen to allow Path to be Displayed
     * Calls createLine in PathDisplayer class to display the Route,Line,Stations and Transfers
     * Calls runDisplayPathAnimation to rotate the Main Logo
     */
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
     * Rotates the Logo when path is found
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

    /**
     * Sets Left Hand Side Pane Size
     */
    public void setRoot(AnchorPane root){
        this.root = root;
        container = new BorderPane();
        container.setMinWidth(686);
        container.setMinHeight(550);
        Rectangle bg = new Rectangle(686, 700);
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
    Pane map;
    public void mapButtonEndClicked() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("BostonMetroMap.fxml"));
        map = loader.load();

        this.root.getChildren().add(map);
        map.setLayoutX(-25);
        map.setLayoutY(-25);

        mapController mapController = loader.getController();

        mapController.setView(this);
        mapController.setDestinationDirection("END");

        ObservableMap<String, Object> stationButtonsMap = loader.getNamespace();
        System.out.println(stationButtonsMap);


    }

    public void closeMap(){
//        map.getChildren().removeAll();
        this.root.getChildren().remove(map);
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

    private void setupAutofill(ComboBox<String> element){
        element.addEventHandler(KeyEvent.KEY_TYPED, t -> element.show());
        element.addEventHandler(KeyEvent.KEY_PRESSED, t -> element.show());
        element.addEventHandler(KeyEvent.KEY_RELEASED,new EventHandler<KeyEvent>(){

            private boolean moveCaretToPos = false;
            private int caretPos;

            @Override
            public void handle(KeyEvent event) {

                //Following chunk of code to handle keyboard events has been provided from https://stackoverflow.com/questions/19924852/autocomplete-combobox-in-javafx
                if (event.getCode() == KeyCode.UP) {
                    caretPos = -1;
                    if (element.getEditor().getText() != null) {
                        moveCaret(element.getEditor().getText().length());
                    }
                    return;
                } else if (event.getCode() == KeyCode.DOWN) {
                    if (!element.isShowing()) {
                        element.show();
                    }
                    caretPos = -1;
                    if (element.getEditor().getText() != null) {
                        moveCaret(element.getEditor().getText().length());
                    }
                    return;
                } else if (event.getCode() == KeyCode.BACK_SPACE) {
                    if (element.getEditor().getText() != null) {
                        moveCaretToPos = true;
                        caretPos = element.getEditor().getCaretPosition();
                    }
                } else if (event.getCode() == KeyCode.DELETE) {
                    if (element.getEditor().getText() != null) {
                        moveCaretToPos = true;
                        caretPos = element.getEditor().getCaretPosition();
                    }
                } else if (event.getCode() == KeyCode.ENTER) {
                    caretPos = element.getEditor().getCaretPosition();
                    return;
                }

                if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT || event.getCode().equals(KeyCode.SHIFT) || event.getCode().equals(KeyCode.CONTROL)
                        || event.isControlDown() || event.getCode() == KeyCode.HOME
                        || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
                    return;
                }
                //the end of borrowed chunk of code

                element.show();
                String text = element.getEditor().getText();
                List<String> filteredList = new ArrayList<>();
                for (String key : sortedStations) {
                    if (key.toLowerCase().contains(text.toLowerCase())) {
                        filteredList.add(key);
                    }
                }
                element.hide();
                element.getItems().clear();
                element.getItems().addAll(filteredList);
                int numOfStations = filteredList.size();
                element.setVisibleRowCount(Math.min(numOfStations, 10));
                element.show();
            }
            //Following method is part of the borrowed code from https://stackoverflow.com/questions/19924852/autocomplete-combobox-in-javafx
            private void moveCaret(int textLength) {
                if (caretPos == -1) {
                    element.getEditor().positionCaret(textLength);
                } else {
                    element.getEditor().positionCaret(caretPos);
                }
                moveCaretToPos = false;
            }

            //handle errors
            //new error message
            //fix styling in css

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
        startStationErrorMsg.setVisible(false);
        startDestSelector.setStyle(" -fx-font-family: Arial; -fx-background-radius: 10; -fx-background-color: ffffff; -fx-border-color: #0B132B;-fx-border-radius: 10;");
        endDestSelector.setStyle(" -fx-font-family: Arial; -fx-background-radius: 10; -fx-background-color: ffffff; -fx-border-color: #0B132B;-fx-border-radius: 10;");

        //Autofill
        startDestSelector.setEditable(true);
        startDestSelector.getEditor().setStyle(" -fx-font-family: Arial; -fx-background-radius: 10; -fx-background-color: ffffff; -fx-border-color: #ffffff;-fx-border-radius: 10;");
        endDestSelector.setEditable(true);
        endDestSelector.getEditor().setStyle(" -fx-font-family: Arial; -fx-background-radius: 10; -fx-background-color: ffffff; -fx-border-color: #ffffff;-fx-border-radius: 10;");
        startDestSelector.setFocusTraversable(false);
        endDestSelector.setFocusTraversable(false);

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
    }
}
