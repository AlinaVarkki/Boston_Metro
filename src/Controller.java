import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.Random;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    Model model;
    View view;
    VBox root;

    public Controller(Model model,View view, VBox root){
        this.model = model;
        this.view = view;
        this.root = root;
    }

    public void run(){
        performSearch();
    }

    private void setUpDropDowns(){
        List<String> stations = model.getListOfStations();
    }

    private void showFirstScreen(){

    }

    /**
     * Calls elements of Model to get the most Direct Path from one Station to the Other
     * Calls elements of View to Construct the Line output with Consistent Styling and Sizing of corresponding Labels
     * */
    private void performSearch(){
        List<String> stations = model.getListOfStations();
        Random random = new Random();
        String from = stations.get(random.nextInt(stations.size()-1));//view.getFromStation();
        String to = stations.get(random.nextInt(stations.size()-1));//view.getToStation();

//        from = "69 BostonCollege";
//        from = "111 CapenStreet";
//        to = "112 Mattapan";

        ArrayList<Tuple<String, ArrayList<String>>> path = model.runSearch(from,to);
        /*for(Tuple<String, ArrayList<String>> tuple : path){
            System.out.println(tuple.first);
            System.out.println(Arrays.deepToString(tuple.second.toArray()));

        }*/
        //view.displaySecondScreen(path);
        root.getChildren().add(view.createLine(path));

    }

}
