import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    Model model;
    View view;

    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
    }

   public void run(){
        this.setUpDropDowns();

        this.setupButtonEventHandler(this);

    }

    private void setUpDropDowns(){
        List<String> stations = model.getListOfStations();
        view.fillStationsOptions(stations);
    }

    private void setupButtonEventHandler(Controller controller){
        view.setFindButtonEventHandler(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                controller.performSearch();
            }});
    }

    private void performSearch(){
        if( view.stationsSelected() ){


            String from = view.getSourceStation();
            String to = view.getDestinationStation();



            List<Tuple<String, List<String>>> path = model.runSearch(from,to);
            view.displayFoundPath(path);
        }
    }

}
