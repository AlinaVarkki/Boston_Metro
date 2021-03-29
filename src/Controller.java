 import javafx.event.Event;
import javafx.fxml.FXML;
 import javafx.scene.control.*;
 import javafx.util.Callback;

 import java.util.List;

 public class Controller {
    @FXML
    Button findButton;
    @FXML
    ComboBox startDestSelector;
    @FXML
    ComboBox endDestSelector;

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
        System.out.println("Button clicked");
    }
}
