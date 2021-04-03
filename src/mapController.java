import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class mapController {

    View view;
    String destinationDirection;
    @FXML
    ImageView mapImage;

    @FXML
    private void getName(ActionEvent event) throws IOException {

        Button button = (Button) event.getSource();
        String stationName = button.getId();

        if(stationName.equals("BostonCollege")){
            stationName = "69 Boston College";
        }else if(stationName.equals("SouthStreet")){
            stationName = "75 South Street";
        }else if(stationName.equals("JFK")){
            stationName = "JFK/UMass";
        }else if(stationName.equals("BackBay")){
            stationName = "BackBay/SouthEnd";
        }


        view.closeMap();
        System.out.println(stationName); // prints out button's text

        if(destinationDirection == "START") view.setStartDest(stationName);
        else view.setEndDest(stationName);
    }

    public void setView(View view){
        this.view = view;
    }

    public void setDestinationDirection(String destinationDirection){
        this.destinationDirection = destinationDirection;
    }


}
