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

        List<Station> path = graph.getPath(stations.get(26), stations.get(41));

        for(Station s: path){
            System.out.println(s.name);
        }

    }

}
