import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args){
        FileReader g = new FileReader();
        g.readInGraph("src/bostonmetro.txt");
       // System.out.println("Orange line:");
        //g.printLineEdges(1,"Orange");
        List<Node> stations = g.getStations();
        List<Edge> connections = g.getConnections();

        MultiGraph graph = new MultiGraph<>();
        for(Node n: stations) graph.addNode(n);
        for(Edge e: connections) graph.addEdge(e);

        List<Station> path = graph.getPath(getStationByName(stations, "Symphony"), getStationByName(stations, "Fenway"));

        for(Station s: path){
            System.out.println(s.name);
        }

    }

    public static Node getStationByName(List<Node> stations, String name){
        for(Node s: stations){
            if(s.name.equals(name)) return s;
        }
        return null;
    }

}
