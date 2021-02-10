import java.util.ArrayList;
import java.util.HashSet;

/**
 * Covers Line for each colour including the letter branches
 * Store possibly edges or nodes for Directional Graph
 * */

public class Line implements Graph{

    String name;
    ArrayList<Edge> connections;
    HashSet<Node> stations;

    public Line(String name){
        this.name = name;
        stations = new HashSet<>();
    }

    public void addEdge(Edge edge ){
        this.connections.add(edge);
    }

    public void addNode(Node node){
        this.stations.add(node);
    }

    public HashSet<Node> nextNodes() {
        return stations;
    }
}
