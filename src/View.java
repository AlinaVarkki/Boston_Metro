import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.List;
import java.util.function.Function;

public class View {

    PathDisplayer pathDisplayer;
    Pane pathDisplayed;
    AnchorPane root ;
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
    public void initialize(){
        customizeDropDown(startDestSelector);
        customizeDropDown(endDestSelector);
        pathDisplayer = new PathDisplayer();
    }

    public void customizeDropDown(ComboBox comboBox){
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
//                                    setGraphic();
                                    if (item.contains("A")) {
                                        setTextFill(Color.RED);
                                    }
                                    else if (item.contains("B")){
                                        setTextFill(Color.ORANGE);
                                    }
                                    else {
                                        setTextFill(Color.GREEN);
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

    public void fillStationsOptions(List<String> stations){
        startDestSelector.getItems().addAll(stations);
        endDestSelector.getItems().addAll(stations);
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
            this.pathDisplayed.setVisible(false);
        }
        root.getChildren().add(pathDisplayer.createLine(path));

    }

    public void setTitleVisibility(boolean visible){
        titleImage.setVisible(visible);
        titleText1.setVisible(visible);
        titleText2.setVisible(visible);
    }

    public void setRoot(AnchorPane root){
        this.root = root;
        Rectangle bg = new Rectangle(686, 550);
        bg.setFill(background);
        root.getChildren().add(bg);
        bg.toBack();
    }
}
