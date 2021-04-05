import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public class Controller {
    Model model;
    View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    /**
     * This method connects Model and View to function properly
     */
    public void run() {
        this.setUpDropDowns();
        this.setupButtonEventHandler(this);
        view.setupAlgorithmSelectorEventHandler();
        view.setupIdsToStations(model.getMapIdsToStation());
        view.setStandardStyles();
        view.setupMap();

    }

    /**
     * This method sets up the stations loaded from the file in the model to the dropdown menus in the View
     */
    private void setUpDropDowns() {
        List<String> stations = model.getListOfStations();
        Map<String, List<String>> stationColorMap = model.getStationColorMap();
        view.customizeDropDowns(stationColorMap);
        view.fillStationsOptions(stations);
    }

    /**
     * This method sets up the controller for Find button in the View, connecting the View to the Model
     */
    private void setupButtonEventHandler(Controller controller) {
        view.setFindButtonEventHandler(e -> controller.performSearch());
    }

    /**
     * Calls elements of Model to get the most Direct Path from one Station to the Other
     * Calls elements of View to Construct the Line output with Consistent Styling and Sizing of corresponding Labels
     */
    private void performSearch() {
        if (view.stationsSelected()) {

            String from = view.getSourceStation();
            String to = view.getDestinationStation();
            String algorithm = view.getAlgorithmSelected();

            List<Pair<String, List<String>>> path = model.runSearch(from, to, algorithm);
            view.displayFoundPath(path);

        }
    }

}
