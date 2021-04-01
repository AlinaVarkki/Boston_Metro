import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class mapController {

    View view;
    String destinationDirection;

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
