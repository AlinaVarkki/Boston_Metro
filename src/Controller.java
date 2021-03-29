import javafx.fxml.FXML;
 import javafx.scene.control.*;
 import javafx.scene.control.Button;
 import javafx.scene.text.Text;

 import java.util.List;

 public class Controller {

     Model model = new Model();

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
        List<String> stations = model.getStations();

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
            System.out.println(endDestSelector.getValue());
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
}
