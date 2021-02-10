import java.util.ArrayList;

/**
 * Covers Line for each colour including the letter branches
 * Store possibly edges or nodes for Directional Graph
 * */

public class Line implements Graph{

    String name;
    ArrayList<Edge> connections;
    ArrayList<Node> stations;

    public Line(String name){
        this.name = name;
    }

    public void addEdge(Edge edge ){
        this.connections.add(edge);
    }

    public void addNode(Node node){
        this.stations.add(node);
    }

    public ArrayList<Node> nextNodes() {
        return stations;
    }
}
