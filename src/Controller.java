 import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
 import javafx.scene.control.MenuButton;
 import javafx.scene.control.MenuItem;

 import java.util.List;

 public class Controller {
    @FXML
    Button findButton;
    @FXML
    MenuButton startDestSelector;
    @FXML
    MenuButton endDestSelector;

    @FXML
    public void initialize(){
        fillStationsOptions();

    }


    public void fillStationsOptions(){
        FileReader fileReader = new FileReader();
        List<Node> stations = fileReader.getStations();

        MenuItem menuItem;
        MenuItem menuItem2;
        for(Node station: stations){
            String name = station.getName();
            menuItem = new MenuItem(name);
            startDestSelector.getItems().add(menuItem);
            menuItem2 = new MenuItem(name);
            endDestSelector.getItems().add(menuItem2);
        }

    }


    @FXML
    public void findButtonClicked(Event e){
        System.out.println("Button clicked");
    }

    @FXML
    public void loadStations(){
        System.out.println("shown");

    }
}
