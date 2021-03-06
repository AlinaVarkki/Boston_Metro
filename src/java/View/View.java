package View;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.*;

public class View {
    AnchorPane root;
    BorderPane container;

    PathDisplayer pathDisplayer;
    Pane mapPath;
    Pane pathDisplayed;

    Map<String, List<String>> stationColorMap;
    List<String> sortedStations;
    Map<String, String> idsToStations;
    Map<String, double[]> stationToCoords;

    String algorithmSelected;

    Pane map;
    MapView mapContr;


    private final Color background = Color.rgb(11, 19, 43);

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
    public void initialize() {
        this.pathDisplayer = new PathDisplayer();
        this.algorithmSelected = "Length";
        this.sortedStations = new ArrayList<>();
        this.setupAutofill(endDestSelector);
        this.setupAutofill(startDestSelector);

    }

    /**
     * Called by Controller setUpDropDowns()
     *
     * @param stationColorMap initialised in Metro.Model, contains all possible Line colours
     *                        Activates Fade Animation of Circular Logo and the Title
     */
    public void customizeDropDowns(Map<String, List<String>> stationColorMap) {
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
     * Called by View.View's customizeDropDowns()
     *
     * @param comboBox Combobox with the list of stations
     * Sets colour of every station in the dropdown list according to which line passes through it
     */
    public void setOptionsColours(ComboBox<String> comboBox) {

        comboBox.setCellFactory(
                new Callback<>() {
                    @Override
                    public ListCell<String> call(ListView<String> param) {
                        final ListCell<String> cell = new ListCell<>() {
                            {
                                super.setPrefWidth(100);
                            }

                            @Override
                            public void updateItem(String item,
                                                   boolean empty) {
                                super.updateItem(item, empty);
                                if (item != null) {
                                    setText(item);
                                    String label = stationColorMap.get(item).get(0);

                                    switch (label.charAt(0)) {
                                        case 'R', 'M' -> setTextFill(Color.RED);
                                        case 'O' -> setTextFill(Color.ORANGE);
                                        case 'B' -> setTextFill(Color.BLUE);
                                        case 'G' -> setTextFill(Color.GREEN);
                                        default -> setTextFill(Color.BLACK);
                                    }
                                } else {
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

        if (startingCircles != null) {
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
    public void changeSelectorColourEnd() {
        if (endingCircles != null) {
            endingCircles.getChildren().remove(0, endingCircles.getChildren().size());
        }

        String selectedStation = endDestSelector.getValue();
        if (selectedStation != null && stationColorMap.containsKey(selectedStation)) {
            endingCircles.getChildren().add(displayCircles(selectedStation));
        }
    }

    /**
     * Creates the circles representing lines that pass through the station given to the method
     * @param selectedStation station to display circles for
     * @return flowpane with the required circles with respective styling
     */
    private Pane displayCircles(String selectedStation) {

        FlowPane pane = new FlowPane();

        for (String color : stationColorMap.get(selectedStation)) {
            pane.getChildren().add(pathDisplayer.makeTripleCircle(0, 0, 8, color, "OffWhite", true));
        }

        pane.setAlignment(Pos.CENTER);
        pane.setPrefWrapLength(55);
        pane.setPrefHeight(55);
        pane.setHgap(3.5);
        pane.setVgap(3.5);

        return pane;
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

        startDestSelector.setValue(newSource);
        endDestSelector.setValue(newDestination);

        changeSelectorColourStart();
        changeSelectorColourEnd();

    }

    /**
     * @param stations all stations
     * sorts stations, by color and index and populates startDestSelector and endDestSelector
     */
    public void fillStationsOptions(List<String> stations) {

        List<String> greenStations = new ArrayList<>();
        List<String> orangeStations = new ArrayList<>();
        List<String> redStations = new ArrayList<>();
        List<String> blueStations = new ArrayList<>();
        List<String> restStations = new ArrayList<>();

        for (String station : stations) {
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

    /**
     * @param stations list of stations
     * sorts given station names by indexes
     */
    private void sortByIds(List<String> stations) {
        stations.sort((o1, o2) -> {
            int id1 = Integer.parseInt(o1.split(" ")[0]);
            int id2 = Integer.parseInt(o2.split(" ")[0]);
            if (id1 > id2)
                return 1;
            return -1;
        });
    }

    /**
     * This method sets the background of end selector to white, in case errors have been displayed
     */
    public void setDefaultStyleEndSelector() {
        endStationErrorMsg.setVisible(false);
        this.changeStyle(endDestSelector, "-fx-background-color", "#ffffff");
    }

    /**
     * This method sets the background of start selector to white, in case errors have been displayed
     */
    public void setDefaultStyleStartSelector() {
        startStationErrorMsg.setVisible(false);
        this.changeStyle(startDestSelector, "-fx-background-color", "#ffffff");
    }

    /**
     * Return the end station selected
     * @return The selected station from end station selector
     */
    public String getDestinationStation() {
        return endDestSelector.getValue();
    }

    /**
     * Return the start station selected
     * @return The selected station from start station selector
     */
    public String getSourceStation() {
        return startDestSelector.getValue();
    }

    /**
     * Used to display the proper error messages and checks for validity of the input
     * @return boolean whether the stations have been selected correctly
     */
    public boolean stationsSelected() {
        boolean valid = true;
        String startStation = startDestSelector.getValue();
        String endStation = endDestSelector.getValue();
        String endStationError = "empty";
        String startStationError = "empty";
        startStationErrorMsg.setVisible(false);
        endStationErrorMsg.setVisible(false);

        if (startStation != null && startStation.equals(endStation)) {
            endStationError = "Sorry, you're already there.";
        }

        if (!sortedStations.contains(startStation)) {
            startStationError = "That station doesn't exist.";
        }

        if (!sortedStations.contains(endStation)) {
            endStationError = "That station doesn't exist.";
        }

        if (startStation == null || startStation.equals("")) {
            startStationError = "Please select a starting station.";
        }

        if (endStation == null || endStation.equals("")) {
            endStationError = "Please select a final station.";
        }

        if (!startStationError.equals("empty")) {
            startStationErrorMsg.setText(startStationError);
            startStationErrorMsg.setVisible(true);
            this.changeStyle(startDestSelector, "-fx-background-color", "#fff0f0");
            valid = false;
        }

        if (!endStationError.equals("empty")) {
            endStationErrorMsg.setText(endStationError);
            endStationErrorMsg.setVisible(true);
            this.changeStyle(endDestSelector, "-fx-background-color", "#fff0f0");
            valid = false;
        }

        return valid;

    }

    /**
     * Used so the Controller can setup event handler on the Find Button
     * @param eventHandler handling press of Find Path button
     */
    public void setFindButtonEventHandler(EventHandler<ActionEvent> eventHandler) {
        findButton.setOnAction(eventHandler);
    }

    /**
     * Hides left hand Home screen to allow Path to be Displayed
     * Calls createLine in View.PathDisplayer class to display the Route,Line,Stations and Transfers
     * Sets up the Toggle switch and its event handler to switch between the two types of presentation
     * Calls runDisplayPathAnimation to rotate the Main Logo
     */
    public void displayFoundPath(List<Pair<String, List<String>>> path) {
        if (root.getChildren().contains(map)) {
            this.closeMap();
        }
        this.setTitleVisibility(false);
        container.getChildren().remove(mapPath);
        container.setCenter(null);

        this.mapPath = this.createMap(path);
        this.pathDisplayed = pathDisplayer.createLine(path);

        runDisplayPathAnimation();

        ToggleSlider togglik = new ToggleSlider();

        container.getChildren().add(togglik);
        togglik.setLayoutX(20);
        togglik.setLayoutY(10);

        container.setCenter(pathDisplayed);
        togglik.toFront();


        togglik.setClickHandler(event -> {
            if (container.getChildren().contains(pathDisplayed)) {
                container.setCenter(null);
                container.getChildren().add(mapPath);
                togglik.runSwitchOn();
                togglik.toFront();
            } else if (container.getChildren().contains(mapPath)) {
                container.getChildren().remove(mapPath);
                container.setCenter(pathDisplayed);
                togglik.runSwitchOn();
                togglik.toFront();
            }
        });

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

    /**
     * This method is used to hide or show the Title image and Text
     * @param visible boolean, true to display the title adn false to hide
     */
    public void setTitleVisibility(boolean visible) {
        titleImage.setVisible(visible);
        titleText1.setVisible(visible);
        titleText2.setVisible(visible);
    }

    /**
     * Sets Left Hand Side Pane Size and the main window
     * @param root the main window to display in
     */
    public void setRoot(AnchorPane root) {
        this.root = root;
        container = new BorderPane();
        container.setMinWidth(795);
        container.setMinHeight(550);
        Rectangle bg = new Rectangle(795, 700);
        bg.setFill(background);
        root.getChildren().add(container);
        root.getChildren().add(bg);
        bg.toBack();

    }

    public void setupMap() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BostonMetroMap.fxml"));

            System.out.println("loading map");

            map = loader.load();
            map.setLayoutX(-25);
            map.setLayoutY(-25);


            mapContr = loader.getController();

            mapContr.setView(this);
            mapContr.setDestinationDirection("END");

            ObservableMap<String, Object> stationButtonsMap = loader.getNamespace();
            Map<String, double[]> coordinateMap = new HashMap<>();
            for (String id : this.idsToStations.keySet()) {
                String id2 = "a" + id;
                String station = idsToStations.get(id);
                Button button = (Button) stationButtonsMap.get(id2);
                if (button != null) {
                    double x = button.getLayoutX();
                    double y = button.getLayoutY();
                    double[] coordinates = {x, y};
                    coordinateMap.put(station, coordinates);
                }

            }
            this.stationToCoords = coordinateMap;

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * This method is used as event handler for the button opening map for the selection of start station
     * It will open or close the map depending on the situation and tell the map to place the selected station
     * into the start selector
     */
    public void mapButtonStartClicked() {
        if (root.getChildren().contains(map)) {
            if (this.mapContr.getDestinationDirection().equals("START")) {
                this.closeMap();
            } else {
                this.mapContr.setDestinationDirection("START");
            }
        } else {
            this.mapContr.setDestinationDirection("START");
            this.root.getChildren().add(map);
        }


    }

    /**
     * This method is used as event handler for the button opening map for the selection of end station
     * It will open or close the map depending on the situation and tell the map to place the selected station
     * into the end selector
     */
    public void mapButtonEndClicked() {
        if (root.getChildren().contains(map)) {
            if (this.mapContr.getDestinationDirection().equals("END")) {
                this.closeMap();
            } else {
                this.mapContr.setDestinationDirection("END");
            }
        } else {
            this.mapContr.setDestinationDirection("END");
            this.root.getChildren().add(map);
        }
    }

    /**
     * This method is used to hide the map selector
     */
    public void closeMap() {
        this.root.getChildren().remove(map);
    }

    /**
     * This method will put the required station in the start station selector after it has been selected on the map selector
     * @param stationId Metro.Station Id
     */
    public void setStartDest(String stationId) {
        String stationName = this.idsToStations.get(stationId);
        startDestSelector.setValue(stationName);
    }

    /**
     * This method will put the required station in the end station selector after it has been selected on the map selector
     * @param stationId Metro.Station Id
     */
    public void setEndDest(String stationId) {
        String stationName = this.idsToStations.get(stationId);
        endDestSelector.setValue(stationName);
    }

    /**
     * @param path to be displayed on the map
     * @return Pane with a map and a line drawn across all appropriate stations
     */
    private Pane createMap(List<Pair<String, List<String>>> path) {
        Pane mapView = new Pane();
        ImageView map = new ImageView("Images/metroMap.png");
        mapView.getChildren().add(map);
        map.setFitHeight(820);
        map.setPreserveRatio(true);
        map.setLayoutX(-25);
        map.setLayoutY(-25);

        String[] stationsAroundCorners = {"30 Downtown Crossing", "28 State",
                "20 North Station", "22 Haymarket",
                "41 Copley", "53 Prudential",
                "33 South Station", "60 Broadway",
                "47 Kenmore", "54 St.Mary's Street",
                "98 JFK/UMass", "100 Savin Hill", "120 North Quincy"};

        List<Double> points = new ArrayList<>();

        for (int line = 0; line < path.size(); line++) {
            Pair<String, List<String>> currentLine = path.get(line);

            List<String> stations = currentLine.getValue();
            String color = currentLine.getKey();

            for (int i = 0; i < stations.size(); i++) {
                String currStation = stations.get(i);

                double[] coords = this.stationToCoords.get(currStation);
                if (coords != null) {
                    points.add(coords[0] - 20);
                    points.add(coords[1] - 20);
                }

                if (Arrays.asList(stationsAroundCorners).contains(currStation)) {
                    if (i + 1 < stations.size()) {
                        String nextStation = stations.get(i + 1);
                        double[] midCoords = getMidCoords(currStation, nextStation, color);
                        if (midCoords[0] != 0) {
                            points.add(midCoords[0] - 20);
                            points.add(midCoords[1] - 20);
                        }
                    } else if (line + 1 < path.size()) {
                        String nextStation = path.get(line + 1).getValue().get(0);
                        double[] midCoords = getMidCoords(currStation, nextStation, color);
                        if (midCoords[0] != 0) {
                            points.add(midCoords[0] - 20);
                            points.add(midCoords[1] - 20);
                        }
                    }
                }
            }
        }

        Double[] allpoints = new Double[points.size()];
        allpoints = points.toArray(allpoints);

        Polyline line = new Polyline();
        line.getPoints().addAll(allpoints);
        line.setStyle("-fx-stroke-width: 12;");
        line.setOpacity(0.75);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.setStrokeLineJoin(StrokeLineJoin.ROUND);
        line.setStrokeType(StrokeType.CENTERED);
        line.setStroke(Color.rgb(244, 244, 244));

        mapView.getChildren().add(line);

        return mapView;

    }

    /**
     * This method will setup the event handlers for the algorithm selectors
     **/
    public void setupAlgorithmSelectorEventHandler() {
        searchLength.setOnAction(actionEvent ->
        {
            algorithmSelected = "Length";
            this.changeStyle(searchTransitions, "-fx-background-color", "#fff");
            searchTransitions.setTextFill(background);
            this.changeStyle(searchLength, "-fx-background-color", "#0B132B");
            searchLength.setTextFill(Color.WHITE);

        });
        searchTransitions.setOnAction(actionEvent -> {
            algorithmSelected = "Transitions";
            this.changeStyle(searchLength, "-fx-background-color", "#fff");
            searchLength.setTextFill(background);
            this.changeStyle(searchTransitions, "-fx-background-color", "#0B132B");
            searchTransitions.setTextFill(Color.WHITE);
        });
    }

    /**
     * This method will setup event handlers on Key Events for the dropdown menu with stations
     * @param element dropdown menu element
     */
    private void setupAutofill(ComboBox<String> element) {
        element.addEventHandler(KeyEvent.KEY_TYPED, t -> element.show());
        element.addEventHandler(KeyEvent.KEY_PRESSED, t -> element.show());
        element.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

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

        });

    }

    /**
     * Method used to decide which type of search does the user want to use
     * @return the selected algorithm, either "Length" or "Transitions"
     */
    public String getAlgorithmSelected() {
        return algorithmSelected;
    }

    /**
     * This method is used during initialization to standardize all styling in one place
     **/
    public void setStandardStyles() {
        //Selection Toggle
        searchTransitions.setStyle("-fx-background-color: #fff; -fx-background-radius: 10;-fx-border-color: #0B132B;-fx-border-radius: 10;");
        searchTransitions.setTextFill(background);
        searchLength.setStyle("-fx-background-color: #0B132B; -fx-background-radius: 10;-fx-border-color: #0B132B;-fx-border-radius: 10;");
        searchLength.setTextFill(Color.WHITE);
        searchTransitions.setFont(Font.font("Arial"));
        searchLength.setFont(Font.font("Arial"));

        //Metro.Station Selectors
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
     * @param elem        Control - FXML element we are changing the style
     * @param newProperty String - This is the FXML style tag value of which we are changing
     * @param newValue    String - The new value we give to the FXML style tag
     **/
    private void changeStyle(Control elem, String newProperty, String newValue) {
        HashMap<String, String> properties = new HashMap<>();
        String style = elem.getStyle();
        String[] styleProperties = style.split(";");
        for (String pair : styleProperties) {
            String[] styleProperty = pair.split(":");
            properties.put(styleProperty[0].trim(), styleProperty[1].trim());
        }

        properties.put(newProperty, newValue);

        StringBuilder newStyle = new StringBuilder();
        for (String key : properties.keySet()) {
            newStyle.append(key);
            newStyle.append(":");
            newStyle.append(properties.get(key));
            newStyle.append(";");
        }
        elem.setStyle(newStyle.toString());
    }

    /**
     * Used to set up the mapping required for the map station selectors to work
     * @param map the mapping from IDs of stations to the full name
     */
    public void setupIdsToStations(Map<String, String> map) {
        this.idsToStations = map;
    }


    /**
     * checks whether an intermediate point needs to be added to make the line fit a bit better
     * @param station,nextStation stations between which we're checking
     * @param color               to determine whether green or orange needs to be taken at that one spot
     * @return coordinates of the mid-point or [0,0] if none is needed
     */
    private double[] getMidCoords(String station, String nextStation, String color) {

        if (station.equals("30 Downtown Crossing") && nextStation.equals("28 State")) {
            return new double[]{532, 312};
        } else if (station.equals("28 State") && nextStation.equals("30 Downtown Crossing")) {
            return new double[]{532, 312};
        } else if (color.equals("Green") && station.equals("20 North Station") && nextStation.equals("22 Haymarket")) {
            return new double[]{545, 230};
        } else if (color.equals("Green") && station.equals("22 Haymarket") && nextStation.equals("20 North Station")) {
            return new double[]{545, 230};
        } else if (station.equals("47 Kenmore") && nextStation.equals("54 St.Mary's Street")) {
            return new double[]{287, 330};
        } else if (station.equals("54 St.Mary's Street") && nextStation.equals("47 Kenmore")) {
            return new double[]{287, 330};
        } else if (station.equals("41 Copley") && nextStation.equals("53 Prudential")) {
            return new double[]{365, 346};
        } else if (station.equals("53 Prudential") && nextStation.equals("41 Copley")) {
            return new double[]{365, 346};
        } else if (station.equals("33 South Station") && nextStation.equals("60 Broadway")) {
            return new double[]{569, 386};
        } else if (station.equals("60 Broadway") && nextStation.equals("33 South Station")) {
            return new double[]{569, 386};
        } else if (station.equals("98 JFK/UMass")) {
            if (nextStation.equals("100 Savin Hill")) {
                return new double[]{572, 529};
            } else if (nextStation.equals("120 North Quincy")) {
                return new double[]{572, 529};
            }
        } else if (nextStation.equals("98 JFK/UMass")) {
            if (station.equals("100 Savin Hill")) {
                return new double[]{572, 529};
            } else if (station.equals("120 North Quincy")) {
                return new double[]{572, 529};
            }
        }
        return new double[]{0, 0};
    }

}
