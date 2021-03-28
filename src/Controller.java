import javafx.scene.control.Label;

import java.util.Arrays;
import java.util.Random;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    Model model;
    View view;
    public Controller(Model model,View view){
        this.model = model;
        this.view = view;
    }

    public void run(){
        performSearch();
    }

    private void setUpDropDowns(){
        List<String> stations = model.getListOfStations();
    }

    private void showFirstScreen(){

    }

    private void performSearch(){
        List<String> stations = model.getListOfStations();
        Random random = new Random();
        String from = stations.get(random.nextInt(stations.size()-1));//view.getFromStation();
        String to = stations.get(random.nextInt(stations.size()-1));//view.getToStation();

        to = "88 FenwoodRoad";
        from = "65 Longwood";
        ArrayList<Tuple<String, ArrayList<String>>> path = model.runSearch(from,to);
        /*for(Tuple<String, ArrayList<String>> tuple : path){
            System.out.println(tuple.first);
            System.out.println(Arrays.deepToString(tuple.second.toArray()));

        }*/
        //view.displaySecondScreen(path);
    }

}
