import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class MapView {

    View view;
    String destinationDirection;
    @FXML
    ImageView mapImage;

    /**
     * Used as event handler for every button, parsing the button Id and setting the selected station
     * in the correct station selector, then closing the map
     * @param event ActionEvent
     */
    @FXML
    private void getName(ActionEvent event) {

        Button button = (Button) event.getSource();
        String stationName = button.getId().substring(1);

        view.closeMap();

        if (destinationDirection.equals("START")) {
            view.setStartDest(stationName);
            view.changeSelectorColourStart();
        } else {
            view.setEndDest(stationName);
            view.changeSelectorColourEnd();
        }
        view.closeMap();
    }

    /**
     * Setting the view with which the mapView communicates
     * @param view the parent View
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * Sets up what is map setup to do "START" for selecting the start station and "END" for selecting the end station
     * @param destinationDirection "START" for selecting the start station and "END" for selecting the end station
     */
    public void setDestinationDirection(String destinationDirection) {
        this.destinationDirection = destinationDirection;
    }

    /**
     * Returns what is map setup to do "START" for selecting the start station and "END" for selecting the end station
     * @return "START" for selecting the start station and "END" for selecting the end station
     */
    public String getDestinationDirection() {
        return this.destinationDirection;
    }

}
