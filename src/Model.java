import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Model {

    MultiGraph<Node, Edge<Node>> multiGraph;
    HashMap<String,Node> stationsHashMap;
    List<Node> stations;

    public Model(String filepath){
        this.setMultiGraph(filepath);
        this.setStationsHashMap();

    }

    /**
     * @param filepath passed to FileReader
     * MultiGraph initialised with Nodes and Edges
     * */
    private void setMultiGraph(String filepath){
        FileReader reader = new FileReader(filepath);

        multiGraph = new MultiGraph<>();
        stations = reader.getStations();
        List<Edge<Node>> connections = reader.getConnections();

        for(Node n: stations) multiGraph.addNode(n);
        for(Edge<Node> e: connections) multiGraph.addEdge(e);

    }

    private void setStationsHashMap(){
        stationsHashMap = new HashMap<>();
        for(Node station : stations){
            this.stationsHashMap.put(station.toString(),station);
        }
    }

    public ArrayList<String> getListOfStations(){
        return new ArrayList<>(stationsHashMap.keySet());

    }

    /**
     * @param start,destination stored as Nodes and passed to MultiGraph
     * Tuple used to store Line-Colour and Station's
     * @return Arraylist of Tuples for optimal Route
     * */
    public ArrayList<Tuple<String,ArrayList<String>>> runSearch(String start,String destination){
        Node from = this.find(start);
        Node to = this.find(destination);

        ArrayList<Tuple<String,ArrayList<String>>> processedForView = new ArrayList<>();

        List<Edge<Node>> path = multiGraph.getPath(from,to);

        Node station = from;
        String lineColour = path.get(0).getLabel();
        Tuple<String,ArrayList<String>> line = new Tuple<>(lineColour,new ArrayList<>());
        line.second.add(station.toString());

        for(Edge<Node> edge: path){
            System.out.println(edge.toString());
            lineColour = edge.getLabel();
            if(!lineColour.equals(line.first)){
                processedForView.add(line);
                line = new Tuple<>(lineColour,new ArrayList<>());
            }
            station = edge.getOppositeNode(station);
            line.second.add(station.toString());
        }

        processedForView.add(line);

        return processedForView;
    }

    private Node find(String name){
        return stationsHashMap.get(name);
    }
}
