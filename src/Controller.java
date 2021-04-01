import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.List;
import java.util.Map;

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
        view.setupAlgorithmSelectorEventHandler();
    }

    private void setUpDropDowns(){
        List<String> stations = model.getListOfStations();
        Map<String, String> stationColorMap = model.getStationColorMap();
        view.customizeDropDowns(stationColorMap);
        view.fillStationsOptions(stations);
    }

    private void setupButtonEventHandler(Controller controller){
        view.setFindButtonEventHandler(e -> controller.performSearch());
    }

    /**
     * Calls elements of Model to get the most Direct Path from one Station to the Other
     * Calls elements of View to Construct the Line output with Consistent Styling and Sizing of corresponding Labels
     * */
    private void performSearch(){
        if( view.stationsSelected() ){

            String from = view.getSourceStation();
            String to = view.getDestinationStation();
            String algorithm = view.getAlgorithmSelected();

            List<Tuple<String, List<String>>> path = model.runSearch(from, to,algorithm);
            view.displayFoundPath(path);

        }
    }

}
