import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class MapView {

    View view;
    String destinationDirection;
    @FXML
    ImageView mapImage;

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

    public void setView(View view) {
        this.view = view;
    }

    public void setDestinationDirection(String destinationDirection) {
        this.destinationDirection = destinationDirection;
    }

    public String getDestinationDirection() {
        return this.destinationDirection;
    }

}
