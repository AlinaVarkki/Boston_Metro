import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class mapController {

    @FXML
    private void getName(ActionEvent event){
        Button button = (Button) event.getSource();
        System.out.println(button.getId()); // prints out button's text
    }
}
