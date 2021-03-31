import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Callback;

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

    }

    public void setOptionsColours(ComboBox comboBox){

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

    public void changeSelectorColourStart(){
        String selectedStation = startDestSelector.getValue();
        if(selectedStation != null){
            circleStart.setVisible(true);
            Image image = new Image("Images/whiteCircle.png");
            if(stationColorMap.get(selectedStation).equals("Green")){
                image = new Image("Images/greenCircle.png");
            }else if(stationColorMap.get(selectedStation).equals("Red")){
                image = new Image("Images/redCircle.png");
            }else if(stationColorMap.get(selectedStation).equals("Orange")){
                image = new Image("Images/yellowCircle.png");
            }else if(stationColorMap.get(selectedStation).equals("Blue")){
                image = new Image("Images/blueCircle.png");
            }
            circleStart.setImage(image);
        }
    }

    public void changeSelectorColourEnd(){
        String selectedStation = endDestSelector.getValue();
        circleEnd.setVisible(true);
        if(selectedStation != null){
            circleStart.setVisible(true);
            Image image = new Image("Images/whiteCircle.png");
            if(stationColorMap.get(selectedStation).equals("Green")){
                image = new Image("Images/greenCircle.png");
            }else if(stationColorMap.get(selectedStation).equals("Red")){
                image = new Image("Images/redCircle.png");
            }else if(stationColorMap.get(selectedStation).equals("Orange")){
                image = new Image("Images/yellowCircle.png");
            }else if(stationColorMap.get(selectedStation).equals("Blue")){
                image = new Image("Images/blueCircle.png");
            }
            circleEnd.setImage(image);
        }
    }

    public void fillStationsOptions(List<String> stations){

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
        endDestSelector.setStyle(" -fx-background-radius: 10; -fx-background-color: ffffff; -fx-border-color: #0B132B;");
    }

    public void setDefaultStyleStartSelector(){
        startStationErrorMsg.setVisible(false);
        startDestSelector.setStyle(" -fx-background-radius: 10; -fx-background-color: ffffff; -fx-border-color: #0B132B;");
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
            root.getChildren().remove(pathDisplayed);
        }
        pathDisplayed = pathDisplayer.createLine(path);
//        root.getChildren().add(pathDisplayed);

        container.setCenter(pathDisplayed);

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
}
