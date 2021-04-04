import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class mapController {

    View view;
    String destinationDirection;
    @FXML
    ImageView mapImage;

    @FXML
    private void getName(ActionEvent event){

        Button button = (Button) event.getSource();
        String stationName = parseString(button.getId());

        view.closeMap();

        if(destinationDirection.equals("START")){
            view.setStartDest(stationName);
            view.changeSelectorColourStart();
        }
        else{
            view.setEndDest(stationName);
            view.changeSelectorColourEnd();
        }
        view.closeMap();
    }

    //method that parses the station name to the one we use for the search.
    // Implemented because button ids cannot contain slashed and numbers at the start
    public String parseString(String station){
        StringBuilder stationParsed = new StringBuilder();

        //starting with 1 because first character is a dummy as id cannot be started with a number
        for(int i = 1; i < station.length(); i++){
            char currChar = station.charAt(i);
            if(Character.isUpperCase(currChar)){
                stationParsed.append(" " + currChar);
            }else{
                stationParsed.append(currChar);
            }
        }

        return stationParsed.toString();
    }

    public void setView(View view){
        this.view = view;
    }

    public void setDestinationDirection(String destinationDirection){
        this.destinationDirection = destinationDirection;
    }

    public String getDestinationDirection(){
        return this.destinationDirection;
    }


}
