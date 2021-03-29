 import javafx.event.Event;
import javafx.fxml.FXML;
 import javafx.scene.control.*;
 import javafx.scene.control.Button;
 import javafx.scene.layout.Background;
 import javafx.scene.text.Text;
 import javafx.util.Callback;

 import java.awt.*;
 import java.util.List;

 public class Controller {
    @FXML
    Button findButton;
    @FXML
    ComboBox startDestSelector;
    @FXML
    ComboBox endDestSelector;

    @FXML
    Text startStationErrorMsg;

    @FXML
    Text endStationErrorMsg;

    @FXML
    public void initialize(){
        fillStationsOptions();


    }

    public void fillStationsOptions(){
        FileReader fileReader = new FileReader();
        List<Node> stations = fileReader.getStations();

        for(Node station: stations){
            String name = station.getName();
            startDestSelector.getItems().add(name);
            endDestSelector.getItems().add(name);
        }
    }


    @FXML
    public void findButtonClicked(Event e){

        if(startDestSelector.getValue() == null){
            startStationErrorMsg.setVisible(true);
            startDestSelector.setStyle(" -fx-background-radius: 10; -fx-background-color: #ffe7e7; -fx-border-color: #0B132B;");
        }else{
            startStationErrorMsg.setVisible(false);
            startDestSelector.setStyle(" -fx-background-radius: 10; -fx-background-color: ffffff; -fx-border-color: #0B132B;");
        }

        if(endDestSelector.getValue() == null){
            endStationErrorMsg.setVisible(true);
            endDestSelector.setStyle(" -fx-background-radius: 10; -fx-background-color: #ffe7e7; -fx-border-color: #0B132B;");
        }else{
            endStationErrorMsg.setVisible(false);
            endDestSelector.setStyle(" -fx-background-radius: 10; -fx-background-color: ffffff; -fx-border-color: #0B132B;");
        }

        System.out.println(endDestSelector.getValue());
        System.out.println(startDestSelector.getValue());
        System.out.println("Button clicked");
    }

    @FXML
     public void setDefaultStyleEndSelector(){
        endStationErrorMsg.setVisible(false);
        endDestSelector.setStyle(" -fx-background-radius: 10; -fx-background-color: ffffff; -fx-border-color: #0B132B;");
    }

    public void setDefaultStyleStartSelector(){
        startStationErrorMsg.setVisible(false);
        startDestSelector.setStyle(" -fx-background-radius: 10; -fx-background-color: ffffff; -fx-border-color: #0B132B;");
    }
}
