import java.util.ArrayList;
import java.util.List;

public class Model {

    FileReader fileReader = new FileReader();

    public List<String> getStations(){
        List<Node> stations = fileReader.getStations();
        List<String> stationsNames = new ArrayList<>();

        for(Node station: stations){
            String name = station.getName();
            stationsNames.add(name);
        }

        return stationsNames;
    }
}
