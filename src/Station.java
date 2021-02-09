import java.util.ArrayList;

public class Station extends Node {

    public Station(String idNum){
        this.idNumber = idNum;
        this.edges = new ArrayList<Edge>();

    }
}
